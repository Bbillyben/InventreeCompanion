/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element.renderer.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
/**
 *
 * @author legen
 */
public class DateTableEditor extends AbstractCellEditor implements TableCellEditor, ChangeListener {
    private LocalDate currDate;
    
    public DateTableEditor() {
    }
    
    @Override
    public Object getCellEditorValue() {
        return this.currDate;
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        UtilDateModel model = new UtilDateModel();
        if (value != null && value instanceof LocalDate) {
            this.currDate = (LocalDate) value;
           
        }else{
            this.currDate = LocalDate.now();
        }
         model.setDate(currDate.getYear(), currDate.getMonthValue()-1, currDate.getDayOfMonth());
         model.setSelected(true);
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl dp = new JDatePickerImpl(datePanel);
        dp.getComponent(0).setPreferredSize(new Dimension(20,15));
        dp.getComponent(1).setPreferredSize(new Dimension(20,15));
        model.addChangeListener(this);
        
        return dp;
    }
    @Override
    public void stateChanged(ChangeEvent e) {
        UtilDateModel m = (UtilDateModel) e.getSource();
        Date d = m.getValue();
        if(d == null){
            this.currDate = null;
        }else{
            this.currDate = LocalDate.of(d.getYear()+1900,d.getMonth()+1, d.getDate());
        }
        this.fireEditingStopped();
    }
   
    
}
