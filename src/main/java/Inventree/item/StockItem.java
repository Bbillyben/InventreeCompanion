/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package Inventree.item;

import barcode.barcode;
import data.CONSTANT;
import java.time.LocalDate;

/**
 *
 * @author blegendre
 */
public class StockItem extends InventreeItem {
    
    public String batch;
    public LocalDate expiry_date;
    
    public int quantityInStock;
    public int quantity;
    
    
    // part
    public PartItem partitem;
    
    // location
    public StockLocation stocklocation;
    public StockLocation transfertLocation;
    
    // barcode
    public barcode barcode;
    public String EAN;
    
    
    public CompanyItem supplierComp;
    public CompanyItem manufacturerComp;
    
    // status au regard du traitement ici
    protected String status; // en process reussi echoue => voir CONSTANT
    public String statusDesc;
    public String action; // add / remove => voir CONSTANT
    
    public StockItem(){
        id = 0;
        quantityInStock = 0;
        quantity = 0;
    }
    
    public String getStatus(){
        return status;
    }
    public boolean setStatus(String stat){
        if(status == CONSTANT.STATUS_DELETED)
            return false;
        status = stat;
        return true;
    }
    public boolean setStatus(String stat, String desc){
        if(status == CONSTANT.STATUS_DELETED)
            return false;
        status = stat;
        statusDesc = desc;
        return true;
    }
    public void setAsNewItem(){
        id = 0;
        quantityInStock = 0;
        partitem = null;
        
                
    }
    // getter pour gestion des colonnes des table
    //"Barcode", "EAN","Name", "location", "quantity in stock"  , "quantity", "action", "status"
    /**
     * Method to get values shown in tables from class elements.
     * @param <T>
     * @param objName
     * @param type
     * @return 
     */
    public <T> T get(String objName, Class<T> type){
        //System.out.println(this.getClass()+ "   ======  require GET \n    -  onbjName :"+objName+"\n    - Class :"+type);
        switch(objName.toUpperCase()){
            case "BARCODE":
                if(barcode!=null)
                    return type.cast(barcode.code);
                break;
            case "EAN":
                 if(barcode!=null)
                    return type.cast(barcode.EAN);
                break;
            case "NAME":
                if(partitem!=null)
                    return type.cast(partitem.name);
                break;
            case "LOCATION":
                if(stocklocation!=null)
                    return type.cast(stocklocation);
                break;
            case "TRANSFERT LOCATION":
                //if(transfertLocation!=null)
                    return type.cast(transfertLocation);
                //break;
            case "QUANTITY IN STOCK":
                return type.cast(quantityInStock);
            case "QUANTITY":
                return type.cast(quantity);
            case "ACTION":
                /*if(CONSTANT.STATUS_DELETED.equals(this.status)){
                    return type.cast(CONSTANT.STATUS_DELETED);
                }*/
                return type.cast(action);
            case "STATUS":
                if(statusDesc != null)
                    return type.cast(status + ": "+statusDesc);
                return type.cast(status);
            case "BATCH":
                return type.cast(batch);
            case "EXPIRY DATE":
                return type.cast(expiry_date);
            case "IPN":
                if(partitem!=null)
                    return type.cast(this.partitem.IPN);
                break;
            case "SUPPLIER":
                if(supplierComp!=null)
                    return type.cast(this.supplierComp);
                break;
            case "MANUFACTURER":
                if( manufacturerComp != null)
                    return type.cast(this.manufacturerComp);
                break;
            case "IDS":
                if(partitem!=null && stocklocation != null )
                    return type.cast(this.id + "/ part "+this.partitem.id+" / loc "+stocklocation.getId());
                break;
            
        }
        return null;
    }
    /**
     * method to set values of class element from table editing
     * @param objName
     * @param value 
     */
     public void set(String objName, Object value){
        //System.out.println(this.getClass()+ "   ======  require SET \n    -  onbjName :"+objName+"\n    - Class :"+value);
        switch(objName.toUpperCase()){
            case "BARCODE":
                if(barcode!=null)
                    barcode.code = (String) value;
                break;
            case "EAN":
                 if(barcode!=null)
                    barcode.EAN = (String) value;
                break;
            case "NAME":
                if(partitem!=null)
                    partitem.name = (String) value;
                break;
            case "LOCATION":
                //if(stocklocation!=null)
                    stocklocation = (StockLocation) value;
                break;
            case "TRANSFERT LOCATION":
                 transfertLocation = (StockLocation) value;
                break;
            case "QUANTITY IN STOCK":
                quantityInStock =  (int) value;
                break;
            case "QUANTITY":
                quantity = (int) value;
                break;
            case "ACTION":
                action = (String) value;
                break;
            case "STATUS":
                status = (String) value;
                break;
            case "BATCH":
                //System.out.println(this.getClass()+" ================ >>>>>>  Set batch :"+value);
                batch = (String) value;
                break;
            case "EXPIRY DATE":
                //System.out.println(this.getClass()+" ================ >>>>>>  Set date :"+value);
                expiry_date = (LocalDate) value;
                break;
            case "SUPPLIER":
                //if(supplierComp!=null)
                    supplierComp = (CompanyItem) value;
                break;
            case "MANUFACTURER":
                //if(manufacturerComp!=null)
                    manufacturerComp = (CompanyItem) value;
                break;
            
        }
    }
    
    @Override
    public String toString(){
        String str = super.toString();
        str += "\n   - id :"+id;
        str += "\n   - batch :"+batch;
        str += "\n   - expiry_date :"+expiry_date;
        str += "\n   - quantityInStock :"+quantityInStock;
        str += "\n   - quantity :"+quantity;
        str += "\n   - status :"+status;
        return str;
    }
    
    
}
