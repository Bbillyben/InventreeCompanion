/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element.renderer.table;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDate;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
/**
 *
 * @author legen
 */
public class DateTableEditor extends AbstractCellEditor implements TableCellEditor, DateChangeListener {
    private LocalDate currDate;
    
    public DateTableEditor() {
    }
    
    @Override
    public Object getCellEditorValue() {
        return this.currDate;
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value != null && value instanceof LocalDate) {
            this.currDate = (LocalDate) value;
           
        }else{
            this.currDate = LocalDate.now();
        }
        DatePicker dp = new DatePicker();
        dp.setPreferredSize(new Dimension(20,15));
        dp.getComponent(0).setPreferredSize(new Dimension(20,15));
        dp.getComponent(1).setPreferredSize(new Dimension(20,15));
        dp.setDate(currDate);
        dp.addDateChangeListener(this);
        
        return dp;
    }
    @Override
    public void dateChanged(DateChangeEvent e) {
        System.out.println("DateTableEditor.DateChangeEvent() :"+e);
        DatePicker m = (DatePicker) e.getSource();
        LocalDate d = m.getDate();
        if(d == null){
            this.currDate = null;
        }else{
            this.currDate = d ; //LocalDate.of(d.getYear()+1900,d.getMonth()+1, d.getDate());
        }
        this.fireEditingStopped();
    }
   
    
}
