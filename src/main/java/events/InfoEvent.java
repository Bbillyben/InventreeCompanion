/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

/**
 *
 * @author blegendre
 */
public class InfoEvent extends iEvent {
    public static final String CONNECTION_ERROR = "info_connection_error";
    public static final String CONNECTION_SUCCESS = "info_connection_success";
   
    public static final String INI_FILE_ERROR = "ini_file_error";
    public static final String INFO_NORM = "information";
    public static final String NEW_SCAN = "new_scan";
    
    public static final String SAVE_ERROR = "Saving_error";
    public static final String SAVE_SUCESS = "Saving_Success";
    
    public static final String GENERIC_OK = "generic_ok";
    public static final String GENERIC_ERROR = "generic_error";
    public static final String GENERIC_DISPLAY_ERROR = "generic_display_error";
    
    public String info;
    
    public InfoEvent(Object source) {
         super(source);
         type = null;
         info = null;
    }

    public InfoEvent(Object source, String EventName) {
         super(source,EventName);
         info = null;
    }
    public InfoEvent(Object source, String EventName, String inF) {
         super(source,EventName);
         info = inF;
    }
}
