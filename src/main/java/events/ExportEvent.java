/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package events;

import java.util.ArrayList;

/**
 *
 * @author blegendre
 */
public class ExportEvent extends iEvent {
    
    public static final String EXPORT_DATA="export_data";
    
    public String export_type;
    public ArrayList<String[]> export_data;
    
    public ExportEvent(Object source) {
         super(source);
         type = null;
    }

    public ExportEvent(Object source, String EventName) {
         super(source,EventName);
    }
   
}
