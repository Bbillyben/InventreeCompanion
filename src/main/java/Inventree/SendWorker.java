/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package Inventree;

import Inventree.item.StockItem;
import data.CONSTANT;
import model.Model;

/**
 *
 * @author blegendre
 */
public class SendWorker extends Thread {
    private final Model model;
    private final APIConnector conn;
    
    private StockItem si;
    public SendWorker(Model mod, APIConnector bConn){
        model = mod;
        conn = bConn;
    }
     public void setItem(StockItem sii){
        si = sii;
    }
      @Override
    public void run() {
        if(si!=null)
            si.setStatus(CONSTANT.STATUS_ON_SENDING);
        conn.sendStockItem(si);
    }
}
