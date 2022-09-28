/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barcodeDecoder;

import Inventree.item.StockItem;
import barcode.barcode;
import com.google.common.collect.Maps;
import data.UTILS;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author legen
 */
public class EAN128Decoder extends BarcodeDecoder {
    private final char defaultBreak = '#';// the default printed caracter break returned by the handscanner (=> to be parametered in the hand scanner)
    private static final String startCode = "]C1";// the printed ~F1 caracter that start the sequence => maybe handscanner dependant
    
    protected static String type = "EAN128";
    
    private static final Map<String, AII> aiinfo = Maps.newHashMap();
    public List<String> unknownAI;
    
    private final Map<String, String> data = Maps.newHashMap();
    
    protected boolean useBCQuantity=false;
    
    
    
    static class AII {
        final int minLength;
        final int maxLength;
        final String identifier;
        final boolean hasBreak;

        public AII(String id, String ident, int minLength, int maxLength, boolean hasbreak) {
          this.minLength = minLength;
          this.maxLength = maxLength;
          this.identifier = ident;
          this.hasBreak = hasbreak;
    }
  }
    
    static {
        ai("00", "SerialShippingContainerCode", 2, 18, false);
        ai("01", "EAN-NumberOfTradingUnit", 2, 14, false);
        ai("02", "EAN-NumberOfTheWaresInTheShippingUnit", 2, 14, false);
        ai("10", "Charge_Number", 2, 20, true);
        ai("11", "ProducerDate_JJMMDD", 2, 6, false);
        ai("12", "DueDate_JJMMDD", 2, 6, false);
        ai("13", "PackingDate_JJMMDD", 2, 6, false);
        ai("15", "MinimumDurabilityDate_JJMMDD", 2, 6, false);
        ai("17", "ExpiryDate_JJMMDD", 2, 6, false);
        ai("20", "ProductModel", 2, 2, false);
        ai("21", "SerialNumber", 2, 20, true);
        ai("22", "HIBCCNumber", 2, 29, false);
        ai("240", "PruductIdentificationOfProducer", 3, 30, true);
        ai("241", "CustomerPartsNumber", 3, 30, true);
        ai("250", "SerialNumberOfAIntegratedModule", 3, 30, true);
        ai("251", "ReferenceToTheBasisUnit", 3, 30, true);
        ai("252", "GlobalIdentifierSerialisedForTrade", 3, 2, false);
        ai("30", "AmountInParts", 2, 8, true);
        ai("310d", "NetWeight_Kilogram", 4, 6, false);
        ai("311d", "Length_Meter", 4, 6, false);
        ai("312d", "Width_Meter", 4, 6, false);
        ai("313d", "Heigth_Meter", 4, 6, false);
        ai("314d", "Surface_SquareMeter", 4, 6, false);
        ai("315d", "NetVolume_Liters", 4, 6, false);
        ai("316d", "NetVolume_CubicMeters", 4, 6, false);
        ai("320d", "NetWeight_Pounds", 4, 6, false);
        ai("321d", "Length_Inches", 4, 6, false);
        ai("322d", "Length_Feet", 4, 6, false);
        ai("323d", "Length_Yards", 4, 6, false);
        ai("324d", "Width_Inches", 4, 6, false);
        ai("325d", "Width_Feed", 4, 6, false);
        ai("326d", "Width_Yards", 4, 6, false);
        ai("327d", "Heigth_Inches", 4, 6, false);
        ai("328d", "Heigth_Feed", 4, 6, false);
        ai("329d", "Heigth_Yards", 4, 6, false);
        ai("330d", "GrossWeight_Kilogram", 4, 6, false);
        ai("331d", "Length_Meter", 4, 6, false);
        ai("332d", "Width_Meter", 4, 6, false);
        ai("333d", "Heigth_Meter", 4, 6, false);
        ai("334d", "Surface_SquareMeter", 4, 6, false);
        ai("335d", "GrossVolume_Liters", 4, 6, false);
        ai("336d", "GrossVolume_CubicMeters", 4, 6, false);
        ai("337d", "KilogramPerSquareMeter", 4, 6, false);
        ai("340d", "GrossWeight_Pounds", 4, 6, false);
        ai("341d", "Length_Inches", 4, 6, false);
        ai("342d", "Length_Feet", 4, 6, false);
        ai("343d", "Length_Yards", 4, 6, false);
        ai("344d", "Width_Inches", 4, 6, false);
        ai("345d", "Width_Feed", 4, 6, false);
        ai("346d", "Width_Yards", 4, 6, false);
        ai("347d", "Heigth_Inches", 4, 6, false);
        ai("348d", "Heigth_Feed", 4, 6, false);
        ai("349d", "Heigth_Yards", 4, 6, false);
        ai("350d", "Surface_SquareInches", 4, 6, false);
        ai("351d", "Surface_SquareFeet", 4, 6, false);
        ai("352d", "Surface_SquareYards", 4, 6, false);
        ai("353d", "Surface_SquareInches", 4, 6, false);
        ai("354d", "Surface_SquareFeed", 4, 6, false);
        ai("355d", "Surface_SquareYards", 4, 6, false);
        ai("356d", "NetWeight_TroyOunces", 4, 6, false);
        ai("357d", "NetVolume_Ounces", 4, 6, false);
        ai("360d", "NetVolume_Quarts", 4, 6, false);
        ai("361d", "NetVolume_Gallonen", 4, 6, false);
        ai("362d", "GrossVolume_Quarts", 4, 6, false);
        ai("363d", "GrossVolume_Gallonen", 4, 6, false);
        ai("364d", "NetVolume_CubicInches", 4, 6, false);
        ai("365d", "NetVolume_CubicFeet", 4, 6, false);
        ai("366d", "NetVolume_CubicYards", 4, 6, false);
        ai("367d", "GrossVolume_CubicInches", 4, 6, false);
        ai("368d", "GrossVolume_CubicFeet", 4, 6, false);
        ai("369d", "GrossVolume_CubicYards", 4, 6, false);
        ai("37", "QuantityInParts", 2, 8, true);
        ai("390d", "AmountDue_DefinedValutaBand", 4, 15, true);
        ai("391d", "AmountDue_WithISOValutaCode", 4, 18, true);
        ai("392d", "BePayingAmount_DefinedValutaBand", 4, 15, true);
        ai("393d", "BePayingAmount_WithISOValutaCode", 4, 18, true);
        ai("400", "JobNumberOfGoodsRecipient", 3, 30, true);
        ai("401", "ShippingNumber", 3, 30, true);
        ai("402", "DeliveryNumber", 3, 17, false);
        ai("403", "RoutingCode", 3, 30, true);
        ai("410", "EAN_UCC_GlobalLocationNumber(GLN)_GoodsRecipient", 3, 13, false);
        ai("411", "EAN_UCC_GlobalLocationNumber(GLN)_InvoiceRecipient", 3, 13, false);
        ai("412", "EAN_UCC_GlobalLocationNumber(GLN)_Distributor", 3, 13, false);
        ai("413", "EAN_UCC_GlobalLocationNumber(GLN)_FinalRecipient", 3, 13, false);
        ai("414", "EAN_UCC_GlobalLocationNumber(GLN)_PhysicalLocation", 3, 13, false);
        ai("415", "EAN_UCC_GlobalLocationNumber(GLN)_ToBilligParticipant", 3, 13, false);
        ai("420", "ZipCodeOfRecipient_withoutCountryCode", 3, 20, true);
        ai("421", "ZipCodeOfRecipient_withCountryCode", 3, 12, true);
        ai("422", "BasisCountryOfTheWares_ISO3166Format", 3, 3, false);
        ai("7001", "Nato Stock Number", 4, 13, false);
        ai("8001", "RolesProducts", 4, 14, false);
        ai("8002", "SerialNumberForMobilePhones", 4, 20, true);
        ai("8003", "GlobalReturnableAssetIdentifier", 4, 34, true);
        ai("8004", "GlobalIndividualAssetIdentifier", 4, 30, true);
        ai("8005", "SalesPricePerUnit", 4, 6, false);
        ai("8006", "IdentifikationOfAProductComponent", 4, 18, false);
        ai("8007", "IBAN", 4, 30, true);
        ai("8008", "DataAndTimeOfManufacturing", 4, 12, true);
        ai("8018", "GlobalServiceRelationNumber", 4, 18, false);
        ai("8020", "NumberBillCoverNumber", 4, 25, false);
        ai("8100", "CouponExtendedCode_NSC_offerCcode", 4, 10, false);
        ai("8101", "CouponExtendedCode_NSC_offerCcode_EndOfOfferCode", 4, 14, false);
        ai("8102", "CouponExtendedCode_NSC", 4, 6, false);
        ai("90", "InformationForBilateralCoordinatedApplications", 2, 30, true);
      }
    
    private static HashMap<String, String[]> equivSearch = new HashMap<>(){
        {
            put("EAN", new String[]{"01","02"});
            put("ExpiryDate", new String[]{"17", "12"});
            put("batch", new String[]{"10", "20"});
            put("amount", new String[]{"30"});
        }
    };
    private static void ai(String id, String ident, int minLength, int maxLength, boolean hasbreak) {
        aiinfo.put(id, new AII(id, ident, minLength, maxLength, hasbreak));
    }
    private static void ai(String id,String ident, int length, boolean hasbreak) {
        aiinfo.put(id, new AII(id, ident, length, length, hasbreak));
    }
    
    
    
    /////
    @Override
    public  boolean isSupported(ArrayList<String> bc){
        return isSupported(bc.get(0));
    }
    public boolean isSupported(String str){
        return str.toUpperCase().indexOf(startCode) == 0;
    }
    
    public EAN128Decoder() {
      
    }
    /*public EAN128Decoder(String s) {
        this.decodeBarcode(s, defaultBreak);
    }
    public EAN128Decoder(String s, char fnc1) {
        this.decodeBarcode(s, fnc1);
    }*/
    
    @Override
    public void decodeBarcode(ArrayList<String> s){
        //this.initDecode();
        this.decodeComplex(s, defaultBreak);
    }
    @Override
    public void decodeBarcode(ArrayList<String> s, boolean useQ){
        useBCQuantity = useQ;
        //this.initDecode();
        this.decodeComplex(s, defaultBreak);
    }
    public void decodeBarcode(ArrayList<String> s, char fnc1){
        //this.initDecode();
        this.decodeComplex(s, fnc1);
    }
    @Override
    public barcode getBarcode() {
       barcode bc = new barcode();
       bc.EAN = getFristAI(equivSearch.get("EAN"));
       bc.code = getBarcodeString();
       bc.type = EAN128Decoder.type;
       
       return bc;
    }
    @Override
    public void processStockItem(StockItem si) {
        si.barcode = getBarcode();
        si.batch = getFristAI(equivSearch.get("batch"));
        si.expiry_date = transformDateStr(getFristAI(equivSearch.get("ExpiryDate")));
        si.EAN = si.barcode.EAN;
        if(useBCQuantity==true && getFristAI(equivSearch.get("amount"))!=null){
            si.quantity=Integer.valueOf(getFristAI(equivSearch.get("amount")));
        }else{
            si.quantity=1;
        }            
    }
    
    
    
    @Override
    public String getBarcodeString() {
        return recomposeBC(defaultBreak);
    }
    @Override
    public String getBarcodeString(char sep){
        return recomposeBC(sep);
    }
    
    @Override
    public Boolean isMultiple(){
        return true;
    }
    
  
    protected void decodeComplex(ArrayList<String> barcodes, char cBreak){
      this.initDecode();
      String pattern = "(" + startCode + "[^\\]]*)";
      Pattern r = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
      for(String str : barcodes){
         
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
    
    protected void decodeBC(String s, char fnc1){
        //System.out.println(" Trying to decode :" + s);

        s = s.replaceFirst("(?i)" + startCode, "");
        //System.out.println(" decode of :" + s);
        StringBuilder ai = new StringBuilder();
        int index = 0;
        while (index < s.length()) {
          ai.append(s.charAt(index++));
          AII info = aiinfo.get(ai.toString());
          if (info != null) {
            StringBuilder value = new StringBuilder();
            for (int i = 0; i < info.maxLength && index < s.length(); i++) {
              char c = s.charAt(index++);
              if (c == fnc1) {
                break;
              }
              value.append(c);
            }
            if (value.length() < info.minLength) {
              throw new IllegalArgumentException("Short field for AI \"" + ai + "\": \"" + value + "\".");
            }
            data.put(ai.toString(), value.toString());
            ai.setLength(0);
          }
        }
        if (ai.length() > 0) {
            unknownAI.add(ai.toString());
        }
      }

    
    private void initDecode(){
      unknownAI = new ArrayList<String>();
      data.clear();
      
    }
    protected String getFristAI(String[] aStr){
        String reStr;
        for (String iaNum : aStr) {
            reStr = getAI(iaNum);
            if(reStr != null)
                return reStr;
        }
        return null;
    }
    protected String getAI(String num){
        AII info = aiinfo.get(num);
        if (info == null)
            throw new IllegalArgumentException("Get AI "+ num + " is not referenced in EAN 128 norme");
        return data.get(num);
    }
    private String recomposeBC(char sep){
        if(data.isEmpty())
            return "";
        String str = startCode;
        for (Map.Entry<String, String> entry : data.entrySet()) {
                    AII info = aiinfo.get(entry.getKey());
                    //System.out.println(" add : "+entry.getKey() + " | "+ entry.getValue() + " | " + info.hasBreak);
                    if(entry.getKey().length() >0 && entry.getValue().length() > 0 ){
                        str += entry.getKey() + entry.getValue() + (info.hasBreak ? sep : "");
                    }	
            }
        return str;
    }
    
    
    public LocalDate transformDateStr(String dateStr){
        if (dateStr == null || dateStr.length() != 6)
            return null;
        int year = Integer.valueOf(dateStr.substring(0,2));
        if(year >=51 ){
            year+=1900;
        }else{
            year+=2000;
        }
        int mm = Integer.valueOf(dateStr.substring(2,4));// base Janvier à 0
        int jj = Integer.valueOf(dateStr.substring(4));
        // borne des valeurs
        mm = UTILS.cstIntbtw(mm, 1, 12);
        jj = UTILS.cstIntbtw(jj, 1, 31);
        LocalDate d =LocalDate.of(year, mm, jj); 
        
        //System.out.println(this.getClass()+" ----> date :"+d);
        return d;//year+"-"+mm+"-"+jj;
    }
    
    
    
    /* la fonction de print */ 
  @Override
    public String toString(){
        String str = "[GS1Code128Data]" + System.lineSeparator();
        for (Map.Entry<String, String> entry : data.entrySet()) {
                AII info = aiinfo.get(entry.getKey());
		str = str + "    - Key : " + entry.getKey() + " - " + info.identifier + " Value : " + entry.getValue() + System.lineSeparator();
	}
        for (int counter = 0; counter < unknownAI.size(); counter++) { 		      
            str += "    - Unknown : " + unknownAI.get(counter) + System.lineSeparator();	
        }   
     
        return str;
    }  
    
}
