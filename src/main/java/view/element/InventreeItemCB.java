/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element;

import Inventree.item.InventreeItem;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author legen
 */
public class InventreeItemCB extends JComboBox {
    public InventreeItemCB(){
        super();
        this.setRenderer(new InventreeItemCBRenderer());
        //this.setEditor(new InventreeItemCBEditor());
    }
    
    public void setSelectedId(String idName){
        if(idName == null)
            return;
        for (int i = 0; i < this.getItemCount(); i++) {
        InventreeItem sl = (InventreeItem) this.getItemAt(i);
        //System.out.println(this.getClass()+" --> compare :"+locName+ " == "+sl.getId()+"("+(String.valueOf(sl.getId()) == locName)+")");
        if(idName.equals(String.valueOf(sl.getId()))){
            this.getModel().setSelectedItem(sl);
            break;
        }
      }
    }
    public String getSelectedId(){
        InventreeItem sl = (InventreeItem) this.getSelectedItem();
        if (sl == null)
            return null;
        return String.valueOf(sl.getId());
    }    
    
}



class InventreeItemCBRenderer extends BasicComboBoxRenderer {
  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    if (value != null) {
      InventreeItem item = (InventreeItem) value;
      setText(item.getDisplayName());
    }
    return this;
  }
}
