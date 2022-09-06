/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barcodeDecoder;

import Inventree.item.StockItem;
import barcode.barcode;
import java.lang.String;

/**
 *
 * @author legen
 */
public abstract class BarcodeDecoder {
    protected String type;
    
    public boolean isSupported(String bc){
        return false;
    }
    
    public abstract void decodeBarcode(String bc);
    public void decodeBarcode(String bc, boolean useQ){
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
