/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package events;

import Inventree.item.InventreeLists;

/**
 *
 * @author blegendre
 */
public class ParamEvent extends iEvent {
     public static final String PARAM_LOADED = "param_loaded";
     
     public InventreeLists ivl;
     
     public ParamEvent(Object source) {
         super(source);
         type = null;
         ivl = null;
    }

    public ParamEvent(Object source, String EventName) {
         super(source,EventName);
         ivl = null;
    }
    public ParamEvent(Object source, String EventName, InventreeLists inF) {
         super(source,EventName);
         ivl = inF;
    }
}
