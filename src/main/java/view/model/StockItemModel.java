/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.model;

import Inventree.item.StockItem;
import data.CONSTANT;
import events.InventreeTableModelEvent;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author blegendre
 */
public class StockItemModel extends AbstractTableModel implements TableModel  { //{
    
    private ArrayList<StockItem> siList;
    protected String[] columnNames; /* = new String[] {
            "Barcode", "EAN","batch","Expiry Date","Name", "location", "IPN",
        "quantity in stock", "quantity", "action", "status",
        "remove", "ids"
    };*/
     public Set<String> columnEdit;
     protected Class[] columnClass;/* = new Class[] {
        String.class,String.class, String.class,LocalDate.class, String.class, StockLocation.class, String.class,
         Integer.class, Integer.class, String.class, String.class,
         JButton.class, String.class
    };*/
    
    public StockItemModel(ArrayList<StockItem> ssi,String[] colNames, Class[] colClass, Set<String> colEdit){
        siList = ssi;
        columnNames = colNames;
        columnClass = colClass;
        columnEdit = colEdit;
    }
    
    public void setData(ArrayList<StockItem> ssi){
        siList = ssi;
        this.fireTableDataChanged();
    }
    
    // setter
    public void setColumnNames(String[] strs){
        this.columnNames = strs;
    }
    public void setColumnClass(Class[] strs){
        this.columnClass = strs;
    }
    public void setColumnEdit(Set<String> s){
       columnEdit = s;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        // check if disabled
        StockItem sit = siList.get(siList.size()-1-row);
        
        if(sit.getStatus() == CONSTANT.STATUS_DELETED)
            return false;
        if(columnEdit == null)
            return false;
        if(!sit.action.equals(CONSTANT.MODE_TRANSFERT) && columnNames[column].equals("transfert location"))
             return false;
        
        return columnEdit.contains(columnNames[column]);
    }
    
    ///// 
     @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }
    @Override
    public int getRowCount() {
        return siList.size();
    }

    @Override
    public int getColumnCount() {
         return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if(columnIndex < -1 || columnIndex > columnNames.length)
            return null;
        StockItem row = siList.get(siList.size()-1 - rowIndex);
        if(columnIndex == -1)
            return row;
        return row.get(columnNames[columnIndex], columnClass[columnIndex]);
    }
    @Override
     public void setValueAt(Object value, int rowIndex, int columnIndex) {
         /*System.out.println(this.getClass()+" ====== modif :"
         +"\n    -> value :"+value
         +"\n    -> rowIndex :"+rowIndex
         +"\n    -> columnIndex :"+columnIndex
         +"\n    -> column:"+columnNames[columnIndex]
         );*/
         
         StockItem row = siList.get(siList.size()-1-rowIndex);
         if(columnNames[columnIndex] =="remove" && (Boolean) value){
             row.setStatus(CONSTANT.STATUS_DELETED);
             this.fireTableRowsUpdated(rowIndex, columnIndex);
         }else{
             row.set(columnNames[columnIndex], value);
             this.fireTableCellUpdated(rowIndex, columnIndex);
         }
         
        
    }
     
     public StockItem getObjectAt(int row){
         if(row < 0 || row >= siList.size())
             return null;

         return siList.get(siList.size()-1-row);
     }

    @Override
    public void addTableModelListener(TableModelListener l) {
        super.addTableModelListener(l);   
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        super.removeTableModelListener(l);
    }
    
    // la gestion des events
    @Override
    public void fireTableRowsUpdated(int firstRow, int lastRow) {
        System.out.println("view.model.StockItemModel.fireTableRowsUpdated()");
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                             TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
    }


    @Override
    public void fireTableCellUpdated(int row, int column) {
        System.out.println("StockItemModel.fireTableCellUpdated() col :"
                +column
                +"  --- > "+columnNames[column]
        );
        InventreeTableModelEvent e =new InventreeTableModelEvent(this, row, row, column);
        switch(columnNames[column]){
            case "create":
                e.setType(InventreeTableModelEvent.ADD_PART);
                break;
            case "link":
                e.setType(InventreeTableModelEvent.LINK_ITEM);
                break;
            case "remove":
                e.setType(InventreeTableModelEvent.DELETE_ITEM);
                break;
        }
        fireTableChanged(e);
    }
    
    
}