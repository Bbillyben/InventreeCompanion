/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.element;

import Inventree.item.StockLocation;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author blegendre
 */
public class StockLocationCB extends JComboBox {
    public StockLocationCB(){
        super();
        this.setRenderer(new StockLocRenderer());
    }
    public void setStockLoc(String locName){
        if(locName == null)
            return;
        for (int i = 0; i < this.getItemCount(); i++) {
        StockLocation sl = (StockLocation) this.getItemAt(i);
        //System.out.println(this.getClass()+" --> compare :"+locName+ " == "+sl.getId()+"("+(String.valueOf(sl.getId()) == locName)+")");
        if(locName.equals(String.valueOf(sl.getId()))){
            this.getModel().setSelectedItem(sl);
            break;
        }
      }
    }
    public String getStockLoc(){
        StockLocation sl = (StockLocation) this.getSelectedItem();
        if (sl == null)
            return null;
        return String.valueOf(sl.getId());
    }
    
}



class StockLocRenderer extends BasicComboBoxRenderer {
  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    if (value != null) {
      StockLocation item = (StockLocation) value;
      setText(item.getDisplayName());
    }
    return this;
  }
}