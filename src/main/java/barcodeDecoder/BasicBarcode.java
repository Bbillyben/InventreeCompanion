/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barcodeDecoder;

import Inventree.item.StockItem;
import barcode.barcode;

/**
 *
 * @author legen
 */
public class BasicBarcode extends BarcodeDecoder {
    private String barcodeStr;
    protected String type = "BASIC";
    
    public  boolean isSupported(String bc){
        return true;
    }
    
    @Override
    public void decodeBarcode(String bc) {
        barcodeStr = bc;
    }

    @Override
    public barcode getBarcode() {
        barcode bc = new barcode();
        bc.code = barcodeStr;
        bc.EAN = barcodeStr;
        bc.type = this.type;
        return bc;
    }

    @Override
    public String getBarcodeString() {
        return barcodeStr;
    }

    @Override
    public void processStockItem(StockItem si) {
        si.barcode = getBarcode(); 
        si.EAN = getBarcode().EAN; 
    }
    
}
