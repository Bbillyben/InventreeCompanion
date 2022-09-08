/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package controller;

import listeners.BarcodeListener;
import listeners.InfoListener;
import listeners.ListenerI;
import model.Model;
import view.iView;

/**
 *
 * @author blegendre
 */
public class SoundController extends iController {
    
     public SoundController(Model md){
        super(md);

    }
     @Override
    public void setView(iView vi){
        super.setView(vi);
        model.addEventListener(view, BarcodeListener.class);
        model.addEventListener(view, ListenerI.class);
        model.addEventListener(view, InfoListener.class);
    }
}
