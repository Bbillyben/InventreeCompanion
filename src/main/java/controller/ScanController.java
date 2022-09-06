/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package controller;

import Inventree.item.StockItem;
import data.CONSTANT;
import data.IniStruct;
import events.NavEvent;
import events.iEvent;
import listeners.BarcodeListener;
import listeners.ListenerI;
import listeners.NavigationListener;
import listeners.ParamListener;
import model.Model;
import view.ScanView;
import view.iView;

/**
 *
 * @author blegendre
 */
public class ScanController extends iController implements NavigationListener {
   
    public ScanController(Model md){
        super(md);
        md.addEventListener(this, NavigationListener.class);
    }
    
    public void addStockItem(StockItem si){
        model.addStockItem(si);
    }
    public void updateStockItem(StockItem si){
        model.reCheckStockItem(si);
    }
    public void createPart(StockItem si){
        if(si.partitem != null)/// déjà une part référencé
            return;
        model.createPartLaunch(si);
    }
    
    public void changeStatus(String status){
         model.changeStatus(status);
    }
    public void changeStockLoc(String newLoc){
        if(newLoc == null)
            return;
        model.changeLocationSelection(newLoc);
        
    }
    public void changeUseQuantity(boolean usq){
        model.changeUseQuantity(usq);
    }
    
    
    public void copyValue(String copyValue){
        model.addToClipBoard(copyValue);
    }
    
    @Override
    public void setView(iView vi){
        super.setView(vi);
        model.addEventListener(view, ListenerI.class);
        model.addEventListener(view, ParamListener.class);
        model.addEventListener(view,BarcodeListener.class);
    }
    @Override
    public void eventRecept(iEvent e) {
        switch(e.type){
            case NavEvent.NAVIGATE :
                this.display(((NavEvent) e).destPage.equals(CONSTANT.PAGE_SCAN));
                break;
        }
    }
    
}
