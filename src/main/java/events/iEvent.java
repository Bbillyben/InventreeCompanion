/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import java.util.EventObject;

/**
 *
 * @author blegendre
 */
public class iEvent extends EventObject {
    
  public String type;
    
    public iEvent(Object source) {
         super(source);
         type = null;
    }
     public iEvent(Object source, String EventName) {
         super(source);
         type = EventName;
    }
}

