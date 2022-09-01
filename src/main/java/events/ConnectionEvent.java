/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

/**
 *
 * @author blegendre
 */
public class ConnectionEvent extends iEvent {
    
  public static final String CONNECTION_ERROR = "connection_error";
  public static final String CONNECTION_SUCCESS = "connection_success";
  
  public String reason;
    
    public ConnectionEvent(Object source) {
         super(source);
         type = null;
         reason = null;
    }

    public ConnectionEvent(Object source, String EventName) {
         super(source,EventName);
         reason = null;
    }
    public ConnectionEvent(Object source, String EventName, String reasonS) {
         super(source,EventName);
         reason = reasonS;
    }
     
}
