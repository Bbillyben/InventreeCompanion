/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.element.renderer.table;

import Inventree.item.CompanyItem;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author blegendre
 */
public class CompanyItemTableRenderer extends JLabel implements TableCellRenderer {
    public CompanyItemTableRenderer(){
        /*for(StockLocation sl : ivl.stocklocation){
            //System.out.println(this.getClass()+" add item "+sl.getName());
            this.addItem(sl);
        }*/
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //System.out.println(this.getClass()+"  --- add "+value);
       
        if(value != null){
            CompanyItem ciT = (CompanyItem) value;
            this.setText(ciT.getName());
        }else{
            this.setText("");
        }
        return this;
    }
}
