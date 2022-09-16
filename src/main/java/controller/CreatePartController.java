/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Inventree.item.StockItem;
import listeners.ParamListener;
import listeners.SendListener;
import model.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import view.iView;

/**
 *
 * @author legen
 */
public class CreatePartController extends iController{

    public CreatePartController(Model md){
        super(md);
    }
     public void linkPart(StockItem si, 
        int subPartId, 
        int supplierId,
        String sku, 
        int manuId, 
        String mpn,
        Boolean assignToPart,
        Boolean assigToSupplier){
         
         // creation du JSON array avec
         // en 0 le Supplier
         // en 1 le manufacturer 
         // null si pas 
         JSONArray jsa = new JSONArray();
         JSONObject jso=null;
         if(supplierId !=0){
             jso = new JSONObject();
             jso.put("part", subPartId);
                jso.put("supplier", supplierId);
             jso.put("SKU", sku);
         }
         jsa.put(jso);
         jso = null;
         if(manuId !=0){
             jso = new JSONObject();
             jso.put("part", subPartId);
             jso.put("manufacturer", manuId);
             jso.put("MPN", sku);
         }
         jsa.put(jso);
//         System.out.println("controller.CreatePartController.linkPart()");
//         System.out.println(jsa);
//         System.out.println(" jsa[0] :"+jsa.isNull(0));  
//         System.out.println(" jsa[1] :"+jsa.isNull(1));

        if(assignToPart)
            si.partitem.setId(subPartId);
         model.linkPart(si, jsa, assignToPart, assigToSupplier);
         
     }
     
    public void createPart(StockItem si,
        int catId, 
        String name,
        String IPN,
        String desc, 
        int subPartId, 
        int locId,
        int minNum, 
        boolean isTemplate, 
        int supplierId,
        String sku, 
        int manuId, 
        String mpn,
        Boolean assignToPart,
        Boolean assigToSupplier){
        
        JSONObject jso = new JSONObject();
        jso.put("category", catId);
        jso.put("name", name);
        if(IPN != null)
            jso.put("IPN", IPN);
        jso.put("description", desc);
        if(subPartId != 0)
            jso.put("variant_of", subPartId);
        jso.put("default_location", locId);
        jso.put("minimum_stock", minNum);
        jso.put("trackable", true);
        jso.put("is_template", isTemplate);
        jso.put("add_supplier_info", true);
        if(supplierId != 0){
            jso.put("supplier", supplierId);
            jso.put("SKU", sku);
        }
        if(manuId != 0){
            jso.put("manufacturer", manuId);
            jso.put("MPN", mpn);
        }
        
        jso.put("copy_category_parameters", true );
        model.createPart(si, jso, assignToPart, assigToSupplier);
        
        
    }
    
    @Override
    public void setView(iView v){
        super.setView(v);
        model.addEventListener(view, SendListener.class);
        model.addEventListener(view, ParamListener.class);

    }
    
}
