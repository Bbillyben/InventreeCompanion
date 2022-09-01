/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import data.IniStruct;

/**
 *
 * @author blegendre
 */
public class IniEvent extends iEvent{
    
    public static final String INI_CHANGED = "ini_changed";
    public static final String INI_LOADED = "ini_loaded";
    
    public IniStruct ini;
    
    public IniEvent(Object source) {
         super(source);
         type = null;
         ini = null;
    }

    public IniEvent(Object source, String EventName) {
         super(source,EventName);
         ini = null;
    }
    public IniEvent(Object source, String EventName, IniStruct is) {
         super(source,EventName);
         ini = is;
    }
     
}
