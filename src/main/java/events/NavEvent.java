/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

/**
 *
 * @author legen
 */
public class NavEvent extends iEvent{
   public static final String NAVIGATE = "navigate";
   public String destPage;
    
    public NavEvent(Object source) {
         super(source);
         type = null;
         destPage = null;
    }

    public NavEvent(Object source, String EventName) {
         super(source,EventName);
         destPage = null;
    }
    public NavEvent(Object source, String EventName, String page) {
         super(source,EventName);
         destPage = page;
    }
}
