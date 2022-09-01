/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barcode;

import Inventree.item.StockItem;
import data.CONSTANT;
import java.io.Serializable;
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
    public ArrayList<StockItem> getListOf(String statusSet){
        ArrayList<StockItem> re = new ArrayList<>();
        for(StockItem si : bList){
            if(statusSet.equals(si.getStatus())){
                re.add(si);
            }
        }
        return re;
    }
    
    /// Fonction pour l'envoi
    /// fonction ajout suppression
    public void add(StockItem si){
        bList.add(si);
    }
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
    
    
    
}
