/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import Inventree.item.StockItem;

/**
 *
 * @author legen
 */
public class PopUpActionEvent extends iEvent {
   public static final String ACTION_COPY = "action_copy";
   public static final String ACTION_UPDATE_ONE = "action_update_one";
   public String value;
   public StockItem si;
    
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
