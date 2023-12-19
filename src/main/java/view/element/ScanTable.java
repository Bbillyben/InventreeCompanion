/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.element;

import Inventree.item.InventreeLists;
import Inventree.item.StockItem;
import Inventree.item.StockLocation;
import data.CONSTANT;
import events.PopUpActionEvent;
import events.iEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import listeners.ListenerI;
import listeners.PopUpActionListener;
import view.element.renderer.table.ActionRenderer;
import view.element.renderer.table.BCrenderer;
import view.element.renderer.table.BasicDateTableRenderer;
import view.element.renderer.table.ButtonEditor;
import view.element.renderer.table.ButtonEditor_add;
import view.element.renderer.table.ButtonRenderer;
import view.element.renderer.table.CompanyItemTableEditor;
import view.element.renderer.table.CompanyItemTableRenderer;
import view.element.renderer.table.DateTableEditor;
import view.element.renderer.table.IntegerEditor;
import view.element.renderer.table.ScanPrefixSimpleCellEditor;
import view.element.renderer.table.StatusRenderer;
import view.element.renderer.table.StockLocationTableEditor;
import view.element.renderer.table.StockLocationTableRenderer;
import view.model.StockItemModel;
/**
 *
 * @author blegendre
 */
// https://www.codejava.net/java-se/swing/jtable-simple-renderer-example

public class ScanTable extends JTable implements ActionListener, PopupMenuListener {
    private EventListenerList listeners;
    
    JPopupMenu jpm;
    private String tmpval;// force storing value to be copyed by popup menu action
    private StockItem tmpSI;// force storing StocItelm to be update by popup menu action
    
    public ScanTable(){ //ArrayList<StockItem> siList){
        super();
        listeners = new EventListenerList();
        //StockItemModel tableModel = new StockItemModel(siList);
       
        jpm = new JPopupMenu();
        JMenuItem copy = new JMenuItem("Copy");
        copy.setActionCommand(CONSTANT.ACTION_COPY_VALUE);
        JMenuItem forceupdate = new JMenuItem("Force Update");
        forceupdate.setActionCommand(CONSTANT.ACTION_FORCE_UPDATE_SINGLE);
        
        jpm.add(copy);
        jpm.add(forceupdate);
        
        this.setComponentPopupMenu(jpm);
        copy.addActionListener(this);
        forceupdate.addActionListener(this);
        
        jpm.addPopupMenuListener(this);
    }
    
    public boolean columnExists(String ident){
        TableColumnModel model = this.getColumnModel();
        boolean found = false;
        for (int index = 0; index < model.getColumnCount(); index++) {
            if (model.getColumn(index).getIdentifier().equals(ident)) {
                found = true;
                break;
            }            
        }
        return found;
    }
    
    @Override
    public void setModel(TableModel tblM ){
        super.setModel(tblM);
        
         //this.setModel(tableModel);DefaultTableCellRenderer
         DefaultTableCellRenderer MyHeaderRender = new DefaultTableCellRenderer();
          MyHeaderRender.setBackground(Color.LIGHT_GRAY);
          MyHeaderRender.setForeground(Color.BLACK);
        
        for(int i = 0; i< this.getColumnCount(); i++){
            String colName = this.getModel().getColumnName(i);
            if(((StockItemModel) this.getModel()).columnEdit.contains(colName)){
                //System.out.println(" ------------- > passed");
                this.getTableHeader().getColumnModel().getColumn(i).setHeaderRenderer(MyHeaderRender);
               
            }
        }
    }
    public void initialisation(InventreeLists ivl){
        //System.out.println("view.element.ScanTable"+" ---------> INITIALISATION");
        this.setDefaultRenderer(StockLocation.class, new StockLocationTableRenderer(ivl));
        this.setDefaultEditor(StockLocation.class, new StockLocationTableEditor(ivl));
        
        this.setDefaultEditor(LocalDate.class, new DateTableEditor());
        this.setDefaultRenderer(LocalDate.class, new BasicDateTableRenderer());
        
        this.setDefaultEditor(Integer.class, new IntegerEditor());
        
        if(this.columnExists("batch")){
//             this.getColumn("batch").setCellRenderer(new ScanPrefixSimpleLabelRenderer());
             this.getColumn("batch").setCellEditor(new ScanPrefixSimpleCellEditor());
        }
        if(this.columnExists("Supplier")){
            this.getColumn("Supplier").setCellRenderer(new CompanyItemTableRenderer());
            this.getColumn("Supplier").setCellEditor(new CompanyItemTableEditor(ivl.suppliers));
        }
        if(this.columnExists("Manufacturer")){
            this.getColumn("Manufacturer").setCellRenderer(new CompanyItemTableRenderer());
            this.getColumn("Manufacturer").setCellEditor(new CompanyItemTableEditor(ivl.manufacturers));
        }
        if(this.columnExists("create")){
            this.getColumn("create").setPreferredWidth(15);
            ButtonRenderer br = new ButtonRenderer();
            br.setIcon(new ImageIcon(getClass().getClassLoader().getResource("create2.png")));
            ButtonEditor_add be = new ButtonEditor_add(new JCheckBox(), "create2.png");
               this.getColumn("create").setCellRenderer(br);
               this.getColumn("create").setCellEditor(be);
        }
        if(this.columnExists("link")){
            this.getColumn("link").setPreferredWidth(15);
            ButtonRenderer br = new ButtonRenderer();
            br.setIcon(new ImageIcon(getClass().getClassLoader().getResource("link2.png")));
            ButtonEditor_add be = new ButtonEditor_add(new JCheckBox(), "link2.png");
               this.getColumn("link").setCellRenderer(br);
               this.getColumn("link").setCellEditor(be);
        }
        if(this.columnExists("remove")){
            ButtonRenderer br = new ButtonRenderer();
            br.setIcon(new ImageIcon(getClass().getClassLoader().getResource("trash2.png")));
            ButtonEditor be = new ButtonEditor(new JCheckBox(), "trash2.png");
            
            this.getColumn("remove").setPreferredWidth(15);
               this.getColumn("remove").setCellRenderer(br);
               this.getColumn("remove").setCellEditor(be);
        }
       if(this.columnExists("action")){
               this.getColumn("action").setCellRenderer(new ActionRenderer());
        }
       if(this.columnExists("Barcode")){
            this.getColumn("Barcode").setPreferredWidth(15);
            this.getColumn("Barcode").setCellRenderer(new BCrenderer());
        }
       if(this.columnExists("status")){
            this.getColumn("status").setCellRenderer(new StatusRenderer());
        }
        
        
        
        
    }
    public void updateData(){ //StockItem si){
        this.tableChanged(new TableModelEvent(dataModel));
    }
    
   @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component comp = super.prepareRenderer(renderer, row, column);
        StockItem si = (StockItem) this.getModel().getValueAt(row,-1); 
        String second = si.getStatus(); 
        if(second != null && !CONSTANT.MODIFIABLE_STATUS.contains(second)){
            comp.setBackground(Color.GRAY);
        }else if(second != null &&CONSTANT.ERROR_SEND_STATUS.contains(second) ){
            comp.setBackground(new Color(255, 235, 149));
        }else if(second != null &&CONSTANT.ERROR_STATUS.contains(second) ){
            comp.setBackground(Color.ORANGE);
        }else{
            comp.setBackground(Color.WHITE);
        }
        return comp;
    }
    
    
    // listener for Jpopumenu
     /* --------------------- Gestion des Event ----------------- */
    public void addEventListener(ListenerI listener, Class listenerInterfaceClass){
        listeners.add(listenerInterfaceClass, listener);
    }
    public void removeEventListener(ListenerI listener, Class listenerInterfaceClass){
        listeners.remove(listenerInterfaceClass, listener);
        
    }
     
    /**
   * Dispatche event to a specific class of listener that implement bcbListenerI.
   * 
     * @param cl : the class to dispatch
     * @param e : an event extend Event
   */
    public void dispatchEvent(Class cl, iEvent e){
        //System.out.println("Model Dispatch : "+cl+ "/ "+e.type+ "("+e+")");
        ListenerI[] listenerList = (ListenerI[]) listeners.getListeners(cl);
        for(ListenerI listener : listenerList){
            listener.eventRecept(e);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(tmpval!=null && CONSTANT.ACTION_COPY_VALUE.equals(e.getActionCommand())){
            PopUpActionEvent ev =new PopUpActionEvent(this, PopUpActionEvent.ACTION_COPY, tmpval);
            this.dispatchEvent(PopUpActionListener.class, ev);
        }else if(tmpSI!=null && CONSTANT.ACTION_FORCE_UPDATE_SINGLE.equals(e.getActionCommand()) ){
            PopUpActionEvent ev =new PopUpActionEvent(this, PopUpActionEvent.ACTION_UPDATE_ONE);
            ev.si = tmpSI;
            this.dispatchEvent(PopUpActionListener.class, ev);
        }
        
    }

    /**Catch popup menu before being invisible and storing values for actions in popup menu item
     * 
     * @param e 
     */
    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        Point p =SwingUtilities.convertPoint(jpm, new Point(0,0), this);
        int rowAtPoint = this.rowAtPoint(p);
        int colAtPoint = this.columnAtPoint(p);
        //System.out.println("row :"+rowAtPoint+ " / col :"+colAtPoint+"    ( point :"+p+")");
        if (rowAtPoint > -1 && colAtPoint > -1) {
            tmpval=String.valueOf(this.getValueAt(rowAtPoint, colAtPoint));
            tmpSI=(StockItem) this.getValueAt(rowAtPoint, -1);
        }else{
            tmpval=null;
            tmpSI = null;
        }
    }
    
    
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {}

    
}

