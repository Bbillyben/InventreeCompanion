/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element;

import Inventree.item.Category;
import Inventree.item.CompanyItem;
import Inventree.item.InventreeItem;
import Inventree.item.InventreeLists;
import Inventree.item.PartItem;
import Inventree.item.StockItem;
import Inventree.item.StockLocation;
import controller.CreatePartController;
import data.UTILS;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;


/**
 *
 * @author legen
 */
public class CreatePartDialog extends JDialog implements ActionListener{
    public static final String NULL_ITEM = "---"; 
    protected InventreeItemFilterCB categoryCB;
    protected JTextField nameTxt;
    protected JTextField IPNTxt;
    protected JTextField descTxt;
    protected InventreeItemFilterCB subPartCB;
    protected InventreeItemFilterCB defLocation;
    protected JSpinner minNum=new JSpinner();
    protected JCheckBox isTemplateCB= new JCheckBox();
    protected InventreeItemFilterCB supplyerCB;
    protected JTextField supplyerSKU;
    protected InventreeItemFilterCB manufacturerCB;
    protected JTextField manufMPN;
    protected JButton saveBtn;
    
    protected StockItem currentStockItem;
    
    protected JCheckBox assignToPart;
    protected JCheckBox assignToSupplyer;
    protected JPanel assignBC;
    
    protected CreatePartController controller;
    public CreatePartDialog(CreatePartController cont, Frame owner, String titre, boolean modal){
        super(owner, titre, modal);
        controller = cont;
        buildView();
    }
    
    public void ini(StockItem si){
        currentStockItem = si;
        if(si.partitem!=null && si.partitem.getId()!=0){
            nameTxt.setText(si.partitem.getName());
            IPNTxt.setText(si.partitem.IPN);
        }else{
            nameTxt.setText("");
            IPNTxt.setText("");
        }
        descTxt.setText("");
        defLocation.setSelectedId(String.valueOf(si.stocklocation.getId()));
        isTemplateCB.setSelected(true);
        supplyerSKU.setText(si.EAN);
        manufMPN.setText(si.EAN);
        
        categoryCB.setSelectedIndex(0);
        subPartCB.setSelectedIndex(0);
        minNum.setValue(0);
        supplyerCB.setSelectedIndex(0);
        manufacturerCB.setSelectedIndex(0);   
        
        assignToPart.setSelected(false);
        assignToSupplyer.setSelected(true);
        
    }
    private void startSaveProcess(){
        boolean status = true;
        status = UTILS.checkTextField(nameTxt);
        if(!UTILS.checkTextField(descTxt))
            status = false;
        
        /*if(!UTILS.checkTextField(manufMPN))
            status = false;*/
        
        /*if(!UTILS.checkComboBox(manufacturerCB))
                status = false;*/
       
        if(!assignToPart.isSelected() && !assignToSupplyer.isSelected()){
            UTILS.setCheckBorder(assignBC, false);
             status = false;
        }else{
            UTILS.setCheckBorder(assignBC, true);
        }
        
        if(assignToSupplyer.isSelected()){
           System.out.println("YEAHH ");
           Boolean test = UTILS.checkComboBox(supplyerCB);
           UTILS.setCheckBorder(assignBC,  test);
           status = status && test; 
       }
        
        
        if(!status){
            JOptionPane.showMessageDialog(this, "Some Required Information are missing");
            return;
        }
        
        controller.createPart(
                currentStockItem,
                ((InventreeItem) categoryCB.getSelectedItem()).getId(), 
                nameTxt.getText(),
                IPNTxt.getText(),
                descTxt.getText(),
                ((InventreeItem) subPartCB.getSelectedItem()).getId(),
                ((InventreeItem) defLocation.getSelectedItem()).getId(),
                (int) minNum.getValue(),
                isTemplateCB.isSelected(),
                ((InventreeItem) supplyerCB.getSelectedItem()).getId(),
                supplyerSKU.getText(),
                ((InventreeItem) manufacturerCB.getSelectedItem()).getId(),
                manufMPN.getText(),
                assignToPart.isSelected(),
                assignToSupplyer.isSelected()
        );
        
           
    }
   
    
    public void updateDatas(InventreeLists ivl){
        categoryCB.removeAllItems();
        for(Category cat : ivl.categories){
            categoryCB.addItem(cat);
        }
        categoryCB.init();
        subPartCB.removeAllItems();
        subPartCB.addItem(new PartItem(NULL_ITEM, 0));
        for(PartItem pi : ivl.partTemplate){
            if(pi.isTemplate)
                subPartCB.addItem(pi);
        }
        subPartCB.init();
        defLocation.removeAllItems();
        for(StockLocation sl : ivl.stocklocation){
            defLocation.addItem(sl);
        }
        defLocation.init();
        supplyerCB.removeAllItems();
        supplyerCB.addItem(new InventreeItem(NULL_ITEM, 0));
        for(CompanyItem sl : ivl.suppliers){
            supplyerCB.addItem(sl);
        }
        supplyerCB.init();
        manufacturerCB.removeAllItems();
        manufacturerCB.addItem(new InventreeItem(NULL_ITEM, 0));
        for(CompanyItem sl : ivl.manufacturers){
            manufacturerCB.addItem(sl);
        }
        manufacturerCB.init();
    }
    private void buildView(){
        int txtFSize = 20;
        categoryCB = new InventreeItemFilterCB();
        nameTxt = new JTextField(txtFSize);
        IPNTxt = new JTextField(txtFSize);
        descTxt = new JTextField(txtFSize);
        subPartCB = new InventreeItemFilterCB();
        defLocation = new InventreeItemFilterCB();
        SpinnerNumberModel spinMod =new SpinnerNumberModel(0,0,200,1);
        minNum=new JSpinner(spinMod);
        isTemplateCB= new JCheckBox();
        supplyerCB = new InventreeItemFilterCB();
        supplyerSKU = new JTextField(txtFSize);
        manufacturerCB = new InventreeItemFilterCB();
        manufMPN = new JTextField(txtFSize);
        saveBtn = new JButton("Save");
        assignToPart=new JCheckBox("Assign Barcode to Part");
        assignToSupplyer=new JCheckBox("Assign Barcode to Supplyer");
        // pour panneau assignement
        assignBC = new JPanel();
        assignBC.setLayout(new BoxLayout(assignBC, BoxLayout.PAGE_AXIS));
        assignBC.add(assignToPart);
        assignBC.add(assignToSupplyer);
        
        //min size txtfield
        Dimension txtDim = new Dimension(200,20);
        categoryCB.setMinimumSize(txtDim);
        nameTxt.setMinimumSize(txtDim);
        IPNTxt.setMinimumSize(txtDim);
        descTxt.setMinimumSize(txtDim);
        subPartCB.setMinimumSize(txtDim);
        defLocation.setMinimumSize(txtDim);
        minNum.setMinimumSize(txtDim);
        isTemplateCB.setMinimumSize(txtDim);
        supplyerCB.setMinimumSize(txtDim);
        supplyerSKU.setMinimumSize(txtDim);
        manufacturerCB.setMinimumSize(txtDim);
        manufMPN.setMinimumSize(txtDim);
        txtDim = new Dimension(200,50);
        assignBC.setMinimumSize(txtDim);
        // panel
        JPanel jp = new JPanel(new GridBagLayout());
        GridBagConstraints cst = new GridBagConstraints();
        cst.anchor = GridBagConstraints.EAST;
        cst.insets = new Insets(0,0,10,10);
        
        // les label
        int i = 0;
        cst.gridx = 0;
        cst.gridy = 0;
        jp.add(new JLabel("Category"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Name"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("IPN"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Description"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Sub Part of"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Default Location"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Minimum Amount"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Set as Template"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Supplier"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Supplier part ref"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Manufacturer"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Manufacturer Id"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Barcode Assignement"), cst);
        
        cst.anchor = GridBagConstraints.WEST;
        cst.fill = GridBagConstraints.HORIZONTAL;
        i=0;
        cst.gridx=1;
        cst.gridy=i;
        jp.add(categoryCB, cst);
        cst.gridy = (i+=2);
        jp.add(nameTxt, cst);
        cst.gridy = (i+=2);
        jp.add(IPNTxt, cst);
        cst.gridy = (i+=2);
        jp.add(descTxt, cst);
        cst.gridy = (i+=2);
        jp.add(subPartCB, cst);
        cst.gridy = (i+=2);
        jp.add(defLocation, cst);
        cst.gridy = (i+=2);
        jp.add(minNum, cst);
        cst.gridy = (i+=2);
        jp.add(isTemplateCB, cst);
        cst.gridy = (i+=2);
        jp.add(supplyerCB, cst);
        cst.gridy = (i+=2);
        jp.add(supplyerSKU, cst);
        cst.gridy = (i+=2);
        jp.add(manufacturerCB, cst);
        cst.gridy = (i+=2);
        jp.add(manufMPN, cst);
        cst.gridy = (i+=2);
        jp.add(assignBC, cst);        
        
        cst.anchor=GridBagConstraints.EAST;
        cst.fill = GridBagConstraints.NONE;
        cst.gridy=(i+=1);
        jp.add(saveBtn, cst);
        
        jp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(jp);
        //this.setSize(600, 250);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        
        saveBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.startSaveProcess();
    }
}