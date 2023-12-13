/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package barcodeDecoder;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author blegendre
 */
public class GS1DataMatrixDecoder extends EAN128Decoder {
    protected  char defaultBreak = '#';       
    
    public List<String> unknownAI;
    
    protected final Map<String, String> data = Maps.newHashMap();
    
    protected boolean useBCQuantity=false;
    
    public GS1DataMatrixDecoder() {
        
    }
    
    @Override
    public String type(){
        return "GS1DM";
    }
    @Override
     public String startCode(){
        return "]D2";
    }
    @Override
    public Boolean isMultiple(){
        return false;
    }
    
    @Override
    protected void buildEquivSearch(){
        equivSearch= new HashMap<>(){
            {
                put("EAN", new String[]{"240", "01"});
                put("ExpiryDate", new String[]{"17", "15"});
                put("batch", new String[]{"10"});
            }
        };
    };
    
    @Override
    protected void buildInfos(){
        ai("01", "GTIN", 2, 14, true);
        ai("10", "BATCH", 2, 14, true);
        ai("11", "ProductionDate_YYMMDD", 2, 6, false);
        ai("12", "DueDate_YYMMDD", 2, 6, false);
        ai("13", "PackagingDate_YYMMDD", 2, 6, false);
        ai("15", "BestBefore_YYMMDD", 2, 6, false);
        ai("16", "SellByDate_YYMMDD", 2, 6, false);
        ai("17", "ExpirationDate_YYMMDD", 2, 6, false);
        ai("21", "ExpirationDate_YYMMDD", 2, 25, true);
        ai("240", "AdditionnalProductId", 2, 25, true);
    }
    
    
}
