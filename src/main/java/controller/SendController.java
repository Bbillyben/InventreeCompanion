/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package controller;

import Inventree.item.StockItem;
import data.CONSTANT;
import events.NavEvent;
import events.iEvent;
import listeners.BarcodeListener;
import listeners.ListenerI;
import listeners.NavigationListener;
import listeners.ParamListener;
import listeners.SendListener;
import model.Model;
import view.ScanView;
import view.iView;

/**
 *
 * @author blegendre
 */
public class SendController extends iController implements NavigationListener {

    public SendController(Model md){
        super(md);
        md.addEventListener((NavigationListener) this, NavigationListener.class);
    }
    
    @Override
    public void display(boolean state){
        super.display(state);
        if(state){
           model.addEventListener(view, BarcodeListener.class);
           model.addEventListener(view, SendListener.class);
        }else{
            model.removeEventListener(view, BarcodeListener.class);
             model.removeEventListener(view, SendListener.class);
        }
    }
    public void updateStockItem(StockItem si){
        if(si==null)
            return;
        model.reCheckStockItem(si);
    }
    public void createPart(StockItem si){
        if(si.partitem != null && si.partitem.getId() != 0)/// déjà une part référencé
            return;
        model.createPartLaunch(si);
    }
    public void linkItem(StockItem si){
        if(si.partitem != null && si.partitem.getId() != 0)/// déjà une part référencé
            return;
        model.linkPartLaunch(si);
    }
    
    public void sendToServer(String sendType){
       
        model.sendToServer(sendType);
    }
    
    
    public void copyValue(String copyValue){
        model.addToClipBoard(copyValue);
    }
    @Override
    public void setView(iView vi){
        super.setView(vi);
        model.addEventListener(view, ListenerI.class);
        model.addEventListener(view, ParamListener.class);
    }
    @Override
    public void eventRecept(iEvent e) {
        /*System.out.println("controller.SendController.eventRecept() navigate :"+((NavEvent) e).destPage
        +" - "+e.type+"/"+NavEvent.NAVIGATE+" ("+(e.type==NavEvent.NAVIGATE)+")"
        +" ("+(((NavEvent) e).destPage.equals(CONSTANT.PAGE_SEND))+")"
        );*/
        switch(e.type){
            case NavEvent.NAVIGATE :
                this.display(((NavEvent) e).destPage.equals(CONSTANT.PAGE_SEND));
                break;
        }
    }
    
}
