/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.element.renderer.table;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author blegendre
 */
public class ScanPrefixSimpleLabelRenderer extends JLabel implements TableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //System.out.println(this.getClass()+"  --- add "+value);
       String v= String.valueOf(value);
        if (v.charAt(0) != ']'){
            this.setText(v);
        }else{
            this.setText(v.substring(3));
        }
        return this;
    }
}
