/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package utils;

import Inventree.item.StockItem;
import data.CONSTANT;

/**
 *
 * @author blegendre
 */
public class ItemCheck {
    
    /**Check validity of StockItem action vs server info before sending
     * and update status in consequence
     * 
     * @param si the stock item to check
     */
    public static void check(StockItem si){
        
        // if action == add => nothing to do
        if(CONSTANT.MODE_ADD.equals(si.action) )
            return;
        
        // Si Action remove or transfert
        // check if Item is recognize => id >0
        if(si.getId() == 0){// if no id => no Stock Item found
            si.setStatus(CONSTANT.STATUS_ERR_NO_ITEM_REMOVE);
            return;
        }
        // si la quantité à remove est > à quantité en stock
        if(si.quantity > si.quantityInStock){
            si.setStatus(CONSTANT.STATUS_ERR_QUANTITY);
            return;
        }
        
        // spécifique transfert
        if(CONSTANT.MODE_TRANSFERT.equals(si.action))
            if(checkTransfert(si))
                return;
        
    }
    private static boolean checkTransfert(StockItem si){
        if(si.stocklocation == null || si.transfertLocation == null){
            si.setStatus(CONSTANT.STATUS_ERR_LOC);
            return true;
        }        
        if(si.stocklocation.getId()==si.transfertLocation.getId()){
            si.setStatus(CONSTANT.STATUS_ERR_SAME_LOC);
            return true;
        }
        return false;
    }
}
