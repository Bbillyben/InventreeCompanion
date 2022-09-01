/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package events;

import javax.swing.event.TableModelEvent;
import view.model.StockItemModel;

/**
 *
 * @author legen
 */
public class InventreeTableModelEvent extends TableModelEvent {
    
    public static final int ADD_PART = 2;
    public static final int DELETE_ITEM = -2;
    public static final int LINK_ITEM = 3;

    public InventreeTableModelEvent(StockItemModel aThis, int row, int row0, int column) {
        super(aThis,row, row0, column);
    }
    
    public void setType(int typeE){
        this.type = typeE;
    }
}
