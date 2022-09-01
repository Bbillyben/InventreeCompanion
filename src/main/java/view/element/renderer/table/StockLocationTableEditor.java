/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element.renderer.table;

import Inventree.item.InventreeLists;
import Inventree.item.StockLocation;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import view.element.InventreeItemCB;
import view.element.StockLocationCB;

/**
 *
 * @author legen
 */
public class StockLocationTableEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {
 
    private StockLocation location;
    private ArrayList<StockLocation> stockLocations;
     
    public StockLocationTableEditor(InventreeLists ivl) {
        this.stockLocations = ivl.stocklocation;
    }
     
    @Override
    public Object getCellEditorValue() {
        return this.location;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value instanceof StockLocation) {
            this.location = (StockLocation) value;
        }
         
        InventreeItemCB comboLoc = new InventreeItemCB();
         
        for(StockLocation sl : stockLocations){
            comboLoc.addItem(sl);
        }
         
        comboLoc.setSelectedItem(location);
        comboLoc.addActionListener(this);
        
         
        return comboLoc;
    }
 
    @Override
    public void actionPerformed(ActionEvent event) {
        InventreeItemCB comboLoc  = (InventreeItemCB) event.getSource();
        this.location = (StockLocation) comboLoc.getSelectedItem();
        this.fireEditingStopped();
    }
 
}