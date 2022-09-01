/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package Inventree.item;

import java.io.Serializable;

/**
 *
 * @author blegendre
 */
public class PartItem extends InventreeItem {
    
    
    public String IPN;
    
    public String EAN;
    
    public int partTemplate;
    public boolean isTemplate;
    
    public PartItem(){
        
    }
    public PartItem(String nameS, int idS){
        this.name = nameS;
        this.id = idS;
    }
    
    
}
