/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Inventree.APIConnector;
import Inventree.InfoWorker;
import Inventree.SendWorker;
import Inventree.item.InventreeLists;
import Inventree.item.StockItem;
import barcode.StockList;
import data.CONSTANT;
import data.IniHandler;
import data.IniStruct;
import data.SerialHandler;
import data.UTILS;

import events.ConnectionEvent;
import events.*;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.event.EventListenerList;
import listeners.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.ItemCheck;

/**
 *
 * @author blegendre
 */
public class Model {
     private EventListenerList listeners;
     
     private JFrame mainFrame;
     
     
     /* gestion des préférences */
     private IniHandler iniH;
     private APIConnector apiConn;
     private InventreeLists ivl;
     
     /* gestion des interaction api */
     private boolean connection_status = false; // status de la connexion au serveur
     private int bcb_status;
     private boolean global_status;
     
     /* Gestion des code barre */
     private InfoWorker iw;
     private SendWorker sw;
     
    private boolean isSending; // follow sending process
    
    public Model() {
        super();
        this.iniModel();
    }
    public void setMainFrame(JFrame jf){
        mainFrame = jf;
    }
    
    private void iniModel(){
        isSending = false;
        
        listeners = new EventListenerList();
        iniH = new IniHandler(this);
        apiConn = new APIConnector(this);
        ivl = InventreeLists.getInstance();
        
        //bcL = new BarcodeList();
        iw = new InfoWorker(this, apiConn);
        iw.setPriority(1);
        
    }
    public void initialize(){
        this.dispatchInfo("Initialisation en cours", InfoEvent.INFO_NORM);
        iniH.initialise();

        
    }
    
    
    // gestion du login
    public void registerLogin(String url, String user, String pass,String forceHTTP){
        iniH.setValue(CONSTANT.SERVEUR_PARAM_HEAD, CONSTANT.SERVEUR_PARAM_URL, url);
        iniH.setValue(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_NAME, user);
        iniH.setValue(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_FORCEINSECURE, forceHTTP);
        
        Boolean savePass = Boolean.valueOf(iniH.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_SAVE_PASS, "false"));
        if(savePass){
            iniH.setValue(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_PASS, pass, true);
        }else{
            iniH.removeKey(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_PASS);
        }
        apiConn.updateURL(url);
        apiConn.updateUserName(user);
        apiConn.updatePassword(pass);
        
        this.dispatchInfo("Sending connection to serveur ....", InfoEvent.INFO_NORM);
        
        apiConn.updateForceHTTP(Boolean.valueOf(forceHTTP));
        sendConnection();
        
    }
    private void sendConnection(){
        connection_status = apiConn.connect();
        if(connection_status){
            this.navigate(CONSTANT.PAGE_SCAN);
            // update des données du serveur à récupérer
            apiConn.updateParams();
             // recharge de la session précédente
             this.loadSerialization();
        }       
    }
    
    // ====================================================================  management of status and error related to
    
    public void setConnectionStatus(Boolean status, String reason){
        connection_status = status;
        ConnectionEvent e = new ConnectionEvent(this, status?ConnectionEvent.CONNECTION_SUCCESS:ConnectionEvent.CONNECTION_ERROR, reason);
        this.dispatchEvent(ConnectionListener.class, e);
        if(!connection_status)
            this.navigate(CONSTANT.PAGE_LOGIN);
    }
    
    public boolean getGlobalStatus(){
        return global_status;
    }
    
    
    /* --------------------- Gestion de la Navigation --------------- */
    public void navigate(String page){
        System.out.println(this+"---> navigate : "+page);
        
        switch (page) {// pour les action du menu
            case CONSTANT.ACTION_LOGOUT:
                apiConn.logout();
                break;
            case CONSTANT.ACTION_UPDATE_PARAM:
                apiConn.updateParams();
                return;  
            case CONSTANT.ACTION_CLEAN_LIST:
                ivl.cleanStockList();
                // pour le fichier de save de la list
                String path = iniH.getIniFolder()+ System.getProperty("file.separator")+CONSTANT.FILE_SAVE;
                UTILS.removeFile(path);
                //this.updateSerialization();
                this.dispatchEvent(BarcodeListener.class, new BarcodeEvent(this, BarcodeEvent.BCB_STATUS_UPDATE));
                return;
            case CONSTANT.ACTION_CHECK_ALL:
                reCheckAllItems();
                return;
            case CONSTANT.ACTION_REMOVE_DELETED:
                removeDeletedStockItem();
                return;
            case CONSTANT.ACTION_SAVE:
                saveScanList();
                return;
        }

        NavEvent e = new NavEvent(this, NavEvent.NAVIGATE, page);
        this.dispatchEvent(NavigationListener.class, e);
    }
    /* --------------------- Gestion des interaction API ------------ */
    public void changeStatus(String newStatus){
        IniStruct tmp = new IniStruct();
        tmp.setValue(CONSTANT.SCAN_PARAM_HEAD, CONSTANT.SCAN_PARAM_MODE, newStatus);
        this.preferenceUpdate(tmp);
        this.dispatchInfo("Status change to :"+newStatus);
    }
    public int currentAPIStatus(){
        return bcb_status;
    }
    public void changeLocationSelection(String newLoc){
        IniStruct tmp = new IniStruct();
        tmp.setValue(CONSTANT.SCAN_PARAM_HEAD, CONSTANT.SCAN_STOCK_LOC, newLoc);
        this.preferenceUpdate(tmp);
        //this.dispatchInfo("Location change to : "+newLoc);
    }
    
    /* -------------------------------------------------------------
    ---------------- GESITON DES CODE BARRE / StockItem -------------
    ---------------------------------------------------------------- */
    public void addStockItem(StockItem si){
        this.dispatchInfo("adding stock item "+si.EAN);
        // add to list or to a current stock item
        String forceStockLoc = iniH.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_FORCE_LOCATION, "false");
        StockItem nsi = ivl.addStockItem(si, forceStockLoc);
        
        //serialization
        //updateSerialization();

        BarcodeEvent e = new BarcodeEvent(this, BarcodeEvent.NEW_BARCODE);
        e.stockitem = nsi;
        this.dispatchEvent(BarcodeListener.class, e);
        if(!iw.isAlive() || iw == null){
            //iw.setItem(nsi, forceStockLoc);
            iw = new InfoWorker(this, apiConn, nsi, forceStockLoc);
            iw.start();
        }     
    }
    public void reCheckAllItems(){
        this.dispatchInfo("Checking all items.....");
        for(StockItem si : ivl.stockList.getList()){
            if(CONSTANT.MODIFIABLE_STATUS.contains(si.getStatus())){
                si.setStatus(CONSTANT.STATUS_PENDING);
            }
        }
        reCheckStockItem(ivl.stockList.getNext(CONSTANT.STATUS_PENDING));
    }
    public void reCheckStockItem(StockItem si){
        if(si == null){
            BarcodeEvent e = new BarcodeEvent(this, BarcodeEvent.BCB_STATUS_UPDATE);
            this.dispatchEvent(BarcodeListener.class, e);
             return;
        }
           
        if(! CONSTANT.MODIFIABLE_STATUS.contains(si.getStatus())){
            //serialization
            //updateSerialization();
            return;
        }
        
        si.setStatus(CONSTANT.STATUS_PENDING);
        BarcodeEvent e = new BarcodeEvent(this, BarcodeEvent.NEW_BARCODE);
        e.stockitem = si;
        this.dispatchEvent(BarcodeListener.class, e);
        
        if(!iw.isAlive() || iw == null){
            String forceStockLoc = iniH.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_FORCE_LOCATION, "false");
            iw = new InfoWorker(this, apiConn, si, forceStockLoc);
            iw.start();
        }
            
    }
    /**Called by APIConnector when has update a SI
     * 
     * @param si 
     */
    public void updateStockItem(StockItem si){
        this.dispatchInfo("Updating stock items "+si.EAN);
        // check SI
        ItemCheck.check(si);
        
        BarcodeEvent e = new BarcodeEvent(this, BarcodeEvent.BCB_STATUS_UPDATE);
        e.stockitem = si;
        this.dispatchEvent(BarcodeListener.class, e);
        StockItem nsi = ivl.stockList.getNext(CONSTANT.STATUS_PENDING);
        //serialization
        //updateSerialization();
        if(nsi != null){
            String forceStockLoc = iniH.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_FORCE_LOCATION, "false");
            //iw.setItem(nsi, forceStockLoc);
            iw = new InfoWorker(this, apiConn, nsi, forceStockLoc);
            iw.start();
        }
    }
    
    public void removeDeletedStockItem(){
        ArrayList<StockItem> rmL = ivl.stockList.getListOf(CONSTANT.DELETED_REMOVE_STATUS);
        ivl.stockList.getList().removeAll(rmL);
        BarcodeEvent e = new BarcodeEvent(this, BarcodeEvent.NEW_BARCODE);
        this.dispatchEvent(BarcodeListener.class, e);
    }
    
    
    // ------------------- CREATION DE PART --------------- //
    // ---------------------------------------------------- //
    public void createPartLaunch(StockItem si){
        SendEvent e = new SendEvent(this, SendEvent.CREATE_ITEM_START, si);
        this.dispatchEvent(SendListener.class, e);
        
    }
    public void createPart(StockItem si, JSONObject jso){
        apiConn.createPart(si, jso);
    }
    public void partCreated(StockItem si){
        SendEvent e;
        if(si.partitem.getId()==0){
            e = new SendEvent(this,SendEvent.CREATE_ITEM_FAILED, si);
            this.dispatchEvent(SendListener.class, e);
            dispatchInfo("Part Creation <font style='color:red'>FAILED</font>");
            return;
        }
        // fin cycle creation
        e = new SendEvent(this,SendEvent.CREATE_ITEM_SUCESS, si);
        this.dispatchEvent(SendListener.class, e);
        //update du barcode
        BarcodeEvent ev = new BarcodeEvent(this, BarcodeEvent.BCB_STATUS_UPDATE);
        ev.stockitem = si;
        this.dispatchEvent(BarcodeListener.class, ev);
        dispatchInfo("Part <font style='color:green'>CREATED</font>");
    }
    
    // ------------------- LINK DE PART --------------- //
    // ---------------------------------------------------- //
    public void linkPartLaunch(StockItem si){
        SendEvent e = new SendEvent(this, SendEvent.LINK_ITEM_START, si);
        this.dispatchEvent(SendListener.class, e);
        
    }
    public void linkPart(StockItem si, JSONArray jsa){
        apiConn.linkPart(si, jsa);
    }
    public void linkCreated(StockItem si, boolean status){
        SendEvent e;
        if(!status){
            e = new SendEvent(this,SendEvent.LINK_ITEM_FAILED, si);
            this.dispatchEvent(SendListener.class, e);
            dispatchInfo("Link Part <font style='color:red'>FAILED</font>");
            return;
        }
        // fin cycle link
        e = new SendEvent(this,SendEvent.LINK_ITEM_SUCESS, si);
        this.dispatchEvent(SendListener.class, e);
        //update du barcode
       
        dispatchInfo("Link Part <font style='color:green'>CREATED</font>");
        si.setStatus(CONSTANT.STATUS_PENDING);
        updateStockItem(si);
    }
    // ================== GESTION DES ENVOI VERS LE SERVEUR ============== //
    // =================================================================== //
    private String sending_type;
    public void sendToServer(String sendType){
        switch(sendType){
            case CONSTANT.ACTION_SEND_UPDATE:
                sending_type=CONSTANT.STATUS_ITEM_FOUND;
                break;
            case CONSTANT.ACTION_SEND_ITEM:
                sending_type=CONSTANT.STATUS_NEW_ITEM;
                break;
            case CONSTANT.ACTION_SEND_PART:
                sending_type=CONSTANT.STATUS_NEW_PART;
                break;
            case CONSTANT.ACTION_SEND_ALL:
                sending_type=CONSTANT.SENDABLE;
                break;
        }
        //System.out.println("model.Model.sendToServer() sending type :"+sending_type+"   ("+sendType+")");
        SendEvent e =new SendEvent(this, SendEvent.START_SENDING);
        this.dispatchEvent(SendListener.class, e);
        isSending = true;
         if(sw == null || !sw.isAlive()){
            sw = new SendWorker(this, apiConn);
            //System.out.println("model.Model.sendToServer() new SI :"+ivl.stockList.getNext(sending_type));
             
            sw.setItem(ivl.stockList.getNext(sending_type));
            sw.start();
        }
         
    }
    public void stopSending(){
        isSending = false;
        sending_type = null;
        SendEvent e = new SendEvent(this, SendEvent.END_SENDING);
        this.dispatchEvent(SendListener.class, e);
    }
    
    public void itemSended(StockItem si){
        /*System.out.println("model.Model.itemSended()"
                +"\n   is null :"+(sw == null)
                +"\n   is alive :"+(sw.isAlive())
                
                );*/
        SendEvent e = new SendEvent(this, SendEvent.ITEM_SENDING,si);
        this.dispatchEvent(SendListener.class, e);
        
        StockItem nsi = ivl.stockList.getNext(sending_type);
        
        if(nsi!=null && isSending){
            sw = new SendWorker(this, apiConn);
            //System.out.println("itemSended() new si :"+nsi);
            sw.setItem(nsi);
            sw.start();
        }else{
            this.stopSending();
        }
    }
   
    /*  ================== GESTION DU CLIPBOARD ================= */
    // ========================================================== //
    public void addToClipBoard(String copyValue){
        StringSelection ss = new StringSelection(copyValue);
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        cb.setContents(ss, null);
        this.dispatchInfo(copyValue + " has been copied to clipboard");
    }
    /* --------------------- Gestion des préférence ------------- */
    public void preferenceLoaded(){
        this.dispatchInfo("Ini file loaded", InfoEvent.INFO_NORM);
        //update du connecteur 
        try{
            apiConn.initData();
        }catch(IllegalArgumentException e){
            InfoEvent ev = new InfoEvent(this, InfoEvent.CONNECTION_ERROR, "Please update connection informations");
            this.dispatchEvent(InfoListener.class, ev);
        }
        IniEvent e = new IniEvent(this, IniEvent.INI_LOADED, iniH.getStructure());
        this.dispatchEvent(ListenerI.class, e);
        this.navigate(CONSTANT.PAGE_LOGIN);
            
        
        // si auto login
        String auto = iniH.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_AUTO_LOG, "false");
        if(Boolean.valueOf(auto))
            sendConnection();
    }
    public void preferenceUpdate(IniStruct iniS){
         Class c;
        iEvent e;
       if(iniH.processIni(iniS)){
           e  = new IniEvent(this, IniEvent.INI_CHANGED, iniH.getStructure());
           c = ListenerI.class;
       }else{
           e = new ConnectionEvent(this, ConnectionEvent.CONNECTION_ERROR, "Unable to Save Parameters");
           c = ConnectionListener.class;
       }
        this.dispatchEvent(c, e);
        
        // process du pass word
        Boolean savePass = Boolean.valueOf(iniH.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_SAVE_PASS, "false"));
        
        if(savePass){
            String pass = apiConn.getPassword();
           
            if(pass != null)
                iniH.setValue(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_PASS, pass, true);
        }else{
            iniH.removeKey(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_PASS);
        }
        
        
    }
    public String getIniValue(String sectionName, String keyName){
        String val = iniH.getValue( sectionName,  keyName);
        if(val == null){
            //InfoEvent e = new InfoEvent(this, InfoEvent.INI_FILE_ERROR, sectionName + " / " + keyName + " not found");
            //this.dispatchEvent(bcbListenerInfoI.class, e);
        }
        return val;
    }
    public String getIniValue(String sectionName, String keyName, boolean encodeB64){
        String val = iniH.getValue( sectionName,  keyName, encodeB64);
        if(val == null){
            //InfoEvent e = new InfoEvent(this, InfoEvent.INI_FILE_ERROR, sectionName + " / " + keyName + " not found");
            //this.dispatchEvent(bcbListenerInfoI.class, e);
        }
        return val;
    }
    public String getIniValue(String sectionName, String keyName, String defaultValue){
        String val = iniH.getValue( sectionName,  keyName, defaultValue);
        if(val == null){
            //InfoEvent e = new InfoEvent(this, InfoEvent.INI_FILE_ERROR, sectionName + " / " + keyName + " not found");
            //this.dispatchEvent(bcbListenerInfoI.class, e);
        }
        return val;
    }
    
    public String getIniFolder(){
        return iniH.getIniFolder();
    }
    
    /* --------------------- Gestion des param serverur/ listes  ------------- */
    public void paramLoaded(){
        ParamEvent e = new ParamEvent(this, ParamEvent.PARAM_LOADED, InventreeLists.getInstance());
        this.dispatchEvent(ParamListener.class, e);
    }
  
    
    /* --------------------- Gestion des Event ----------------- */
    public void addEventListener(ListenerI listener, Class listenerInterfaceClass){
        //System.out.println("Model addEvent : "+eventClass+ "/ "+listener);
        listeners.add(listenerInterfaceClass, listener);
    }
    public void removeEventListener(ListenerI listener, Class listenerInterfaceClass){
        /*if(listeners.getListeners(listenerInterfaceClass).equals(listener)){
        listeners.remove(listenerInterfaceClass, listener);
        }*/
        listeners.remove(listenerInterfaceClass, listener);
        
    }
    
    
    
    /**
   * Dispatche event to a specific class of listener that implement bcbListenerI.
   * 
     * @param cl : the class to dispatch
     * @param e : an event extend Event
   */
    public void dispatchEvent(Class cl, iEvent e){
        //System.out.println("Model Dispatch : "+cl+ "/ "+e.type+ "("+e+")");
        ListenerI[] listenerList = (ListenerI[]) listeners.getListeners(cl);
        for(ListenerI listener : listenerList){
            listener.eventRecept(e);
        }
    }
    public void dispatchInfo(String info, String InfoType){
        InfoEvent e = new InfoEvent(this, InfoType, info);
        this.dispatchEvent(InfoListener.class, e);
    }
    protected void dispatchInfo(String info){
        this.dispatchInfo(info, InfoEvent.INFO_NORM);
    }

    
    // ====================================== SERIALIZATION ============================== //
    public void saveScanList(){
        boolean success = updateSerialization();
        String ss = (success ? InfoEvent.SAVE_SUCESS : InfoEvent.SAVE_ERROR);
        InfoEvent e = new InfoEvent(this, ss);
        this.dispatchEvent(InfoListener.class, e);
    }
    private boolean updateSerialization() {
        // uypdate de la sérialisation
        String path = iniH.getIniFolder()+ System.getProperty("file.separator")+CONSTANT.FILE_SAVE;
        return SerialHandler.writeObjectToFile(ivl.stockList, path);
    }

    private void loadSerialization() {
        String path = iniH.getIniFolder()+ System.getProperty("file.separator")+CONSTANT.FILE_SAVE;
        StockList sl = SerialHandler.readObjectFromFile(StockList.class, path);
        if (sl != null){
            for(StockItem si : sl.getList()){
               ivl.stockList.add(si);
            }
            reCheckAllItems();
           
        }
    }
}
