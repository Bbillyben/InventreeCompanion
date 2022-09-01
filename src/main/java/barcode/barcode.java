/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barcode;

import data.CONSTANT;
import java.io.Serializable;

/**
 *
 * @author blegendre
 */
public class barcode implements Serializable {
    
    public String code;
    public String EAN;
    public String type;
    
    public barcode(){
        
    }
    
    
    @Override 
    public String toString(){
        String str = super.toString();
        str += "\n    - code :"+code;
        str += "\n    - EAN :"+EAN;
        str += "\n    - type :"+type;
        return str;
    }
    
}
