/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blegendre
 */
public class SerialHandler {
    
    public static boolean writeObjectToFile(Serializable obj, String filePath){
        try {
                FileOutputStream f = new FileOutputStream(filePath);
                ObjectOutputStream s = new ObjectOutputStream(f);
                s.writeObject(obj);
                s.close();
                f.close();
        } catch (IOException error) {
           error.printStackTrace();
           return false;
           
       }
        System.out.println("Successfully saved!");
        return true;
        
    }
    
    
    
    public static <T> T readObjectFromFile(Class<T> cl, String filePath){
        
        T obj = null;
        try{
            FileInputStream in = new FileInputStream(filePath);
            ObjectInputStream s = new ObjectInputStream(in);
            obj = cl.cast(s.readObject());
            s.close();
            in.close();
            
        }catch (ClassNotFoundException | IOException e) {
            System.out.println("[SerialHandler] unable to open file");
          return null;
        }
        System.out.println("SerialHandler > sucessfully loaded");
        return obj;
    }
    

    
    
}
