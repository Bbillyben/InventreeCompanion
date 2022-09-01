/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

/**
 *
 * @author legen
 */
public class PopUpActionEvent extends iEvent {
   public static final String ACTION_COPY = "action_copy";
   public String value;
    
    public PopUpActionEvent(Object source) {
         super(source);
         type = null;
         value = null;
    }

    public PopUpActionEvent(Object source, String EventName) {
         super(source,EventName);
         value = null;
    }
    public PopUpActionEvent(Object source, String EventName, String cValue) {
         super(source,EventName);
         value = cValue;
    }
}
