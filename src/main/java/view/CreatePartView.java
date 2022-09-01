/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import Inventree.item.StockItem;
import controller.CreatePartController;
import events.ParamEvent;
import events.SendEvent;
import events.iEvent;
import javax.swing.JFrame;
import listeners.ParamListener;
import listeners.SendListener;
import view.element.CreatePartDialog;
import view.element.LinkPartDialog;

/**
 *
 * @author legen
 */
public class CreatePartView implements iView, SendListener, ParamListener{
    protected CreatePartDialog cpD;
    protected LinkPartDialog lpD;
    protected CreatePartController controller;
    protected JFrame mainFrame;
    
    public CreatePartView(CreatePartController ctrl, JFrame f){
        controller = ctrl;
        mainFrame = f;
        buildView();
    }
    private void buildView(){
        cpD = new CreatePartDialog(controller, mainFrame, "Create new Part", true);
        lpD= new LinkPartDialog(controller, mainFrame, "Link Item to Part", true);
    }
    
    protected void startCreat(StockItem si){
        System.out.println("view.CreatePartView.startCreat()");
        cpD.ini(si);
        cpD.pack();
        cpD.setVisible(true);
    }
    protected void stopCreat(StockItem si){
        cpD.setVisible(false);
    }
    protected void startLink(StockItem si){
        lpD.ini(si);
        lpD.pack();
        lpD.setVisible(true);
    }
    protected void stopLink(StockItem si){
        lpD.setVisible(false);
    }
    @Override
    public void eventRecept(iEvent e) {
        System.out.println("view.CreatePartView.eventRecept() "+e);
        switch(e.type){
            case SendEvent.CREATE_ITEM_START:
                startCreat(((SendEvent) e).item);
                break;
            case SendEvent.CREATE_ITEM_SUCESS:
                stopCreat(((SendEvent) e).item);
                break;
            case SendEvent.LINK_ITEM_START:
                startLink(((SendEvent) e).item);
                break;
            case SendEvent.LINK_ITEM_SUCESS:
                stopLink(((SendEvent) e).item);
                break;
            case ParamEvent.PARAM_LOADED:
                cpD.updateDatas(((ParamEvent) e).ivl);
                lpD.updateDatas(((ParamEvent) e).ivl);
                break;
        }
    }
    
    
    
    @Override
    public void setVisible(boolean state) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
