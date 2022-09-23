/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package barcodeDecoder;

import Inventree.item.StockItem;
import barcode.barcode;
import java.util.ArrayList;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author blegendre
 */
public class InternalBarcodeDecoder extends BarcodeDecoder  {
    protected static String type="InternalInventreeCommand";
    protected JSONObject cmd;
    
    protected Set<String> availableCmd = Set.of(
            "stocklocation"
    );
    
    @Override
    public boolean isCommand(){
        return true;
    }
    
    @Override
    public  boolean isSupported(ArrayList<String> bc){ 
        String bcStr = bc.get(0);
        try {
            JSONObject jso =  new JSONObject(bcStr);
            boolean isAvailable = false;
            for(String key : jso.keySet()){
                isAvailable = isAvailable || availableCmd.contains(key);
            }
            return isAvailable;
        } catch (JSONException ex) {
            return false;
        }
    }
    

    @Override
    public void decodeBarcode(ArrayList<String> bc) {
        if(!isSupported(bc)){
            cmd = null;
        }else{
            cmd = new JSONObject(bc.get(0));
        }

    }
    @Override
    public JSONObject getCommand(){
        return cmd;
    }

    @Override
    public barcode getBarcode() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getBarcodeString() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void processStockItem(StockItem si) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
