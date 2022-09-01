/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author legen
 */
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import org.ini4j.*;

import java.util.HashMap;
import model.Model;
import org.ini4j.Profile.Section;
        
public class IniHandler {
    /* Static String iniPath
    * chemin du fichier ini
    */
    String iniPath;
    String iniFileName = "ini_java.ini" ;
    String iniFolder = "ext_file";
    Ini iniObj;
    
    private final Model bModel;
    
    public IniHandler( Model bm){
        bModel = bm;
        //this.initialise();
    }
    
    public void initialise(){
               
        File iniFile = getIniFile();
        
        try{
            iniObj = new Ini(iniFile);
            
            
        }catch (IOException e) {
          System.out.println("An error occurred in IniFile .");
          this.save();
          //e.printStackTrace();
        }
        bModel.preferenceLoaded();
    }
    
    public IniStruct getStructure(){
        IniStruct iSt = new IniStruct();
        for (String sectionName: iniObj.keySet()) {
            //System.out.println("["+sectionName+"]");
            Section section = iniObj.get(sectionName);
            for (String optionKey: section.keySet()) {
                //System.out.println("\t"+optionKey+"="+section.get(optionKey));
                iSt.setValue(sectionName, optionKey, section.get(optionKey));
            }
        }
        return iSt;  
    }
    public String getValue(String sectionName, String keyName){
        Ini.Section section = iniObj.get(sectionName);
        if(section == null)
            return null;
        return section.get(keyName);
    }
    public String getValue(String sectionName, String keyName, String defaultValue){
        Ini.Section section = iniObj.get(sectionName);
        if(section == null)
            return defaultValue;  
        
        return section.get(keyName,defaultValue);
    }
    public String getValue(String sectionName, String keyName, String defaultValue, boolean encodeB64){
        String val = this.getValue(sectionName, keyName);
        if(val == null)
            return defaultValue;
                    
        if(encodeB64)
            val = new String(Base64.getDecoder().decode(val));
        return val;
    }
    public String getValue(String sectionName, String keyName, boolean encodeB64){
        String val = this.getValue(sectionName, keyName);
        if(val == null)
            return null;                   
        if(encodeB64)
            val = new String(Base64.getDecoder().decode(val));
        return val;
    }
    
    
    public Boolean setValue(String sectionName, String keyName, String value){
        Ini.Section section = iniObj.get(sectionName);
        if(section == null){
           section =  iniObj.add(sectionName);
            
        }
        section.put(keyName, value);
        return save();
    }
    public void setValue(String sectionName, String keyName, String value, boolean encodeB64) {
        String val = value;
        if(encodeB64){
            val = Base64.getEncoder().encodeToString(val.getBytes());
        }
        this.setValue(sectionName, keyName, val);
    }
    
    public Boolean removeKey(String sectionName, String keyName){
         Ini.Section section = iniObj.get(sectionName);
         if(section == null)
            return true;
         section.remove(keyName);
         return save();
    }
    public Boolean removeSection(String sectionName){
        iniObj.remove(sectionName);
        return save();
    }
    
    /** Process d'un Objet IniStruct
     * 
     * @param iniS
     * @return true/false if all are stored or if there is a niusse
     */
    public Boolean processIni(IniStruct iniS){
        HashMap<String, String> sectMap;
        String sectName;
        Boolean sucessStat = true;
        for (HashMap.Entry<String, HashMap> sectSet : iniS.data.entrySet()) {
            sectMap = sectSet.getValue();
            sectName = sectSet.getKey();
            for(HashMap.Entry<String,String> keySet : sectMap.entrySet()){
                sucessStat = sucessStat && this.setValue(sectName, keySet.getKey(), keySet.getValue());
            }
            
        }
        return sucessStat;
    }
    
    /* save
    * save the ini file 
    */
    private Boolean save(){
        try{
            iniObj.store(getIniFile());
        }catch (IOException e) {
          System.out.println("An error occurred in IniFile .");
          e.printStackTrace();
          return false;
        }
        return true;
    }
    
    
    /* getIniFile : 
    * return a file for ini key value pair storage
    */
    private File getIniFile(){
        String currDir = this.getIniFolder();
        File directory = new File(currDir);
        if (! directory.exists()){
            directory.mkdir(); 
        }
        
        File iniFile = new File(currDir + System.getProperty("file.separator") + iniFileName);
        try{
            if (iniFile.createNewFile()) {
              System.out.println("File created: " + iniFile.getName());
            }
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
          return null;
        }
        return iniFile;
    }
    public String getIniFolder(){
        String currDir = System.getProperty("user.dir");
        currDir = currDir.concat(System.getProperty("file.separator")).concat(iniFolder);
        return currDir;
    }

    
    
    
}
