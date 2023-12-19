/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author legen
 */
public class CenterDialog extends JDialog {
    
    private Frame owner;

    public CenterDialog(Frame owner, String title, Boolean modal) {
        super(owner, title, modal); // true means it's modal, false means it's modeless
        this.owner = owner;
    }
    public CenterDialog(Frame owner, String title) {
        super(owner, title); // true means it's modal, false means it's modeless
        this.owner = owner;
    }
    public CenterDialog(Frame owner, Boolean modal) {
        super(owner, modal); // true means it's modal, false means it's modeless
        this.owner = owner;
        this.centerDialogBox();
    }
    public CenterDialog(Frame owner) {
        super(owner); // true means it's modal, false means it's modeless
        this.owner = owner;
    }
    
    public void centerDialogBox() {
        
        Dimension parentSize = this.owner.getSize();
        Dimension dialogSize = this.getSize();
        Point parentLocn = this.owner.getLocationOnScreen();

        int locnX = parentLocn.x + (parentSize.width - dialogSize.width) / 2;
        int locnY = parentLocn.y + (parentSize.height - dialogSize.height) / 2;

        this.setLocation(locnX, locnY);
    }
    
    @Override
    public void setVisible(boolean b){
       this.centerDialogBox();
       super.setVisible(b);
    }
}
