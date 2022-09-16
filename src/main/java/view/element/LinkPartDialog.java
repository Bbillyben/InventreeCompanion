/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element;

import Inventree.item.CompanyItem;
import Inventree.item.InventreeItem;
import Inventree.item.InventreeLists;
import Inventree.item.PartItem;
import Inventree.item.StockItem;
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
import javax.swing.JTextField;

/**
 *
 * @author legen
 */
public class LinkPartDialog extends JDialog implements ActionListener{
    public static final String NULL_ITEM = "---"; 
    protected InventreeItemFilterCB subPartCB;
    protected InventreeItemFilterCB SupplierCB;
    protected JTextField SupplierSKU;
    protected InventreeItemFilterCB manufacturerCB;
    protected JTextField manufMPN;
    
    protected JCheckBox assignToPart;
    protected JCheckBox assignToSupplier;
    protected JPanel assignBC;
    
    protected JButton saveBtn;
    
    protected StockItem currentStockItem;
    
    protected CreatePartController controller;
    public LinkPartDialog(CreatePartController cont, Frame owner, String titre, boolean modal){
        super(owner, titre, modal);
        controller = cont;
        buildView();
    }
    
    public void ini(StockItem si){
        currentStockItem = si;

        SupplierSKU.setText(si.EAN);
        manufMPN.setText(si.EAN);
        subPartCB.setSelectedIndex(0);
        SupplierCB.setSelectedIndex(0);
        
        assignToPart.setSelected(false);
        assignToSupplier.setSelected(true);
    }
    private void startSaveProcess(){
        boolean status = true;
        if(!UTILS.checkComboBox(subPartCB))
            status = false;
       /* if(!UTILS.checkComboBox(manufacturerCB))
                status = false;
        if(!UTILS.checkTextField(manufMPN))
            status = false;*/
        /*
        if(!UTILS.checkTextField(SupplierSKU))
            status = false;
        if(!UTILS.checkComboBox(SupplierCB)){
            if(!UTILS.checkComboBox(manufacturerCB)){
                status = false;
            }else{
                 SupplierCB.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            }
         }else{
            manufacturerCB.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }*/
        if(!assignToPart.isSelected() && !assignToSupplier.isSelected()){
            UTILS.setCheckBorder(assignBC, false);
             status = false;
        }else{
            UTILS.setCheckBorder(assignBC, true);
        }
        
       if(assignToSupplier.isSelected()){
           System.out.println("YEAHH ");
           Boolean test = UTILS.checkComboBox(SupplierCB);
           UTILS.setCheckBorder(assignBC,  test);
           status = status && test; 
       }
        
        if(!status){
            JOptionPane.showMessageDialog(this, "Some Required Information are missing");
            return;
        }
        
        controller.linkPart(
                currentStockItem,
                ((InventreeItem) subPartCB.getSelectedItem()).getId(),
                ((InventreeItem) SupplierCB.getSelectedItem()).getId(),
                SupplierSKU.getText(),
                ((InventreeItem) manufacturerCB.getSelectedItem()).getId(),
                manufMPN.getText(),
                assignToPart.isSelected(),
                assignToSupplier.isSelected()
        );
        
           
    }  
    
    public void updateDatas(InventreeLists ivl){

        subPartCB.removeAllItems();
        subPartCB.addItem(new PartItem(NULL_ITEM, 0));
        for(PartItem pi : ivl.partTemplate){
            subPartCB.addItem(pi);
        }
        subPartCB.init();
        
        SupplierCB.removeAllItems();
        SupplierCB.addItem(new InventreeItem(NULL_ITEM, 0));
        for(CompanyItem sl : ivl.suppliers){
            SupplierCB.addItem(sl);
        }
        SupplierCB.init();
        manufacturerCB.removeAllItems();
        manufacturerCB.addItem(new InventreeItem(NULL_ITEM, 0));
        for(CompanyItem sl : ivl.manufacturers){
            manufacturerCB.addItem(sl);
        }
        manufacturerCB.init();
    }
    private void buildView(){
        int txtFSize = 20;
        subPartCB = new InventreeItemFilterCB();
        SupplierCB = new InventreeItemFilterCB();
        SupplierSKU = new JTextField(txtFSize);
        manufacturerCB = new InventreeItemFilterCB();
        manufMPN = new JTextField(txtFSize);
        saveBtn = new JButton("Save");
        
        assignToPart=new JCheckBox("Assign Barcode to Part");
        assignToSupplier=new JCheckBox("Assign Barcode to Supplier");
        // pour panneau assignement
        assignBC = new JPanel();
        assignBC.setLayout(new BoxLayout(assignBC, BoxLayout.PAGE_AXIS));
        assignBC.add(assignToPart);
        assignBC.add(assignToSupplier);
        
        //min size txtfield
        Dimension txtDim = new Dimension(200,20);
        subPartCB.setMinimumSize(txtDim);
        SupplierCB.setMinimumSize(txtDim);
        SupplierSKU.setMinimumSize(txtDim);
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
        jp.add(new JLabel("Part to Link"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Supplier"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Supplier Id"), cst);
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
        jp.add(subPartCB, cst);
        cst.gridy = (i+=2);
        jp.add(SupplierCB, cst);
        cst.gridy = (i+=2);
        jp.add(SupplierSKU, cst);
        cst.gridy = (i+=2);
        jp.add(manufacturerCB, cst);
        cst.gridy = (i+=2);
        jp.add(manufMPN, cst);
        cst.gridy = (i+=2);
        jp.add(assignBC, cst); 
        
        cst.anchor=GridBagConstraints.EAST;
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
