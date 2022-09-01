/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import Inventree.item.StockItem;
import barcode.barcode;

/**
 *
 * @author blegendre
 */
public class BarcodeEvent extends iEvent {
    
    public static final String NEW_BARCODE = "new_barcode";
    public static final String EAN_128="ean_128";
    public static final String UNKOWN_BARCODE = "unknown_barcode";
    public static final String BCB_STATUS_UPDATE = "bcb_status_update";
    /**
     * the value of the barcode for the event to bring with
     */
    public barcode barcode;
    public StockItem stockitem;
    public String eventValue;
    public String[][] tableDesc;
    
    public BarcodeEvent(Object source) {
         super(source);
         barcode = null;
         type = null;
    }
     public BarcodeEvent(Object source, String EventName) {
         super(source);
         type = EventName;
         barcode = null;
    }
    public BarcodeEvent(Object source, String EventName, String eVal) {
         super(source);
         type = EventName;
         eventValue = eVal;
    }
     public BarcodeEvent(Object source, String EventName, barcode bc) {
         super(source);
         type = EventName;
         barcode = bc;
    }
     
    @Override
     public String toString(){
         String str = super.toString();
         str+=" - "+type+(barcode == null ? "" : "\n barcode :"+barcode);
         str+=(stockitem == null ? "" : "\n StockItem :"+stockitem);
         return str;
     }
   
   
}
