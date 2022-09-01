/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element.renderer.table;

import java.awt.Component;
import java.time.LocalDate;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author legen
 */
public class BasicDateTableRenderer extends JLabel implements TableCellRenderer {
    public BasicDateTableRenderer(){
        this.setOpaque(true);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value == null){
            this.setText("");
        }else{
             LocalDate d = ((LocalDate) value);
            //System.out.println(this.getClass()+" --> date :"+d);
            this.setText(d.toString());
        }     
        return this;
    }
    
}
