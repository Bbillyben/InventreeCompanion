/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 * Class creating a strucutre similar to ini file
 * will be used to send parameters to IniHandler.
 * @author blegendre
 */
import java.util.Base64;
import java.util.HashMap;


public class IniStruct {
    public final HashMap<String, HashMap> data = new HashMap<>(); 
    
    public void setValue(String sectionName, String keyName, String value){
        
        if(! data.containsKey(sectionName)){
            data.put(sectionName, new HashMap<String, String>());
        }
        HashMap<String, String> mps = (HashMap<String, String>) data.get(sectionName);
        
        if(!mps.containsKey(keyName)){
            mps.put(keyName, value);
        }else{
            mps.replace(keyName, value);
        }
    }
    public void setValue(String sectionName, String keyName, String value, boolean encodeB64){
        String val = value;
        if(encodeB64){
            val = Base64.getEncoder().encodeToString(val.getBytes());
        }
        this.setValue(sectionName, keyName, val);
    }
    public String getValue(String sectionName, String keyName){
        if(! data.containsKey(sectionName)){
            return null;
        }
        HashMap<String, String> mps = (HashMap<String, String>) data.get(sectionName);
        
        if(!mps.containsKey(keyName)){
             return null;
        }
        return mps.get(keyName);
    }
    
    public String getValue(String sectionName, String keyName, String defaultValue){
        //System.out.println("data.IniStruct.getValue() "+sectionName+"/"+keyName+"/"+defaultValue);
        if(! data.containsKey(sectionName)){
            return defaultValue;
        }
        HashMap<String, String> mps = (HashMap<String, String>) data.get(sectionName);
        
        if(!mps.containsKey(keyName)){
             return defaultValue;
        }
        return mps.get(keyName);
    }
    public String getValue(String sectionName, String keyName, boolean encodeB64){
        String val = this.getValue(sectionName, keyName);
        if(val == null)
            return null;                   
        if(encodeB64)
            val = new String(Base64.getDecoder().decode(val));
        return val;
    }
    public String getValue(String sectionName, String keyName, String defaultValue, boolean encodeB64){
        String val = this.getValue(sectionName, keyName);
        if(val == null)
            return defaultValue;
                    
        if(encodeB64)
            val = new String(Base64.getDecoder().decode(val));
        return val;
    }
    
    
    @Override
    public String toString(){
        HashMap<String, String> sectMap;
        String sectName;
        String str = "";
         for (HashMap.Entry<String, HashMap> sectSet : data.entrySet()) {
            sectMap = sectSet.getValue();
            sectName = sectSet.getKey();
            str +="["+sectName+"]"+System.getProperty("line.separator");
            for(HashMap.Entry<String,String> keySet : sectMap.entrySet()){
                str +="  - "+ keySet.getKey()+ " : "+ keySet.getValue() +System.getProperty("line.separator");
            }
            
        }
         return str;
    }
    
}
