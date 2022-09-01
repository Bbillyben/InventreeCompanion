/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package controller;

import model.Model;
import view.iView;

/**
 *
 * @author blegendre
 */
public class iController {
    protected final Model model;
    protected iView view;
    
    public iController(Model m){
        model = m;
    }
    public void setView(iView vi){
        view = vi;
    }
    public void display(boolean state) {
        view.setVisible(state);        
    }
}
