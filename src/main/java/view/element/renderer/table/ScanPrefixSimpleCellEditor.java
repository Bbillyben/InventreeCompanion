/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.element.renderer.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author blegendre
 */
public class ScanPrefixSimpleCellEditor extends AbstractCellEditor  
        implements TableCellEditor, ActionListener {
    private JTextField tf;
    
    public ScanPrefixSimpleCellEditor(){
        tf = new JTextField();
        tf.addActionListener(this);
    }
    @Override
    public Object getCellEditorValue() {
        String v= tf.getText();
        if(v == null || v.length()==0)return "";
        if (v.charAt(0) != ']')return v;
        return v.substring(3);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if(value == null)value = "";
        tf.setText(value.toString());
        return tf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stopCellEditing();
    }
    
}
