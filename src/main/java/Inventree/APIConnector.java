/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventree;

import Inventree.item.Category;
import Inventree.item.CompanyItem;
import Inventree.item.InventreeLists;
import Inventree.item.PartItem;
import Inventree.item.StockItem;
import Inventree.item.StockLocation;
import data.CONSTANT;
import data.UTILS;
import events.IniEvent;
import events.iEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import javax.naming.AuthenticationException;
import listeners.ListenerI;
import model.Model;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author blegendre
 */

public class APIConnector implements ListenerI{
    public static String PROTOCOLE="https://";
    public static String PROTOCOLE_FORCE_NON_SECURE="http://";
    public static Pattern cleanProtPattern;
    static {
        cleanProtPattern=Pattern.compile("^https{0,1}:[/]+|/$");
    }
    
    private final Model model;
    private String invURL;
    private String apiKey;
    private String userName;
    private String password;
    
    private boolean forceHTTP=false;
    
    private InventreeLists ivl;
    
    
    
    public APIConnector(Model m){
        model = m;
        m.addEventListener(this, ListenerI.class);
        ivl = InventreeLists.getInstance();
    }
    
    
    /* ------------ setter et getter -------------- */
    public void updateURL(String val){
        invURL = val;
        if (invURL == null)
            throw new IllegalArgumentException("Invalid URL value");
    }    
    public void updateAPIKey(String val){
        apiKey = val;
        
        if (apiKey == null)
            throw new IllegalArgumentException("Invalid apiKey value");
    }
    public void updateUserName(String val){
        userName = val;
        if (userName == null)
            throw new IllegalArgumentException("Invalid userName value");
    }
    public void updatePassword(String val){
        password = val;
        //if (password == null)
        //    throw new IllegalArgumentException("Invalid userName value");
        //System.out.println(this+" UPDATE \n ---------> pass :"+password);
    }
    public void updateForceHTTP(boolean force){
        forceHTTP = force;
    }
    // getter
    public String getPassword(){
        //System.out.println(this+" GET \n ---------> pass :"+password);
        return  this.password;
    }
    public String getUserName(){
        return  userName;
    }
    public String getURL(){
        return  invURL;
    }
    public void logout(){
        apiKey = "";
        password = "";
    }
    // Connection
    /**Launc connection and get the token from iv server
     * 
     * @return 
     */
    public Boolean connect(){
        //System.out.println(this+" CONNECT  \n ---------> pass :"+password);
        if(invURL == null | userName == null | password == null){
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.INFO_MISSING);
            return false;
        }
        try {
           apiKey = InventreeAPI.requestToken(cleanURL(invURL), userName, password);
           //System.out.println(apiKey);
           model.setConnectionStatus(Boolean.TRUE, null);
           return true;
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
        }
        return false;
        
    }
    
    // =================================== MISE 0 JOUR DES LISTE PARAM ===================== //
    // ===================================================================================== //
    
    /**
     * Launch update of items list from ivl server
     * - stock locations, manufacturers, suppliers, templated part, category
     */
    public void updateParams(){
         updateStockLocation();
         updateManufacturer();
         updateSupplier();
         updatePartTemplateSourceList();
         updateCategory();
         model.paramLoaded();
         
    }
    private void updateCategory(){
        JSONArray jso;
        try {
           jso = InventreeAPI.requestCategoryInfo(cleanURL(invURL), apiKey,null);
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            return;
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            return;
        }
        ArrayList<Category> ptt = ivl.categories;
        ptt.clear();
        Category sl;
        for (int i = 0 ; i < jso.length(); i++) {
           JSONObject obj = jso.getJSONObject(i);
           sl = new Category();
           int id = obj.getInt("pk");
            String name = obj.getString("name");
            int level = obj.getInt("level");
            sl.setId(id);
            sl.setName(name);
            sl.setLevel(level);
            ptt.add(sl);
            
        }
    }
    private void updatePartTemplateSourceList(){
        //HashMap<String, String> m = new HashMap<>();
        //m.put("is_template", "true");
        JSONArray jso;
        try {
           jso = InventreeAPI.requestPartInfo(cleanURL(invURL), apiKey,null);
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            return;
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            return;
        }
        ArrayList<PartItem> ptt = ivl.partTemplate;
        ptt.clear();
        PartItem pi;
        for (int i = 0 ; i < jso.length(); i++) {
            JSONObject obj = jso.getJSONObject(i);
            pi = new PartItem();           
            pi.setId(obj.getInt("pk"));
            pi.setName(obj.getString("name"));
            if(obj.has("IPN") && String.valueOf(obj.get("IPN")) != "null")
                pi.IPN= String.valueOf(obj.get("IPN"));
            if(obj.has("variant_of") && String.valueOf(obj.get("variant_of")) != "null")
                pi.partTemplate= obj.getInt("variant_of");
            pi.isTemplate = obj.getBoolean("is_template");
            ptt.add(pi);
        }
    }
   /**Mise à jour de la liste des lieux de stockage
    * met à jour les items Stocklocation dans InventreeLists
    */ 
    private void updateStockLocation(){
       JSONArray jso;
       try {
           jso = InventreeAPI.getStockLocation(cleanURL(invURL), apiKey);
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            return;
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            return;
        }
       
       ArrayList<StockLocation> sll = ivl.stocklocation;
       sll.clear();
       StockLocation sl;
       
       for (int i = 0 ; i < jso.length(); i++) {
           JSONObject obj = jso.getJSONObject(i);
           sl = new StockLocation();
           int id = obj.getInt("pk");
            String name = obj.getString("name");
            int level = obj.getInt("level");
            sl.setId(id);
            sl.setName(name);
            sl.setLevel(level);
            sll.add(sl);
            
        }
       
       //System.out.println(this+" \n     sl :"+ivl.stocklocation);
       
    }    
    /**Update manufacturer list from serveur
     * 
     */
    
    private void updateManufacturer(){
        JSONArray jso;
        
        HashMap m = new HashMap();
        m.put("is_manufacturer", "true");
        try {
           jso = InventreeAPI.requestCompanyInfo(cleanURL(invURL), apiKey,m);
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            return;
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            return;
        }
       
       ArrayList<CompanyItem> sll = ivl.manufacturers;
       sll.clear();
       CompanyItem sl;
       
       for (int i = 0 ; i < jso.length(); i++) {
           JSONObject obj = jso.getJSONObject(i);
           sl = new CompanyItem();
           int id = obj.getInt("pk");
            String name = obj.getString("name");
            
            sl.setId(id);
            sl.setName(name);
            sl.is_manufacturer =true;
            sll.add(sl);
            
        }
    }
    
   /**Update Supplier list from serveur
    * 
    */
    
    private void updateSupplier(){
        JSONArray jso;
        
        HashMap m = new HashMap();
        m.put("is_supplier", "true");
        try {
           jso = InventreeAPI.requestCompanyInfo(cleanURL(invURL), apiKey,m);
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            return;
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            return;
        }
       
       ArrayList<CompanyItem> sll = ivl.suppliers;
       sll.clear();
       CompanyItem sl;
       
       for (int i = 0 ; i < jso.length(); i++) {
           JSONObject obj = jso.getJSONObject(i);
           sl = new CompanyItem();
           int id = obj.getInt("pk");
            String name = obj.getString("name");
            sl.setId(id);
            sl.setName(name);
            sl.is_manufacturer =true;
            sll.add(sl);
            
        }
    }
    
    // initialisation à partir de l'ini
    /**
     * Initialisation from data stored in the ini file.
     */
    public void initData(){
        
        this.updateURL(model.getIniValue(CONSTANT.SERVEUR_PARAM_HEAD, CONSTANT.SERVEUR_PARAM_URL));
        this.updateUserName(model.getIniValue(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_NAME));
        this.updateForceHTTP(Boolean.valueOf(model.getIniValue(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_FORCEINSECURE)));
        
        String isPass = model.getIniValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_SAVE_PASS, "false");
        
         if(apiKey == null)// => donc on est pas encore connecté
             this.updatePassword(model.getIniValue(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_PASS, true));
   
    }

    
    /* --------------- Event Management -------------- */
    @Override
    public void eventRecept(iEvent e) {
        if(!e.type.equals(IniEvent.INI_CHANGED))
            return;
        this.initData();
    }
    
    

    // ========================= Retrouver les information sur un stockItem  ===================== //
    // =========================================================================================== //
    
    /** Retrieve information in Serveur for stocitem
     * it qill search for EAN/barcode via barcode API in server, if not foudn it will look into Supplier SKU and then manufaturer MPN
     * If not found return unknown part
     * else will find Part Information
     * 
     * @param si stockitem to be processed
     */
    public void updateStockItemData(StockItem si,String forceStockLoc){
        if(si.EAN == null){
            si.setStatus(CONSTANT.STATUS_ERROR_EAN, "No EAN defined");
            return;
        }
            
        // reinitialise les datat du SI
        si.setAsNewItem();
        JSONArray jso;
        JSONObject obj;
        PartItem pi = new PartItem();
        si.partitem = pi;
         try {
             // test sur API barcode
             obj = InventreeAPI.getBarcodeInfo(cleanURL(invURL), apiKey,si.EAN);
             
             
             // If not found in barcode, we search in MPN and SKU
             if(obj == null || obj.has("error") ){
                 jso = testBarcodeOnInternal(si);
                if (jso.length() >0 ){
                    obj = jso.getJSONObject(0);
                    pi.setId(obj.getInt("part"));
                    updatePartItemData(si, forceStockLoc);
                    return;
                 }
             }else{
                 // get type of element with barcode : 
                 if(obj.has("part")){// si c'est une part
                     pi.setId(obj.getJSONObject("part").getInt("pk"));
                     updatePartItemData(si, forceStockLoc);
                     return;
                 }else if(obj.has("supplierpart")){// si c'est un fournisseur
                     Integer pk = obj.getJSONObject("supplierpart").getInt("pk");
                     obj=InventreeAPI.requestPartCompanyInfo(cleanURL(invURL), apiKey,pk);
                     System.out.println("Supplier info :"+obj);
                    if (obj != null ){
                        pi.setId(obj.getJSONObject("part_detail").getInt("pk"));
                        updatePartItemData(si, forceStockLoc);
                        return;
                     }
                     
                 }else if(obj.has("stockitem")){// si c'est un stockitem
                     Integer pk = obj.getJSONObject("stockitem").getInt("pk");
                     
                     obj=InventreeAPI.requestStockItemInfo(cleanURL(invURL), apiKey, pk);
                     if (obj != null ){
                        pi.setId(obj.getJSONObject("part_detail").getInt("pk"));
                        updatePartItemData(si, forceStockLoc);
                        return;
                     }
                 }
             }
               
        } catch (AuthenticationException ex) {
            si.setStatus(CONSTANT.STATUS_NEW_ITEM);
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            return;
        } catch (IOException ex) {
            si.setStatus(CONSTANT.STATUS_NEW_ITEM);
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            return;
        }
        // if nothing has been found => new part
        if (si.partitem.getId()==0){
            si.setStatus(CONSTANT.STATUS_NEW_PART);
            model.updateStockItem(si);
        } 
    }
    /**perform search in MPN and SKU for EAN
     * 
     * @param si : the stockitem
     * @return
     * @throws AuthenticationException
     * @throws IOException 
     */
    private JSONArray testBarcodeOnInternal(StockItem si) throws AuthenticationException, IOException{
        JSONArray jso;
        HashMap m = new HashMap();
        m.put("MPN", si.EAN);
        jso = InventreeAPI.requestManufacturerInfo(cleanURL(invURL), apiKey,m);
        if(jso.length() == 0){
               m.clear();
               // test Supplier
                m.put("SKU", si.EAN);
                jso = InventreeAPI.requestSupplierInfo(cleanURL(invURL), apiKey,m);
              
           }
        return jso;       
    }
    /**
     * If Part is found corresponding to the barcode, try to update some data from Inventree server
     * 
     * @param si
     * @param forceStockLoc 
     */
    public void updatePartItemData(StockItem si,String forceStockLoc){
        if(si.partitem.getId() == 0){
            si.setStatus(CONSTANT.STATUS_NEW_ITEM);
            model.updateStockItem(si);
            return;
        }
        //System.out.println(this.getClass()+" updatePartItemData ");
        JSONArray jso;
        JSONObject obj;
        PartItem pi = si.partitem;
        try {
           // find item part from its id previously found
           jso = InventreeAPI.requestPartInfo(cleanURL(invURL), apiKey, pi.getId());
           //System.out.println(this.getClass()+" jso length "+jso.length());
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            return;
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            return;
        }
        // si on a une touche
        if (jso.length() >0 ){
            obj = jso.getJSONObject(0);
            pi.setId(obj.getInt("pk"));
            if(obj.get("IPN")!=JSONObject.NULL)
                pi.IPN = obj.getString("IPN");
            pi.setName(obj.getString("name"));
            updateStockInfo(si, forceStockLoc);
        }else{
            si.setStatus(CONSTANT.STATUS_NEW_ITEM);
            model.updateStockItem(si);
        } 
        
    }
    /**
     * If part is found try to find stock information
     * compar batch number, location and expiricy date
     * @param si : the stock item searched 
     * @param forceStockLoc params to force distant location instead of current scan parameter location
     */
    public void updateStockInfo(StockItem si,String forceStockLoc){
        //System.out.println(this.getClass()+" updateStockInfo ");
        JSONArray jso;
        JSONObject obj;
        PartItem pi = si.partitem;
        HashMap m = new HashMap();
        m.put("part", pi.getId());
        if(si.batch != null)
            m.put("batch", si.batch);
        /*if(si.expiry_date != null){
            String d = si.expiry_date.format(DateTimeFormatter.ofPattern("yyLLdd"));
            m.put("expiry_date", d);
        }*/
         try {
           // test Supplier
           jso = InventreeAPI.requestStockInfo(cleanURL(invURL), apiKey, m);
          
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            return;
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            return;
        }
         
         if (jso.length() == 0 ){// if no item correspond to the search params
             si.setStatus(CONSTANT.STATUS_NEW_ITEM);
             model.updateStockItem(si);
             return;
         }
         // 1 or more items founds
         Boolean comp;
         for (int i = 0 ; i < jso.length(); i++) {
           comp = true;
           obj = jso.getJSONObject(i);
           String batch = obj.getString("batch");
         
           int locId = (obj.get("location") == JSONObject.NULL ? 0 : obj.getInt("location") );
           String expDate = String.valueOf(obj.get("expiry_date"));
           // the comparaison operators
           comp = comp && (si.batch == null  ? batch == "" : si.batch.equals(batch));
           if(forceStockLoc.equals(CONSTANT.SCAN_FORCE_LOC_LOCAL))
               comp = comp && (si.stocklocation.getId() == locId);
           if(si.expiry_date != null){
               comp = comp && UTILS.formatDate(si.expiry_date, "yyyy-LL-dd").equals(expDate);
           }
            
           if(comp){
               si.setId(obj.getInt("pk"));
               si.quantityInStock = obj.getInt("quantity");
                if(forceStockLoc.equals(CONSTANT.SCAN_FORCE_LOC_DISTANT))
                    si.stocklocation.setId(locId);
                    updateLocationData(si);
               break;
           }
            
        }
          if (si.getId() == 0 ){// if no item correspond to the search params
             si.setStatus(CONSTANT.STATUS_NEW_ITEM);
         }else{
              si.setStatus(CONSTANT.STATUS_ITEM_FOUND);
          }
           model.updateStockItem(si); 
    }
    
    /**
     * Retrieve location information if stock is found
     * @param si : instance of StockItem 
     */
    protected void updateLocationData(StockItem si){
        if(si.stocklocation.getId()==0)
            return;
        
        JSONArray jso;
        JSONObject obj;
        try {
           jso = InventreeAPI.getStockLocation(cleanURL(invURL), apiKey, si.stocklocation.getId());
          
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            return;
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            return;
        }
         if (jso.length() == 0 ){// if no item correspond to the search params
             si.setStatus(CONSTANT.STATUS_SEND_ERROR);
             model.updateStockItem(si);
             return;
         }
        obj = jso.getJSONObject(0);
        int id = obj.getInt("pk");
        String name = obj.getString("name");
        int level = obj.getInt("level");
        si.stocklocation.setName(name);
        si.stocklocation.setLevel(level);
            
    }
    
    // ========================= ENVOI stockItem  VERS SERVEUR  ============================ //
    // =========================================================================================== //
    /**
     * Method to start the send process of a stockItem
     * @param si 
     */
    public void sendStockItem(StockItem si){
        if(si==null){
            model.stopSending();
            return;
        }
        boolean status= true;

        // check si part trouvé mais pas d'item dans le stock
        if(si.partitem != null && si.getId() == 0) {// si il y a une part mais pas d'id
            status = status && sendAddStockItem(si);
        }
        // si item trouvé dans un stock => à mettre à jour
        if( si.getId() != 0){
            status = status && sendUpdateStock(si);
        }
        // check send status
        if(status){
            si.setStatus(CONSTANT.STATUS_SEND);
        }else{
            si.setStatus(CONSTANT.STATUS_SEND_ERROR);
        }
        
        model.itemSended(si);
    }
    /**
     * Add a distant StockItem from a StockItem instance
     * @param si
     * @return 
     */
    private boolean sendAddStockItem(StockItem si){
        JSONObject jso = createAddItemJSON(si);
        
        int status;
         try {
           status = InventreeAPI.addItemToStock(cleanURL(invURL), apiKey, jso);
           return InventreeAPI.check(status);
      
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            return false;
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            return false;
        }
    }
    /**
     * Update a distant StockItem from a StockItem instance
     * @param si
     * @return 
     */
    private boolean sendUpdateStock(StockItem si){
       HashMap m = new HashMap();
        m.put(si.getId(), si.quantity);
        int status = 0;
        try {
           switch(si.action){
               case CONSTANT.MODE_ADD:
                   status = InventreeAPI.addToStockItem(cleanURL(invURL), apiKey, m);
                   break;
               case CONSTANT.MODE_REMOVE:
                   status = InventreeAPI.removeToStockItem(cleanURL(invURL), apiKey, m);
                   break;
               case CONSTANT.MODE_TRANSFERT:
                   status = InventreeAPI.transfertItemToStock(cleanURL(invURL), apiKey, createTransfertItemJSON(si));
                   break;
           }
           /*if(CONSTANT.MODE_ADD.equals(si.action)){
               status = InventreeAPI.addToStockItem(cleanURL(invURL), apiKey, m);
           }else{
               status = InventreeAPI.removeToStockItem(cleanURL(invURL), apiKey, m);
           }*/
           return InventreeAPI.check(status);
      
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            return false;
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            return false;
        }
    }
    
    /**Create a part
     * 
     * @param si ths stock item to link with
     * @param jso the JSON of part description
     * @param assignToPart : if barcode should be assigned to the part
     * @param assigToSupplier : if barcode should be assigned to the Supplier item
     */
    public void createPart(StockItem si, JSONObject jso, Boolean assignToPart, Boolean assigToSupplier){
        JSONObject newPart = null;
         try {
           newPart = InventreeAPI.addPart(cleanURL(invURL), apiKey, jso);
        } catch (AuthenticationException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
        } catch (IOException ex) {
            model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
        }
         if (newPart != null){
            si.partitem.IPN = newPart.getString("IPN");
            si.partitem.setName(newPart.getString("name"));
            si.partitem.setId(newPart.getInt("pk"));
            si.setStatus(CONSTANT.STATUS_NEW_ITEM);
         }
         
         // assign barcode to part
         JSONObject assJSO;
         if(assignToPart){
             assJSO = new JSONObject();
             assJSO.put("part", si.partitem.getId());
             assJSO.put("barcode", si.EAN);
            try {
                Integer status = InventreeAPI.assignBarcode(cleanURL(invURL), apiKey, assJSO);
            }catch (AuthenticationException ex) {
                model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            } catch (IOException ex) {
                model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            }
         }
         // assign barcode to Supplier part
         if(assigToSupplier){
             HashMap<String, String> search = new HashMap<>();
             search.put("part", String.valueOf(si.partitem.getId()));
            try {
                JSONArray res = InventreeAPI.requestSupplierInfo(cleanURL(invURL), apiKey, search);
                if(res != null){
                    assJSO = new JSONObject();
                    assJSO.put("supplierpart",  res.getJSONObject(0).getInt("pk"));
                    assJSO.put("barcode", si.EAN);
                    Integer status = InventreeAPI.assignBarcode(cleanURL(invURL), apiKey, assJSO);
                }
            }catch (AuthenticationException ex) {
                model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            } catch (IOException ex) {
                model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            } 
         }
         
         
         model.partCreated(si);
    }
     /**
      * Link the barcode to an existing item
     * @param si ths stock item to link with
     * @param jso the JSON of part description
     * @param assignToPart : if barcode should be assigned to the part
     * @param assigToSupplier : if barcode should be assigned to the Supplier item
     */
    public void linkPart(StockItem si, JSONArray jsa, Boolean assignToPart, Boolean assigToSupplier){
        boolean status = true;
        JSONObject suppPart=null;
        JSONObject assJSO;
        if(!jsa.isNull(0)){
            try {
                suppPart = InventreeAPI.addSupplierPart(cleanURL(invURL), apiKey, (JSONObject) jsa.get(0));
            } catch (AuthenticationException ex) {
                 model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            } catch (IOException ex) {
                model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            }
            if(suppPart == null)
                status = false;
        }
        if(!jsa.isNull(1)){
            int manStatus = 0;
            try {
                manStatus = InventreeAPI.addManufacturerPart(cleanURL(invURL), apiKey, (JSONObject) jsa.get(1));
            } catch (AuthenticationException ex) {
                 model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            } catch (IOException ex) {
                model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            }
            if( manStatus == 0)
                status = false;
        }
        
        if(assignToPart && si.partitem.getId() !=0){
            assJSO = new JSONObject();
            assJSO.put("part",  si.partitem.getId());
            assJSO.put("barcode", si.EAN);
            
            try {
                Integer statusBC = InventreeAPI.assignBarcode(cleanURL(invURL), apiKey, assJSO);
            } catch (AuthenticationException ex) {
                 model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            } catch (IOException ex) {
                model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            }
        }
        
        if(assigToSupplier && suppPart != null){
            assJSO = new JSONObject();
            assJSO.put("supplierpart",  suppPart.getInt("pk"));
            assJSO.put("barcode", si.EAN);
            
            try {
                Integer statusBC = InventreeAPI.assignBarcode(cleanURL(invURL), apiKey, assJSO);
            } catch (AuthenticationException ex) {
                 model.setConnectionStatus(Boolean.FALSE, CONSTANT.AUTH_ERROR);
            } catch (IOException ex) {
                model.setConnectionStatus(Boolean.FALSE, CONSTANT.CONN_ERROR);
            }
        }
        
        model.linkCreated(si, status);
        
            
    }
    // ===================== UTILITAIRE ==============//
    /**
     * Create a json object used to described the StockItem to be added
     * @param si
     * @return 
     */
    public JSONObject createAddItemJSON(StockItem si){
        
        JSONObject jso = new JSONObject();
        jso.put("part", si.partitem.getId());
        jso.put("location", si.stocklocation.getId());
        jso.put("quantity", si.quantity);
        if(si.batch != null)
            jso.put("batch", si.batch);
        if(si.expiry_date !=null)
            jso.put("expiry_date", si.expiry_date.toString());
       
        
        return jso;
    }
    
    /**
     * create the json for transfert api
     * @param si
     * @return 
     */
    public JSONObject createTransfertItemJSON(StockItem si){
        
        JSONObject jso = new JSONObject();
        JSONObject item = new JSONObject();
        JSONArray items = new JSONArray();
        
        
        item.put("pk", si.getId());
        item.put("quantity", si.quantity);
        items.put(item);
        jso.put("items", items);
        jso.put("location", si.transfertLocation.getId());      
        
        return jso;
    }
    
    /** clearURL : to remove protocole and leading /
     * to force https protocole
     * 
     * @param url url to be cleaned
     * @return url without protocol and without '/' at the end
     */
    public String cleanURL(String url){
        if(forceHTTP){
            return PROTOCOLE_FORCE_NON_SECURE.concat(cleanProtPattern.matcher(url).replaceAll(""));
        }
         return PROTOCOLE.concat(cleanProtPattern.matcher(url).replaceAll(""));
    }
}


/*
try {
           
} catch (AuthenticationException ex) {
    
} catch (IOException ex) {
    
}
*/