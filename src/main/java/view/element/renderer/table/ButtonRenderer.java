/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element.renderer.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author legen
 */
public class ButtonRenderer extends JButton implements TableCellRenderer 
  {
    private String forceString;
    public ButtonRenderer() {
      setOpaque(true);
      //this.setPreferredSize(new Dimension(10,10));
      this.setEnabled(true);
    }
    public ButtonRenderer(String txt) {
      setOpaque(true);
      this.setEnabled(true);
      forceString = txt;
    }
    public void setIcon(ImageIcon ic){
        
        super.setIcon(new ImageIcon(ic.getImage().getScaledInstance(15, 15,java.awt.Image.SCALE_SMOOTH)));
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(forceString != null){
            setText(forceString);
        }/*else{
            setText((value == null) ? " X " : value.toString());
        }*/
        
      return this;
    }
  }

