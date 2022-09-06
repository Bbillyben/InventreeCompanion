/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.element.renderer.table;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author blegendre
 */
public class StatusRenderer extends JLabel implements TableCellRenderer{

    public StatusRenderer(){
        this.setOpaque(true);
        //this.setHorizontalAlignment(SwingConstants.WEST);
        //this.setVerticalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value == null){
            this.setText("");
        }else{
            this.setText((String) value);
            this.setToolTipText((String) value);
        }   
        return this;
    }
    
}
