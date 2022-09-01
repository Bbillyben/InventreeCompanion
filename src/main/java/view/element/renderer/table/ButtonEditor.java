/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element.renderer.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author legen
 */
public class ButtonEditor extends DefaultCellEditor implements ActionListener 
  {
    private String label;
    private boolean sure = false;
    public ButtonEditor(JCheckBox checkBox)
    {
      super(checkBox);
    }
    public ButtonEditor(JCheckBox checkBox, String txt)
    {
      super(checkBox);
      label = txt;
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
    boolean isSelected, int row, int column) 
    {
        if(label == null)
            label = (value == null) ? " x " : value.toString();
        System.out.println("view.element.renderer.table.ButtonEditor.getTableCellEditorComponent()"+label);
      JButton button = new JButton();
      //button.setText(label);
      ImageIcon ic = new ImageIcon(getClass().getClassLoader().getResource(label));
      
      button.setIcon(new ImageIcon(ic.getImage().getScaledInstance(15, 15,java.awt.Image.SCALE_SMOOTH)));
      button.addActionListener(this);
      return button;
    }
    public Object getCellEditorValue() 
    {
      return sure;//new String(label);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(this.getComponent(),"Are you sure you want to delete this row?", "Delete Barcode Scan",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE);
        sure = result == 0;
        if(result == 0){
            this.fireEditingStopped();
        }
        
    }
  }