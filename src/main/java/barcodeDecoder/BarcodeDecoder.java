/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barcodeDecoder;

import Inventree.item.StockItem;
import barcode.barcode;
import java.util.ArrayList;
import org.json.JSONObject;

/**BarcodeDecoder abstract class to be extended for processing barcode
 * 
 * it has to be instanciated in data.KeyHandlerManager in variable : 
 * private static BarcodeDecoder[] DECODERS
 * 
 * decoders are tested in the order of this array.
 *
 * @author legen
 */
public abstract class BarcodeDecoder {
    //protected String type="NONE";// strig representing type of barcode
    
    /**To test wether this decoder is able to handle this barcode
     * 
     * @param bc an array list of barcodes scanned, can be more than 1 if isMultiple() return true; (to aggreagate multiple scan in one)
     * @return 
     */
    public boolean isSupported(ArrayList<String> bc){
        return false;
    }
    /**to set the barcode decoding as a commande (used for Internal barcode for instance)
     * 
     * @return 
     */
    public boolean isCommand(){
        return false;
    }
    /**get the type of barcode
     * 
     * @return 
     */
    public String type(){
        return "None";
    }
    /**if is commande, return the command in the appropriate format
     * the command  is dispatche by the keyhandler with a BarcodeEvent, landing in Model.manageCommand method.
     * 
     * @return 
     */
    public JSONObject getCommand(){
        return null;
    }
    
    /**Launch the decoding of the barcode by the decoder
     * To be implemented to store datas for StocItem generation
     * 
     * @param bc 
     */
    public abstract void decodeBarcode(ArrayList<String> bc);
    /**Launch the decoding of the barcode by the decoder, added of a parameter to specify whether the quantity encoded in the barcode should be used
     * 
     * 
     * @param bc
     * @param useQ 
     */
    public void decodeBarcode(ArrayList<String> bc, boolean useQ){
        this.decodeBarcode(bc);
    }
    /**Get the barcoode instance filled with the decoded value(s)
     * 
     * @return 
     */
    public abstract barcode getBarcode();
    /**get the barcode String representation of the scanned barcode
     * 
     * @return 
     */
    public abstract String getBarcodeString();
    public String getBarcodeString(char sep){
        return getBarcodeString();
    }
    
    /**Get the prefix of the barcode if any (first char should be "]")
     * 
     * @param bcStr : the barcode string
     * @param pSize : int - length of the prefix size, default 2
     * 
     * @return String prefix, including "]" char
     */
    public String getPrefix(String bcStr){
        return getPrefix(bcStr, 3);
    }
    public String getPrefix(String bcStr, int pSize){
        if (bcStr.charAt(0) != ']')return "";
        return bcStr.substring(0,pSize);
    }
    /**Get the the barcode without prefix if any (first char should be "]")
     * 
     * @param bcStr : the barcode string
     * @param pSize : int - length of the prefix size, default 2
     * 
     * @return String prefix, including "]" char
     */
    public String removePrefix(String bcStr){
        return removePrefix(bcStr, 2);
    }
    public String removePrefix(String bcStr,  int pSize){
        if (bcStr.charAt(0) != ']')return bcStr;
        return bcStr.substring(pSize+1);
    }
    /**To specify if the keyhandler has to wait before launching the process of barcode decoding
     * 
     * 
     * @return 
     */
    public Boolean isMultiple(){
        return false;
    }
    /**to update a StockItem instance with the barcode information
     * 
     * should set 
     *   - the barcode (stockitem.instance)
     *   - the StockItem Identifyer (stockitem.EAN)
     *   - the StockItem Quantity to add (stockitem.quantity)
     * additionnal stockitem variable could be set (batch, expiry_date, partitem,...)
     * 
     * @param si 
     */
    public abstract void processStockItem(StockItem si);

    private char CHR(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
   
}
