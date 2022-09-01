/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package Inventree.item;

import java.io.Serializable;
import org.apache.maven.shared.utils.StringUtils;

/**
 *
 * @author blegendre
 */
public class Category extends InventreeItem {

    private int level; 
    
    public Category(){
    }
    public Category(int idS, String nameS){
        id = idS;
        name=nameS;
    }
    
   
    public int getLevel(){
        return level;
    }
    public void setLevel(int i){
         level = i;
    }
    
    @Override
    public String getDisplayName(){
        return StringUtils.repeat(" --", level)+" " + name;
        
    }
    
    @Override
    public String toString(){
        String str = super.toString();
        str+="\n    - id :"+id;
        str+="\n    - name :"+name;
        str+="\n    - level :"+level;
        return str;
    }
    
}
