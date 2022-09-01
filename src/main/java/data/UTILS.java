/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package data;

import Inventree.item.InventreeItem;
import Inventree.item.StockItem;
import java.awt.Color;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import static view.element.LinkPartDialog.NULL_ITEM;

/**
 *
 * @author blegendre
 */
public class UTILS {
    
    public static boolean isNullOrEmpty(String str){
        return (str == null || str.isEmpty() || str.isBlank());
    }
    
    // pour l'affichage du status des SI
    
    public static String getSIInfo(StockItem si){
        String str ="";
        // le code barre
        str = "barcode :"+si.EAN;
        // le batch 
        if(si.batch != null)
            str+=" (batch :"+si.batch+")";
        // le date expiration
        if(si.expiry_date != null)
            str+= " Exp. Date :"+si.expiry_date;
        // le nom de la pièce
        if(si.partitem !=null)
            str+=" - "+si.partitem.getName();
        //localisation 
        str+= " / stock :"+si.stocklocation.getName();
        
        // action + quantity
        str += " -> Action :"+si.action +" ("+si.quantity+")";
        
        
        // le status 
        str+="  / Status :<font style='";
        if(CONSTANT.ERROR_STATUS.contains(si.getStatus())){
            str+= "color:red'>";
        }else if(CONSTANT.STATUS_SEND.equals(si.getStatus())){
            str+= "color:green'>";
        }else{
             str+= "color:orange'>";
        }
        str+=si.getStatus()+"</font>";
        
        
        
        return str;
    }
    
    // ================================= POUR LES DATES
    public static String formatDate(LocalDate ld, String format){
        if(ld == null)
            return null;
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
       return ld.format(formatter);
    }
    
    // test sur des éléments typ textfield
    public static boolean checkTextField(JTextField tf){
        if(tf.getText().isEmpty() || tf.getText().isBlank()){
            tf.setBorder(BorderFactory.createLineBorder(Color.RED));
            return false;
        }else{
            tf.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            return true;
        }
    }
    
    public static boolean checkComboBox(JComboBox cb){
        InventreeItem iv = (InventreeItem) cb.getSelectedItem();
        if(iv == null || iv.getId() == 0 || iv.getName() == NULL_ITEM){
            cb.setBorder(BorderFactory.createLineBorder(Color.RED));
            return false;
        }else{
            cb.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            return true;
        }
    }
    // ================================= GESTION DE FICHIER
        
    public static void removeFile(String filepath){
        File file = new File(filepath);
 
        if (file.delete()) {
            System.out.println("File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file");
        }
        
    }
}
