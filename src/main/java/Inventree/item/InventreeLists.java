/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package Inventree.item;

import barcode.StockList;
import data.CONSTANT;
import data.UTILS;
import java.util.ArrayList;

/**Singleton Class to reference Parameters from Serveur (eg stock location, category, ...)
 * and stock items list scanned
 * @author blegendre
 */
public class InventreeLists {
    private static InventreeLists ivl;
    
    public ArrayList<StockLocation> stocklocation;
    public ArrayList<CompanyItem> manufacturers;
    public ArrayList<CompanyItem> suppliers;
    public ArrayList<PartItem> partTemplate;
    public ArrayList<Category> categories;
    public StockList stockList;
    
    // généraiton du singleton
    public static InventreeLists getInstance(){
        if(ivl == null)
            ivl = new InventreeLists();
        return ivl;
    }
    public static void setInstance(InventreeLists ii){
        ivl = ii;
    }
    
    //////////////////////////////////////////////
    public InventreeLists(){
        stocklocation = new ArrayList<>();
        manufacturers = new ArrayList<>();
        suppliers = new ArrayList<>();
        partTemplate = new ArrayList<>();
        categories = new ArrayList<>();
        
        stockList = new StockList();
    }
    
    public void cleanStockList(){
        stockList.clear();
    }
    
    
    // ---------------------- GESTION DE LA LISTE DES STOCKITEM -------------------- //
    // ----------------------------------------------------------------------------- //
    
    /** Add a stockitem to stock item list if new or add quantity to corresponding stock item in the list
     * 
     * compare EAN, batch, expiry date
     * if forceStockLocation == true, compare StockLocation.id
     * 
     * @param si : StockItem to add
     * @param forceStockLocation : parameter to force corresponding location
     * @return 
     */
    public StockItem addStockItem(StockItem si, String forceStockLocation){
        boolean isSame;
        for(StockItem tsi : stockList.getList()){
            if(! CONSTANT.MODIFIABLE_STATUS.contains(tsi.status))
                continue;
            isSame = true;
            isSame = isSame && (si.EAN == null ? tsi.EAN == null : UTILS.cleanBC(si.EAN).equals(UTILS.cleanBC(tsi.EAN)));
            isSame = isSame && (si.batch == null ? tsi.batch == null : si.batch.equals(tsi.batch));
            isSame = isSame && (si.expiry_date == null ? tsi.expiry_date == null : si.expiry_date.equals(tsi.expiry_date));
            //if(forceStockLocation == CONSTANT.SCAN_FORCE_LOC_LOCAL)
            isSame = isSame && (si.stocklocation.getId() == tsi.stocklocation.getId());
            isSame = isSame && (si.action == null ? tsi.action == null : si.action.equals(tsi.action));
            if(isSame){
                tsi.quantity += si.quantity;                    
                return tsi;
            }
            
        }
        si.setStatus(CONSTANT.STATUS_PENDING);
        stockList.add(si);
        return si;
    }
    
    
    
    
    // -------------------------------- Creation d'un item pour l'export -------------------- //
    // -------------------------------------------------------------------------------------- //
    
    public ArrayList<String[]> getExportData(){
        
        return stockList.getExportData();
    }
    
}
