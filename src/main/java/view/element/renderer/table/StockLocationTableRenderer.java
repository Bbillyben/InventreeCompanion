/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element.renderer.table;

import Inventree.item.InventreeLists;
import Inventree.item.StockLocation;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author legen
 */
public class StockLocationTableRenderer extends JLabel implements TableCellRenderer{

    public StockLocationTableRenderer(InventreeLists ivl){
        this.setOpaque(true);
        /*for(StockLocation sl : ivl.stocklocation){
            //System.out.println(this.getClass()+" add item "+sl.getName());
            this.addItem(sl);
        }*/
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //System.out.println(this.getClass()+"  --- add "+value);
        
        //this.setSelectedItem(value);
        if(value != null){
            this.setText(((StockLocation) value).getName());
        }else{
            this.setText("---");
        }
            
        return this;
    }
    
}
