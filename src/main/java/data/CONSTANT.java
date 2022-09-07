/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import Inventree.item.StockLocation;
import java.time.LocalDate;
import java.util.Set;
import javax.swing.JButton;

/**
 *
 * @author legen
 */
public class CONSTANT {
    // =================  FICHIER INI ================  //
    // ===============================================  //
    public static final String SERVEUR_PARAM_HEAD ="SERVEUR_SETTING";
    public static final String SERVEUR_PARAM_URL ="url";
    //public static final String BCB_PARAM_API ="api";
    public static final String USER_PARAM_HEAD ="USER_SETTING";
    public static final String USER_PARAM_NAME ="name";
    public static final String USER_PARAM_PASS ="pass";
    public static final String USER_PARAM_FORCEINSECURE ="force_http";
    
    public static final String PARAM_HEAD ="PARAMS";
    public static final String PARAM_secEAN ="seconde_to_EAN";
    public static final String PARAM_SAVE_PASS ="save_password";
    public static final String PARAM_AUTO_LOG ="auto_login";
    public static final String PARAM_FORCE_LOCATION ="force_location";
    
     public static final String SCAN_PARAM_HEAD ="SCAN";
     public static final String SCAN_PARAM_MODE="mode";
     public static final String SCAN_STOCK_LOC="stock_location";
     public static final String SCAN_PARAM_USE_QUANTITY="use_bc_quantity";
     
     public static final String SCAN_FILE_HEAD ="FILE";
     public static final String SCAN_FILE_SAVEONEXIT ="save_on_exit";
     
     
    // pour la synchro
     public static final String SCAN_FORCE_LOC_DISTANT="sync_dist";// force avec le Stocklocation serveur
     public static final String SCAN_FORCE_LOC_LOCAL="sync_local";// force avec le Stocklocation défini dans l'app
    
    // =================  NAVIGATION  / MENU  ================  //
    // =======================================================  //
    public static final String PAGE_PARAM = "Parameters";
    public static final String PAGE_SCAN = "Scan";
    public static final String PAGE_LOGIN = "login";
    public static final String PAGE_SEND = "send";
    
    public static final String PAGE_NULL = "nowhere";
    public static final String ACTION_LOGOUT = "logout";
    
    public static final String ACTION_UPDATE_PARAM = "update_param";
    public static final String ACTION_CLEAN_LIST = "clean_stocklist";
    public static final String ACTION_CHECK_ALL = "check_all";
    public static final String ACTION_REMOVE_DELETED = "remove_deleted";
    public static final String ACTION_SAVE = "save_list";
    
    public static final String ACTION_SEND_UPDATE = "send_update";
    public static final String ACTION_SEND_ITEM = "send_item";
    public static final String ACTION_SEND_PART = "send_part";
    public static final String ACTION_SEND_ALL = "send_all";

    public static final String ACTION_COPY_VALUE = "copy_value";
// =================  BARCODE  ================  //
    // ===============================================  //
    public static final String STATUS_PENDING = "pending";// en attente d'action => vers identification
    
    public static final String STATUS_ERROR_EAN = "error no EAN";// EAN n'est pas défini
    public static final String STATUS_SEND = "sended";
    public static final String STATUS_SEND_ERROR = "send_error";
    public static final String STATUS_ON_SENDING = "on_sending";
    
    public static final String STATUS_DELETED = "deleted";// item supprimé
    public static final String STATUS_NEW_ITEM = "new item";// item non identifié
    public static final String STATUS_NEW_PART = "new part";// part non identifié egalement
    
    public static final String STATUS_ON_UPDATE = "on update";// mise à jour des infos
    public static final String STATUS_ITEM_FOUND = "item found";// item identifié dans les stock
    
    public static final String STATUS_ERR_NO_ITEM_REMOVE = "Err: no item to remove";// si action remove et pas d'item stock identifié
    public static final String STATUS_ERR_QUANTITY = "Err: Stock quantity insufficient";//si action remove et que quantité stock < quantité demandé
    public static final String STATUS_ERR_SAME_LOC = "Err: location identical";//si action transfert et même location selectionées
    public static final String STATUS_ERR_LOC = "Err: No Location defined";// si un stocklocation n'est pas défini 
    
    public static final String SENDABLE = "sendable_item"; // utilisé pour tout les items qui sont envoyable au server, a utiliser avec le Set SENDABLE_STATUS
    
    public static final Set<String> MODIFIABLE_STATUS= Set.of(
        STATUS_PENDING, 
        STATUS_NEW_ITEM,
        STATUS_NEW_PART,
        STATUS_ITEM_FOUND,
        STATUS_ON_UPDATE,
        STATUS_ERR_NO_ITEM_REMOVE,
        STATUS_ERR_QUANTITY,
        STATUS_ERR_SAME_LOC,
        STATUS_ERR_LOC
    );
    public static final Set<String> SENDABLE_STATUS = Set.of(
        STATUS_ITEM_FOUND,
        STATUS_NEW_ITEM
    );
    public static final Set<String> ERROR_STATUS= Set.of(
        STATUS_ERROR_EAN,
        STATUS_SEND_ERROR,
        STATUS_ERR_NO_ITEM_REMOVE,
        STATUS_ERR_QUANTITY,
        STATUS_ERR_SAME_LOC,
        STATUS_ERR_LOC
    );
    public static final Set<String> DELETED_REMOVE_STATUS= Set.of(
        STATUS_DELETED,
        STATUS_SEND,
        STATUS_ON_SENDING,
        STATUS_ERROR_EAN,
        STATUS_SEND_ERROR,
        STATUS_ERR_NO_ITEM_REMOVE,
        STATUS_ERR_QUANTITY,
        STATUS_ERR_SAME_LOC,
        STATUS_ERR_LOC
    );
    // =================  CONNECTION  ================  //
    // ===============================================  //
    public static final String AUTH_ERROR = "Authentication Error, please check your credential";
    public static final String CONN_ERROR = "Unreachable or Wrong URL";
    public static final String INFO_MISSING = "Information missing";
    
    
    
    
    // =================  SCAN  ================  //
    // ===============================================  //
    
    public static final double SCAN_SEC_EAN = 2.0;
    // pendant le scan
    public static final String MODE_ADD = "add";
    public static final String MODE_REMOVE = "remove";
    public static final String MODE_TRANSFERT = "transfert";
     // =================  FILES  ================  //
    // ===============================================  //
    public static final String FILE_SAVE = "currentList.ser";
    
    
    
    
    // =====================  POUR LES TABLES =============  //
    // ====================================================  //
    
    // --------------- table de scan
    public static final String[] TAB_COL_SCAN =  new String[] {
        "Barcode", "EAN","batch","Expiry Date","Name", 
        "location", "transfert location", "IPN",
        "quantity in stock", "quantity", "action", "status",
        "remove"
    };
    public static final Set<String> TAB_COL_SCAN_EDIT =  Set.of(
           "batch","Expiry Date","location","transfert location", "remove","quantity"
    );
    public static final Class[] TAB_COL_SCAN_CLASS =  new Class[] {
        String.class,String.class, String.class,LocalDate.class, String.class, 
        StockLocation.class,StockLocation.class, String.class,
         Integer.class, Integer.class, String.class, String.class,
         JButton.class
    };
    
    // --------------- table de SEND - update 
    
    public static final Set<String> TAB_COL_UPDATE_EDIT =  Set.of(
           "location","transfert location","remove", "quantity"
    );
    // --------------- table de SEND - Add item 
    public static final String[] TAB_COL_ITEM =  new String[] {
        "Barcode", "EAN","batch","Expiry Date","Name", 
        "location", "IPN",
        "quantity", "action", "status",
        "remove"
    };
    public static final Set<String> TAB_COL_ITEM_EDIT =  Set.of(
          "batch","Expiry Date","location", "remove","quantity"
    );
    public static final Class[] TAB_COL_ITEM_CLASS =  new Class[] {
        String.class,String.class, String.class,LocalDate.class, String.class, 
        StockLocation.class, String.class,
         Integer.class, String.class, String.class,
         JButton.class
    };
    // --------------- table de SEND - add part
    public static final String[] TAB_COL_PART =  new String[] {
        "Barcode",          "EAN",          "batch",
        "Expiry Date",              "location",          
        "quantity",         "action",       "status",
        //"Supplier",         "Manufacturer",
        "create", "link","remove"
    };
    public static final Class[] TAB_COL_PART_CLASS =  new Class[] {
        String.class,       String.class,   String.class,
        LocalDate.class,       StockLocation.class,   
        Integer.class,      String.class,   String.class,
        //CompanyItem.class,  CompanyItem.class,
        JButton.class, JButton.class, JButton.class
    };
    public static final Set<String> TAB_COL_PART_EDIT =  Set.of(
        "batch","Expiry Date", "location",// "IPN",
        "create", "link", "remove",
        //"Supplier", "Manufacturer",
        "quantity"
    );
     // --------------- table de SEND - error part
    
    
}
