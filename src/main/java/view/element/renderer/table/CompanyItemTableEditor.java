/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.element.renderer.table;

import Inventree.item.CompanyItem;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import view.element.CompanyItemCB;

/**
 *
 * @author blegendre
 */
public class CompanyItemTableEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {
    
    private CompanyItem ci;
    private ArrayList<CompanyItem> compItems;
     
    public CompanyItemTableEditor(ArrayList<CompanyItem> alCI) {
        this.compItems = alCI;
    }
     
    @Override
    public Object getCellEditorValue() {
        return this.ci;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
         
        CompanyItemCB comboLoc = new CompanyItemCB();
         
        for(CompanyItem sl : compItems){
            System.out.println("view.element.renderer.table.CompanyItemTableEditor.getTableCellEditorComponent() :"+sl.getName());
            comboLoc.addItem(sl);
        }
         
        if (value instanceof CompanyItem) {
            this.ci = (CompanyItem) value;
            comboLoc.setSelectedItem(ci);
        }
        
        comboLoc.addActionListener(this);
        
         
        return comboLoc;
    }
 
    @Override
    public void actionPerformed(ActionEvent event) {
        CompanyItemCB comboLoc  = (CompanyItemCB) event.getSource();
        this.ci = (CompanyItem) comboLoc.getSelectedItem();
        this.fireEditingStopped();
    }
 
}