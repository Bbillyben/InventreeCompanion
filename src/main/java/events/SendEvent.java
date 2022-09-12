/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package events;

import Inventree.item.StockItem;

/**
 *
 * @author blegendre
 */
public class SendEvent extends iEvent {
    
    public static final String START_SENDING = "start_sending";
    public static final String END_SENDING = "end_sending";
    public static final String ITEM_SENDING = "item_sending";
    
    public static final String CREATE_ITEM_START = "create_item_start";
    public static final String CREATE_ITEM_FAILED = "create_item_failed";
    public static final String CREATE_ITEM_SUCESS = "create_item_success";
    
    public static final String LINK_ITEM_START = "link_item_start";
    public static final String LINK_ITEM_FAILED = "link_item_failed";
    public static final String LINK_ITEM_SUCESS = "link_item_success";
    
    public static final String EXPORT_SCAN_LIST = "export_scan_list";
    
    public StockItem item;
    public SendEvent(Object source) {
         super(source);
         type = null;
         item = null;
    }

    public SendEvent(Object source, String EventName) {
         super(source,EventName);
         item = null;
    }
    public SendEvent(Object source, String EventName, StockItem itemSend) {
         super(source,EventName);
         item = itemSend;
    }
}
