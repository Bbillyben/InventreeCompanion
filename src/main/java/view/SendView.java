/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view;

import Inventree.item.InventreeLists;
import Inventree.item.StockItem;
import barcode.StockList;
import controller.SendController;
import data.CONSTANT;
import data.IniStruct;
import events.BarcodeEvent;
import events.IniEvent;
import events.ParamEvent;
import events.PopUpActionEvent;
import events.SendEvent;
import events.iEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import listeners.BarcodeListener;
import listeners.ListenerI;
import listeners.ParamListener;
import listeners.PopUpActionListener;
import listeners.SendListener;
import utils.RotatedIcon;
import utils.TableColumnAdjuster;
import utils.TextIcon;
import view.element.ScanTable;
import view.model.StockItemModel;

/**
 *
 * @author blegendre
 */
public class SendView extends JPanel implements iView, 
        BarcodeListener, ListenerI, SendListener, ParamListener, 
        TableModelListener, ActionListener, PopUpActionListener {
    
    
    private final SendController controller;
    
    private ScanTable updateTable;
    private ScanTable itemTable;
    private ScanTable partTable;
    private ScanTable errorTable;
    
    private TableColumnAdjuster uTA;
    private TableColumnAdjuster iTA;
    private TableColumnAdjuster pTA;
    
    private JButton sendUpdate;
    private JButton sendItem;
    private JButton sendPart;
    
    public SendView(SendController ctrl){
        super(new GridBagLayout());
        controller = ctrl;
        buildView();
    }
    
    public void updateAll(){
        removeTableListeners();
        StockList sl = InventreeLists.getInstance().stockList;
        
        ((StockItemModel) updateTable.getModel()).setData(sl.getListOf(CONSTANT.STATUS_ITEM_FOUND));
        ((StockItemModel) itemTable.getModel()).setData(sl.getListOf(CONSTANT.STATUS_NEW_ITEM));
        ((StockItemModel) partTable.getModel()).setData(sl.getListOf(CONSTANT.STATUS_NEW_PART));
        ((StockItemModel) errorTable.getModel()).setData(sl.getListOf(CONSTANT.ERROR_STATUS));
        addTableListeners();
    }
    
    @Override
    public void setVisible(boolean state){
        super.setVisible(state);
        if(state){
            
             updateAll();
            
        }else {
            removeTableListeners();
        }
           
    }
    
    
     private void loadIni(IniStruct ini){

    }
     private void loadParam(InventreeLists ivl){
         
        updateTable.initialisation(ivl);
        itemTable.initialisation(ivl);
        partTable.initialisation(ivl);
        errorTable.initialisation(ivl);
    }
    
    // ====================================== BUILD de LA VIEW =========================== //
    // =================================================================================== //
     private void buildView(){
         
         // table des update
         updateTable = new ScanTable();
         StockItemModel updateModel = new StockItemModel(
                InventreeLists.getInstance().stockList.getList(),
                CONSTANT.TAB_COL_SCAN,
                CONSTANT.TAB_COL_SCAN_CLASS,
                CONSTANT.TAB_COL_UPDATE_EDIT
        );
        updateTable.setModel(updateModel);
        //uTA = new TableColumnAdjuster(updateTable);
        JScrollPane uJSP = new JScrollPane(updateTable);
        
        //table des update item
        itemTable = new ScanTable();
         StockItemModel itemModel = new StockItemModel(
                InventreeLists.getInstance().stockList.getList(),
                CONSTANT.TAB_COL_ITEM,
                CONSTANT.TAB_COL_ITEM_CLASS,
                CONSTANT.TAB_COL_ITEM_EDIT
        );
        itemTable.setModel(itemModel);
        //iTA = new TableColumnAdjuster(itemTable);
        JScrollPane iJSP = new JScrollPane(itemTable);
        
        sendItem =new JButton();
        TextIcon t1 = new TextIcon(sendItem, "Send to server", TextIcon.Layout.HORIZONTAL);
        RotatedIcon r1 = new RotatedIcon(t1, RotatedIcon.Rotate.UP);
        sendItem.setActionCommand(CONSTANT.ACTION_SEND_ALL);
        sendItem.setIcon(r1);
        sendItem.setMinimumSize(new Dimension(0,0));

        JPanel iPan = new JPanel(new GridBagLayout());
        // le panel pour les tables
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.95;
        c.weighty = 0.5;
        
        iPan.add(uJSP, c);
        
        GridBagConstraints c2 = new GridBagConstraints();
        c2.fill = GridBagConstraints.BOTH;
        c2.gridx = 0;
        c2.gridy = 1;
        c2.weightx = 0.95;
        c2.weighty = 0.5;
        
        iPan.add(iJSP, c2);
        
        GridBagConstraints c3 = new GridBagConstraints();
        c3.fill = GridBagConstraints.BOTH;
        c3.gridx = 1;
        c3.gridy = 0;
        c3.weightx = 0.05;
        c3.weighty = 1;
        c3.gridheight = GridBagConstraints.REMAINDER;
        
        iPan.add(sendItem, c3);
        iPan.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Update & Create Stock Item from Part"));
        
        //table des update part
        partTable = new ScanTable();
         StockItemModel partModel = new StockItemModel(
                InventreeLists.getInstance().stockList.getList(),
                CONSTANT.TAB_COL_PART,
                CONSTANT.TAB_COL_PART_CLASS,
                CONSTANT.TAB_COL_PART_EDIT
        );
        partTable.setModel(partModel);
        //pTA = new TableColumnAdjuster(partTable);
        JScrollPane pJSP = new JScrollPane(partTable);
        
        
        JPanel pPan = new JPanel(new BorderLayout());
        pPan.add(pJSP);
        //pPan.add(sendPart, BorderLayout.AFTER_LAST_LINE);
        pPan.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Create Part Item"));
        
        
        // table des erruers
        errorTable = new ScanTable();
        StockItemModel errorModel = new StockItemModel(
                InventreeLists.getInstance().stockList.getList(),
                CONSTANT.TAB_COL_SCAN,
                CONSTANT.TAB_COL_SCAN_CLASS,
                CONSTANT.TAB_COL_SCAN_EDIT
        );
        errorTable.setModel(errorModel);
        JScrollPane eJSP = new JScrollPane(errorTable);
        JPanel ePan = new JPanel(new BorderLayout());
        ePan.add(eJSP);
        //pPan.add(sendPart, BorderLayout.AFTER_LAST_LINE);
        ePan.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Error Item"));
        
        
        GridBagConstraints cst = new GridBagConstraints();
        //cst.anchor = GridBagConstraints.NORTH;
        //cst.insets = new Insets(0, 0, 10, 10);
        cst.fill = GridBagConstraints.BOTH;
        cst.gridx = 0;
        cst.gridy = 0;
        
        cst.weightx = 1.0;
        cst.weighty = 0.66;
        this.add(iPan, cst);
        
        GridBagConstraints cst2 = new GridBagConstraints();
        //cst2.anchor=GridBagConstraints.NORTH;
        cst2.fill = GridBagConstraints.BOTH;
        cst2.gridx = 0;
        cst2.gridy = 1;
        
        cst2.weightx = 1.0;
        cst2.weighty = 0.20;
        this.add(pPan,cst2);
         
         GridBagConstraints cst3 = new GridBagConstraints();
        //cst2.anchor=GridBagConstraints.NORTH;
        cst3.fill = GridBagConstraints.BOTH;
        cst3.gridx = 0;
        cst3.gridy = 2;
        
        cst3.weightx = 1.0;
        cst3.weighty = 0.13;
        this.add(ePan,cst3);
         
     }
     
     private void addTableListeners(){
        itemTable.getModel().addTableModelListener(this);
        updateTable.getModel().addTableModelListener(this);
        partTable.getModel().addTableModelListener(this);
        errorTable.getModel().addTableModelListener(this);
        //sendUpdate.addActionListener(this);
        sendItem.addActionListener(this);
        //sendPart.addActionListener(this);
        itemTable.addEventListener(this, PopUpActionListener.class);
        updateTable.addEventListener(this, PopUpActionListener.class);
        partTable.addEventListener(this, PopUpActionListener.class);
        errorTable.addEventListener(this, PopUpActionListener.class);

        
     }
     private void removeTableListeners(){
          itemTable.getModel().removeTableModelListener(this);
         updateTable.getModel().removeTableModelListener(this);
         partTable.getModel().removeTableModelListener(this);
         errorTable.getModel().removeTableModelListener(this);
         //sendUpdate.removeActionListener(this);
        sendItem.removeActionListener(this);
        //sendPart.removeActionListener(this);
        itemTable.removeEventListener(this, PopUpActionListener.class);
        updateTable.removeEventListener(this, PopUpActionListener.class);
        partTable.removeEventListener(this, PopUpActionListener.class);
        errorTable.removeEventListener(this, PopUpActionListener.class);
     }
    @Override
    public void eventRecept(iEvent e) {
        //System.out.println("view.SendView.eventRecept()"+e.type);
        switch(e.type){
            case IniEvent.INI_LOADED :
            case IniEvent.INI_CHANGED :
                loadIni(((IniEvent) e).ini);
                break;
            case ParamEvent.PARAM_LOADED:
                loadParam(((ParamEvent) e).ivl);
                break;
            case BarcodeEvent.NEW_BARCODE, BarcodeEvent.BCB_STATUS_UPDATE:
            case SendEvent.ITEM_SENDING:
                updateAll();
                break;
            case PopUpActionEvent.ACTION_COPY:
                controller.copyValue(((PopUpActionEvent) e).value);
                break;
            case PopUpActionEvent.ACTION_UPDATE_ONE:
                controller.updateStockItem(((PopUpActionEvent) e).si);
                break;
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        StockItem si = ((StockItemModel) e.getSource()).getObjectAt(e.getFirstRow());
        System.out.println("view.SendView.tableChanged()type :"+e.getType());
        switch(e.getType()){
            case 2:// create new part
                controller.createPart(si);
                break;
            case 3:// link to an existing part
                controller.linkItem(si);
                break;
            case -2://delete item
            case 0://cell update
            default:
                controller.updateStockItem(si);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
             int result = JOptionPane.showConfirmDialog(this,"This will send StockItems to distant server.\n Are you sure?", "Send Item to Server",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE);
             
            if(result == 0)
                controller.sendToServer(e.getActionCommand()); 
    }
    
}
