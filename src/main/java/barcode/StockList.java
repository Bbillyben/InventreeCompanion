/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barcode;

import Inventree.item.StockItem;
import Inventree.item.StockLocation;
import data.CONSTANT;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author legen
 */
public class StockList implements Serializable{
  private final ArrayList<StockItem> bList;
    
    public StockList(){
        bList = new ArrayList<>();
    }
    
   /**return the next StockItem with the status set as status param
    * 
    * @param status the status from CONSTANT that is looked for
    * @return 
    */
    public StockItem getNext(String status){
        StockItem si;
        for(int i=0; i<bList.size();i++){
            si=bList.get(i);
            if(si.getStatus() == status || (CONSTANT.SENDABLE.equals(status) && CONSTANT.SENDABLE_STATUS.contains(si.getStatus())))
                return si;
        }
        return null;
    }
    
    /**Get the ArrayList of StockItem corresponding to a Set of STATUS
     * 
     * @param statusSet a set of CONSTANT.STATUS_* to be returned
     * @return 
     */
    public ArrayList<StockItem> getListOf(Set statusSet){
        ArrayList<StockItem> re = new ArrayList<>();
        for(StockItem si : bList){
            if(statusSet.contains(si.getStatus())){
                re.add(si);
            }
        }
        return re;
    }
    /**
     * Get a list of StockItem corresponding to a given status
     * @param statusSet : the status searched
     * @return 
     */
    public ArrayList<StockItem> getListOf(String statusSet){
        ArrayList<StockItem> re = new ArrayList<>();
        for(StockItem si : bList){
            if(statusSet.equals(si.getStatus())){
                re.add(si);
            }
        }
        return re;
    }
    
    /**
     * Add a stock item to the list     * 
     * @param si 
     */
    public void add(StockItem si){
        bList.add(si);
    }
    /**
     * remove a stockitem from the list
     * @param si 
     */
    public void remove(StockItem si){
        bList.remove(si);
    }
    /**Clear the StockItem List
     * 
     */
    public void clear(){
        bList.clear();
    }
    /**Get the ArrayList of StockItem
     * 
     * @return the ArrayList of Stock Item
     */
    public ArrayList<StockItem> getList(){
        return bList;
    }
    // -------------------------------- Creation d'un item pour l'export -------------------- //
    // -------------------------------------------------------------------------------------- //
    
    /**
     * Generate a Bi dimensional array of the list to be exported
     * @return 
     */
    public ArrayList<String[]> getExportData(){
        ArrayList<String[]> expD = new ArrayList<>();
        
        String[] tmp = new String[CONSTANT.EXPORT_COL.length+1];
        System.arraycopy(CONSTANT.EXPORT_COL, 0, tmp, 0, CONSTANT.EXPORT_COL.length);
        
        expD.add(tmp);
        Object ob;
        for(StockItem si : bList){
            tmp = new String[CONSTANT.EXPORT_COL.length+1];
            for(int i = 0; i<CONSTANT.EXPORT_COL.length; i++){
                ob=si.get(CONSTANT.EXPORT_COL[i], CONSTANT.EXPORT_COL_CLASS[i]);
                if(ob==null)
                    continue;
                if(ob.getClass() == StockLocation.class){
                    tmp[i]=((StockLocation) ob).getName();
                }else if(ob.getClass() == LocalDate.class){
                    tmp[i]=((LocalDate) ob).format(DateTimeFormatter.ISO_DATE);
                }else{
                    tmp[i]=ob.toString();
                }
            }
            expD.add(tmp);
        }
        return expD;
    }
    
}
