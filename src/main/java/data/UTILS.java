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
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
    // ================================ pour les barcode
    private static final Pattern CLEAN_BC_PATTERN=Pattern.compile("[\s \r\n]*", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    public static String cleanBC(String bc){
        return CLEAN_BC_PATTERN.matcher(bc).replaceAll("");
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
            setCheckBorder(tf, false);
            return false;
        }else{
            tf.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            setCheckBorder(tf, true);
            return true;
        }
    }
    
    public static boolean checkComboBox(JComboBox cb){
        InventreeItem iv = (InventreeItem) cb.getSelectedItem();
        if(iv == null || iv.getId() == 0 || (iv.getName() == null ? NULL_ITEM == null : iv.getName().equals(NULL_ITEM))){
            setCheckBorder(cb, false);
            return false;
        }else{
            setCheckBorder(cb, true);
            return true;
        }
    }
    
    public static Boolean setCheckBorder(JComponent cmp, Boolean isOk){
        if(isOk){
            cmp.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
        }else{
            cmp.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        return isOk;
    }
    
    /**Constraint an int between 2 bornes
     * 
     * @param value current value
     * @param minV minimal value
     * @param maxV maximale value
     * @return 
     *      - value if min<value<max
     *      - min if value<min
     *      - max if value > max
     * 
     */
    public static int cstIntbtw(int value, int minV, int maxV){
        return Math.max(minV, Math.min(value, maxV));
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
