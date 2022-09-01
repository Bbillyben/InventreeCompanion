/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.element;

import Inventree.item.CompanyItem;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author blegendre
 */
public class CompanyItemCB extends JComboBox {
     public CompanyItemCB(){
        super();
        this.setRenderer(new CompanyCBRenderer());
    }
     public void setSelection(String locName){
        if(locName == null)
            return;
        for (int i = 0; i < this.getItemCount(); i++) {
        CompanyItem sl = (CompanyItem) this.getItemAt(i);
        //System.out.println(this.getClass()+" --> compare :"+locName+ " == "+sl.getId()+"("+(String.valueOf(sl.getId()) == locName)+")");
        if(locName.equals(String.valueOf(sl.getId()))){
            this.getModel().setSelectedItem(sl);
            break;
        }
      }
    }
    public String getSelection(){
        CompanyItem sl = (CompanyItem) this.getSelectedItem();
        if (sl == null)
            return null;
        return String.valueOf(sl.getId());
    }
     
}


class CompanyCBRenderer extends BasicComboBoxRenderer {
  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    if (value != null) {
      CompanyItem item = (CompanyItem) value;
      setText(item.getDisplayName());
    }
    return this;
  }
}