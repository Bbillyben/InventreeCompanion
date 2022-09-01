/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.element.renderer.table;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author blegendre
 */
public class IntegerEditor extends AbstractCellEditor implements TableCellEditor {
    
    private int currentValue;
    private final JSpinner spin;

    public IntegerEditor() {
        SpinnerNumberModel model =new SpinnerNumberModel(1,1,200,1);
        spin = new JSpinner(model);
    }
    
    @Override
    public Object getCellEditorValue() {
        return spin.getValue();// this.currentValue;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
       
        if (value instanceof Integer && (int) value > 0) {
            spin.setValue((int) value);
        }
        return spin;
    }
    /*
    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner spin = (JSpinner)  e.getSource();
        this.currentValue=(int) spin.getValue();
        this.fireEditingStopped();
    }
*/

}
