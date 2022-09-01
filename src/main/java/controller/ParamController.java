/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package controller;

import data.CONSTANT;
import data.IniStruct;
import events.NavEvent;
import events.iEvent;
import listeners.ListenerI;
import listeners.NavigationListener;
import model.Model;
import view.ParamView;

/**
 *
 * @author blegendre
 */
public class ParamController extends iController implements NavigationListener {
    
     public ParamController(Model md){
        super(md);
        model.addEventListener(this, NavigationListener.class);
    }
    
     public void saveParams(IniStruct iniH){
         model.preferenceUpdate(iniH);
     }
     
     public void setView(ParamView vi){
         super.setView(vi);
         model.addEventListener(vi,ListenerI.class);
     }

    @Override
    public void eventRecept(iEvent e) {
        switch (e.type) {
            case NavEvent.NAVIGATE:
                String page = ((NavEvent) e).destPage;
                this.display(page.equals(CONSTANT.PAGE_PARAM));
                break;
        }
    }
    
}
