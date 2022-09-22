/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barcodeDecoder;

import Inventree.item.StockItem;
import barcode.barcode;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author legen
 */
public abstract class BarcodeDecoder {
    protected static String type;// strig representing type of barcode
    
    public boolean isSupported(ArrayList<String> bc){
        return false;
    }
    public boolean isCommand(){
        return false;
    }
    public String getType(){
        return type;
    }
    public JSONObject getCommand(){
        return null;
    }
    
    public abstract void decodeBarcode(ArrayList<String> bc);
    public void decodeBarcode(ArrayList<String> bc, boolean useQ){
        this.decodeBarcode(bc);
    }
    public abstract barcode getBarcode();
    public abstract String getBarcodeString();
    public String getBarcodeString(char sep){
        return getBarcodeString();
    }
    public Boolean isMultiple(){
        return false;
    }
    
    public abstract void processStockItem(StockItem si);
    
   
}
