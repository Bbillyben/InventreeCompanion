/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package barcodeDecoder;

import data.UTILS;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author blegendre
 */
public class DataMatrixDecoder extends EAN128Decoder {
    @Override
    public String type(){
        return "DM";
    }
    @Override
     public String startCode(){
        return "]D1";
    }
    @Override
    public Boolean isMultiple(){
        return false;
    }
    
    @Override
    protected void decodeComplex(ArrayList<String> barcodes, char cBreak){
      System.out.println(this+"initiate decode of "+barcodes);
      this.initDecode();
      String pattern = "(" + this.startCode() + "[^\\]]*)";
      Pattern r = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
      
      Pattern p = Pattern.compile("[\s ]{2,}", Pattern.CASE_INSENSITIVE);
      Pattern k = Pattern.compile("\\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
      Pattern d = Pattern.compile("[#]{2,}", Pattern.CASE_INSENSITIVE);
      
      for(String str : barcodes){
        str=p.matcher(str).replaceAll(String.valueOf(cBreak));
        str=k.matcher(str).replaceAll(matchResult -> "#" + matchResult.group(1));
        str=d.matcher(str).replaceAll("#");
        str=UTILS.cleanBC(str);
        Matcher m = r.matcher(str);
        while (m.find()) {
             if(isSupported(m.group(1))){
              this.decodeBC(m.group(1), cBreak);
             }
         }     
      }
     
      if (data.isEmpty())
            throw new IllegalArgumentException("This is not a EAN-128 barcode");      
    }
}
