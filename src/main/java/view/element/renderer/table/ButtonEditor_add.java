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
public class ButtonEditor_add extends DefaultCellEditor implements ActionListener 
  {
    private String label;
    public ButtonEditor_add(JCheckBox checkBox)
    {
      super(checkBox);
    }
    public ButtonEditor_add(JCheckBox checkBox, String txt)
    {
      super(checkBox);
      label = txt;
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
    boolean isSelected, int row, int column) 
    {
      JButton button = new JButton();
      
      ImageIcon ic = new ImageIcon(getClass().getClassLoader().getResource(label));
      button.setIcon(new ImageIcon(ic.getImage().getScaledInstance(15, 15,java.awt.Image.SCALE_SMOOTH)));

      button.addActionListener(this);
      return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            this.fireEditingStopped();        
    }
  }