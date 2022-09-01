/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view;

import Inventree.item.InventreeLists;
import Inventree.item.StockItem;
import Inventree.item.StockLocation;
import controller.ScanController;
import data.CONSTANT;
import data.IniStruct;
import data.KeyHandlerManager;
import events.BarcodeEvent;
import events.IniEvent;
import events.ParamEvent;
import events.PopUpActionEvent;
import events.iEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import listeners.BarcodeListener;
import listeners.ListenerI;
import listeners.ParamListener;
import listeners.PopUpActionListener;
import listeners.keyBarcodeListener;
import utils.TableColumnAdjuster;
import view.element.InventreeItemCB;
import view.element.InventreeItemFilterCB;
import view.element.ScanTable;
import view.model.StockItemModel;
import view.element.StockLocationCB;

/**
 *
 * @author blegendre
 */
public class ScanView extends JPanel 
        implements iView, ActionListener, keyBarcodeListener, 
        ListenerI, ParamListener, BarcodeListener, TableModelListener, PopUpActionListener {
    private final ScanController controller;
    
    JRadioButton purchaseBtn;
    JRadioButton consumeBtn;
    JPanel btnPan;
    JTextField bcTxt;
    ScanTable bcTable;
    DefaultTableModel tableModel;
    InventreeItemFilterCB stockList;
    ButtonGroup group;
    
    // pour écoute des key
    private KeyHandlerManager khm;
    
    private String currentStatus;
    
    private String[] columnNames;
    private TableColumnAdjuster tca;
    
    private String stockLoc;
    
    public ScanView(ScanController ctrl){
        super(new BorderLayout());
        controller = ctrl;
        buildView();
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
    private void updateAll(){
        removeTableListeners();
        bcTable.updateData();//((BarcodeEvent)e).stockitem);
        addTableListeners();
    }
    private void addTableListeners(){
        bcTable.getModel().addTableModelListener(this);
    }
    private void removeTableListeners(){
        bcTable.getModel().removeTableModelListener(this);
    }
    private void buildView(){
       GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        
        group = new ButtonGroup();
        purchaseBtn = new JRadioButton(CONSTANT.MODE_ADD);
        purchaseBtn.setActionCommand(CONSTANT.MODE_ADD);
        consumeBtn = new JRadioButton(CONSTANT.MODE_REMOVE);
        consumeBtn.setActionCommand(CONSTANT.MODE_REMOVE);
        
        purchaseBtn.setOpaque(false);
        consumeBtn.setOpaque(false);
        
        group.add(purchaseBtn);
        group.add(consumeBtn);
        
        
        // Conteneur du panneau params
        JPanel paramCont = new JPanel(new GridLayout());
        paramCont.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Scan Mode"));
        // le panel pour les boutons
        btnPan = new JPanel(new GridBagLayout());
        constraints.insets = new Insets(10, 50, 10, 50);
        btnPan.add(purchaseBtn, constraints);//, BorderLayout.CENTER);
        constraints.gridx = 1;
        btnPan.add(consumeBtn, constraints);//, BorderLayout.CENTER);
         
        
        // pour la selection des location
        JPanel listCont = new JPanel();
        stockList = new InventreeItemFilterCB();
        stockList.setActionCommand("stocklistchange");
        listCont.add (stockList);

        
        
        paramCont.add(btnPan);//, BorderLayout.LINE_START);
        paramCont.add(listCont);//, BorderLayout.LINE_END);
        
        // le textField pour la saisie des barcode
        JPanel txtCont = new JPanel(new BorderLayout());//new GridBagLayout());
        //JLabel bcLab = new JLabel("Barcode Scanning");
        bcTxt = new JTextField(20);
        //txtCont.add(bcLab);//,constraints);
        constraints.gridy = 1;
        txtCont.add(bcTxt);//, constraints);
        txtCont.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Scan Area"));
        
        //la table
        bcTable = new ScanTable();
        
       //     mise en place du modèle pour la table
        StockItemModel tableModel = new StockItemModel(
                InventreeLists.getInstance().stockList.getList(),
                CONSTANT.TAB_COL_SCAN,
                CONSTANT.TAB_COL_SCAN_CLASS,
                CONSTANT.TAB_COL_SCAN_EDIT
        );
        bcTable.setModel(tableModel);
        
        
        // ------------------------------- fin model
        tca = new TableColumnAdjuster(bcTable);
        JScrollPane jsp = new JScrollPane(bcTable);
        JPanel tableCont = new JPanel(new BorderLayout());
        tableCont.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Barcode Status"));
        tableCont.add(jsp);
        
        
        // ajout au panel principal
        this.add(paramCont, BorderLayout.PAGE_START);
        this.add(tableCont , BorderLayout.CENTER);
        this.add(txtCont, BorderLayout.PAGE_END);
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Scan"));
        
        // les listener
        purchaseBtn.addActionListener(this);
        consumeBtn.addActionListener(this);
        //stockList.addActionListener(this);
        
        khm = new KeyHandlerManager();
        khm.addListener(bcTxt);
        khm.addBarcodeListener(this);
        
        
        bcTable.addEventListener(this, PopUpActionListener.class);
        
    }
    /* ------------------------ gestion scan mode change -----------*/
    private void changeMode(String scanmode){
        String color ;
        
        switch(scanmode){
            case CONSTANT.MODE_REMOVE: // consume
                btnPan.setBackground(Color.red);
                consumeBtn.setSelected(true);
                currentStatus = CONSTANT.MODE_REMOVE;
                break;
            case CONSTANT.MODE_ADD: // pruchase
                btnPan.setBackground(Color.green);
                purchaseBtn.setSelected(true);
                currentStatus = CONSTANT.MODE_ADD;
                break;
            default:
                btnPan.setBackground(null);
                consumeBtn.setSelected(false);
                purchaseBtn.setSelected(false);
                currentStatus = null;

                            
        }
    }
    
   
    private void loadIni(IniStruct ini){
         String secToEan = ini.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_secEAN, String.valueOf(CONSTANT.SCAN_SEC_EAN));
         khm.setSecondeToRelease(Double.valueOf(secToEan));
         
         String mode = ini.getValue(CONSTANT.SCAN_PARAM_HEAD, CONSTANT.SCAN_PARAM_MODE, CONSTANT.MODE_ADD);
         //System.out.println("view.ScanView.loadIni() mode :"+mode);
         changeMode(mode);
         stockLoc = ini.getValue(CONSTANT.SCAN_PARAM_HEAD, CONSTANT.SCAN_STOCK_LOC);
         this.stockList.setSelectedId(stockLoc);
    }
    private void loadParam(InventreeLists ivl){
         
        // liste des stocks
        stockList.removeActionListener(this);
        stockList.removeAllItems();
        for(StockLocation sl : ivl.stocklocation){
            stockList.addItem(sl);
        }
        bcTable.initialisation(ivl);
        this.stockList.setSelectedId(stockLoc);
        stockList.addActionListener(this);
        stockList.init();
        
    }

    @Override
    public void eventRecept(iEvent e) {
         switch(e.type){
            case IniEvent.INI_LOADED :
            case IniEvent.INI_CHANGED :
                loadIni(((IniEvent) e).ini);
                break;
            case ParamEvent.PARAM_LOADED:
                loadParam(((ParamEvent) e).ivl);
                break;
            case BarcodeEvent.NEW_BARCODE, BarcodeEvent.BCB_STATUS_UPDATE:
                updateAll();
                break;
            case PopUpActionEvent.ACTION_COPY:
                controller.copyValue(((PopUpActionEvent) e).value);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case CONSTANT.MODE_ADD, CONSTANT.MODE_REMOVE:
                controller.changeStatus(e.getActionCommand());
            break;
            case "stocklistchange":
                String currLoc = stockList.getSelectedId();
                System.out.println(this.getClass()+" ---> ActionEvent action cmd :"
                        +e.getActionCommand()+" / paramString :"+e.paramString()
                        +" - new loc :"+currLoc
                );
                
                controller.changeStockLoc(currLoc);
                break;
        }        
        bcTxt.requestFocus();
    }

    @Override
    public void processBarcode(BarcodeEvent e) {
        StockItem si = e.stockitem;
        si.stocklocation =(StockLocation) stockList.getSelectedItem();
        si.quantity = 1;
        si.action = group.getSelection().getActionCommand();
        //System.out.println(this+" in loc : "+si.stocklocation);
        controller.addStockItem(e.stockitem);
        //System.out.println(this+ " Barcode Event : \n"+e);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        StockItem si = ((StockItemModel) bcTable.getModel()).getObjectAt(e.getFirstRow());
        System.out.println("view.ScanView.tableChanged() type :"+e.getType());
        switch(e.getType()){
            case 2:// create new part
                break;
            case -2://delete item
            case 0://cell update
            default:
                controller.updateStockItem(si);
                break;
        }
        
    }
    
}
