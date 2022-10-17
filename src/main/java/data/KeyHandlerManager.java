/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import Inventree.item.StockItem;
import barcodeDecoder.BarcodeDecoder;
import barcodeDecoder.BasicBarcode;
import barcodeDecoder.EAN128Decoder;
import barcodeDecoder.InternalBarcodeDecoder;
import events.BarcodeEvent;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextField;
import listeners.keyBarcodeListener;

/**Class dedicated to handle keyboard event from textfield or other Component 
 * to lanch barcode decodiong regarding a list of barcodedecoder
 *
 * @author blegendre
 */
public class KeyHandlerManager implements KeyListener, ActionListener {
    
    private List<Object> listeners;
    private String barcode;
    private ArrayList<String> barcodes;
    private ScheduledExecutorService ses;
    private int secondeToRelease;
    
    private List<keyBarcodeListener> bcListener;
    
    private boolean useQuantity=false;
    /**
     * list of barcodedecoder ''plugin'' to be challenged against raw barcode
     * to be updated with the list of barcode decoder extending BarcodeDecoder
     */
    private static BarcodeDecoder[] DECODERS=new BarcodeDecoder[]{
        new InternalBarcodeDecoder(),// for inventree internal barcode handling
        new EAN128Decoder(), // for EAN128/GS1-128 barcode
        new BasicBarcode() //DON'T REMOVE, it will set the alphanumeric code scanned as a the whole identifier, without extra processing
    };
    
    public KeyHandlerManager(){
        secondeToRelease = 1000;
         this.init();
    }
    public KeyHandlerManager(Double secToRel){
        secondeToRelease = (int) Math.round(secToRel*1000);
         this.init();
    }
    
     private void init(){
        //bc128Deco = new GS1Code128Data();
        barcodes = new ArrayList<>();
        listeners = new ArrayList<>();
        bcListener = new ArrayList<>();
        barcode = "";
        ses = Executors.newScheduledThreadPool(1);
    }
    /* setter et getter */
    public void setSecondeToRelease(Double secToRel){
        secondeToRelease = (int) Math.round(secToRel*1000);
    }
    /**define if quantity from barcode decoding is used
     * 
     * @param useQ 
     */
    public void setUserQuantity(boolean useQ){
        useQuantity=useQ;
    }
    
    /*       Ajout et suppression des objet à écouter     */
    public void addListener(Object keyObj){
        //System.out.println(" - add listener on "  + keyObj.getClass().getName());
        listeners.add(keyObj);
        
         switch(keyObj.getClass().getName()){
            case "javax.swing.JPanel": 
                ((Component) keyObj).addKeyListener(this);
                break;
            case "javax.swing.JTextField":
                ((JTextField) keyObj).addActionListener(this);
                break;
            default:
                System.out.println("Choix incorrect");
                break;
        }
    }
    public void removeListener(Component keyObj){
        keyObj.removeKeyListener(this);
        listeners.remove(keyObj);
    }
    
    public void removeAllListener(){
        listeners.forEach(bcl -> this.removeListener((Component) bcl));
    }
    
    /* gestion des barcode */
     private void handleBarcode() {
        // find right decoder
        BarcodeDecoder decoder = getDecoder(barcodes);
        if(decoder == null){
            return;
            //throw new Exception("Unknown barcode type : "+barcodeStr);
        }
        
        System.out.println("KeyHandlerManager / decoder : "+decoder);
        BarcodeEvent ev = new BarcodeEvent(this);
        decoder.decodeBarcode(barcodes, useQuantity);
        if(decoder.isCommand()){// if recognize as a command barcode
           ev.type=BarcodeEvent.BCB_COMMAND;
           ev.command = decoder.getCommand();
        }else{
            ev.type = BarcodeEvent.NEW_BARCODE;
            StockItem si = new StockItem();
            decoder.processStockItem(si);
            // clean current barcode 
            ev.stockitem = si;
            ev.barcode = si.barcode;
        }
        barcodes.clear();
        this.dispatchEvent(ev);
        
        //barcode = "";
    }
     /**Retrieve the first decoder that recognize the barcode type
      * from DECODER static array 
      * @param barcodeStr the barcode 
      * @return 
      */
     protected BarcodeDecoder getDecoder(ArrayList<String> barcodeStr){
         for(BarcodeDecoder bcd : DECODERS){
            if(bcd.isSupported(barcodeStr)){
                return bcd;
            }
        }
         return null;
     }
     
     /* méthode pour centraliser la prise en compte de nouveau caratères 
     * à partir de Panel ou jtextfield ou autre
     */
     private void handleChar(){
         if(barcodes == null || barcodes.size()<1)
             return;
        int secF = getDecoder(barcodes).isMultiple() ? secondeToRelease : 100;
        Runnable task2 = () -> this.handleBarcode();
        if(!ses.isTerminated())
            ses.shutdownNow();
        ses = Executors.newScheduledThreadPool(1);
        ses.schedule(task2, secF, TimeUnit.MILLISECONDS);
     }

    /*         Gestion des fonction d'écoute    */
    // pour les composant
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        barcode += c;
        if(e.getKeyCode()== KeyEvent.VK_ENTER){
            barcodes.add(barcode);
            barcode="";
            this.handleChar();
        }
            
            
    }
   // pour les JtextField
    @Override
    public void actionPerformed(ActionEvent e) {
       //System.out.println(" Action performed : " + e.toString());
       //barcode += e.getActionCommand();
       barcodes.add(e.getActionCommand());
       barcode = "";
       ((JTextField) e.getSource()).setText("");
        this.handleChar();
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    // poue les ecouteur
    public void addBarcodeListener(keyBarcodeListener kbl){
        bcListener.add(kbl);
        
    }
    public void removeBarcodeListener(keyBarcodeListener kbl){
        bcListener.remove(kbl);
        
    }
    private void dispatchEvent(BarcodeEvent e){
        bcListener.forEach(kbl -> kbl.processBarcode(e));
    }
    
    
    /* les implementation inutiles pour le moment */

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    

   
    
}


