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
import javax.swing.JButton;
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
    protected InventreeItemFilterCB supplyerCB;
    protected JTextField supplyerSKU;
    protected InventreeItemFilterCB manufacturerCB;
    protected JTextField manufMPN;
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

        supplyerSKU.setText(si.EAN);
        manufMPN.setText(si.EAN);
        subPartCB.setSelectedIndex(0);
        supplyerCB.setSelectedIndex(0);
    }
    private void startSaveProcess(){
        boolean status = true;
        if(!UTILS.checkComboBox(subPartCB))
            status = false;
        if(!UTILS.checkComboBox(manufacturerCB))
                status = false;
        if(!UTILS.checkTextField(manufMPN))
            status = false;
        /*
        if(!UTILS.checkTextField(supplyerSKU))
            status = false;
        if(!UTILS.checkComboBox(supplyerCB)){
            if(!UTILS.checkComboBox(manufacturerCB)){
                status = false;
            }else{
                 supplyerCB.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            }
         }else{
            manufacturerCB.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }*/
        
        if(!status){
            JOptionPane.showMessageDialog(this, "Some Required Information are missing");
            return;
        }
        
        controller.linkPart(
                currentStockItem,
                ((InventreeItem) subPartCB.getSelectedItem()).getId(),
                ((InventreeItem) supplyerCB.getSelectedItem()).getId(),
                supplyerSKU.getText(),
                ((InventreeItem) manufacturerCB.getSelectedItem()).getId(),
                manufMPN.getText()
        );
        
           
    }  
    
    public void updateDatas(InventreeLists ivl){

        subPartCB.removeAllItems();
        subPartCB.addItem(new PartItem(NULL_ITEM, 0));
        for(PartItem pi : ivl.partTemplate){
            subPartCB.addItem(pi);
        }
        subPartCB.init();
        
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
        subPartCB = new InventreeItemFilterCB();
        supplyerCB = new InventreeItemFilterCB();
        supplyerSKU = new JTextField(txtFSize);
        manufacturerCB = new InventreeItemFilterCB();
        manufMPN = new JTextField(txtFSize);
        saveBtn = new JButton("Save");
        
        //min size txtfield
        Dimension txtDim = new Dimension(200,20);
        subPartCB.setMinimumSize(txtDim);
        supplyerCB.setMinimumSize(txtDim);
        supplyerSKU.setMinimumSize(txtDim);
        manufacturerCB.setMinimumSize(txtDim);
        manufMPN.setMinimumSize(txtDim);
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
        jp.add(new JLabel("Supplier Id (set as barcode)"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Manufacturer"), cst);
        cst.gridy = (i+=2);
        jp.add(new JLabel("Manufacturer Id (set as barcode)"), cst);
        cst.gridy = (i+=2);
        
        cst.anchor = GridBagConstraints.WEST;
        i=0;
        cst.gridx=1;
        cst.gridy=i;
        jp.add(subPartCB, cst);
        cst.gridy = (i+=2);
        jp.add(supplyerCB, cst);
        cst.gridy = (i+=2);
        jp.add(supplyerSKU, cst);
        cst.gridy = (i+=2);
        jp.add(manufacturerCB, cst);
        cst.gridy = (i+=2);
        jp.add(manufMPN, cst);
        
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
