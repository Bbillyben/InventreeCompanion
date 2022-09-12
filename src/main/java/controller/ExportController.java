/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package controller;

import listeners.ExportListener;
import model.Model;
import view.iView;

/**
 *
 * @author blegendre
 */
public class ExportController extends iController {
    
     public ExportController(Model md){
        super(md);
       
    }
     
    public void exportFeedback(String status, String filepath, String rqs){
        model.exportFeedback(status, filepath, rqs);
    }
    @Override
    public void setView(iView vi){
        super.setView(vi);
        model.addEventListener(view, ExportListener.class);
    }
}
