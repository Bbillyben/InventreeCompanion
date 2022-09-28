/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package barcodeDecoder;

import Inventree.item.PartItem;
import Inventree.item.StockItem;
import barcode.barcode;
import data.CONSTANT;
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
    protected Set<String> availableItem = Set.of(
            "part",
            "stockitem",
            "supplierpart"
    );
    
    @Override
    public boolean isCommand(){
        for(String key : cmd.keySet()){
            if(availableCmd.contains(key))
                return true;
        }
        return false;
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
            for(String key : jso.keySet()){
                isAvailable = isAvailable || availableItem.contains(key);
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
        barcode bc = new barcode();
        bc.code=cmd.toString();
        bc.type = InternalBarcodeDecoder.type;
        bc.EAN = CONSTANT.INTERNAL;
        return bc;
    }

    @Override
    public String getBarcodeString() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void processStockItem(StockItem si) {
        si.barcode = this.getBarcode();
        if(cmd.has("part")){
            if(si.partitem == null)
                si.partitem = new PartItem();
            si.partitem.setId(cmd.getInt("part"));
        }
        if(cmd.has("stockitem")){
            si.setId(cmd.getInt("stockitem"));
        }
        /*if(cmd.has("supplierpart")){
            
        }*/
        si.quantity = 1;
        si.EAN = CONSTANT.INTERNAL;
    }
    
}
