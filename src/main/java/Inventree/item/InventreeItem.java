/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventree.item;

import java.io.Serializable;

/**
 *
 * @author legen
 */
public class InventreeItem implements Serializable {
    protected int id;
    protected String name;
    public InventreeItem(){
        id = 0;
        name = null;
    }
    public InventreeItem(String nameS){
        id = 0;
        name = nameS;
    }
    public InventreeItem(int idS){
        id = idS;
        name = null;
    }
    public InventreeItem(String nameS, int idS){
        id = idS;
        name = nameS;
    }
    
    public int getId(){
        return id;
    }
    public void setId(int i){
         id = i;
    }
    public String getName(){
        return name;
    }
    
    public void setName(String n){
        name = n;
    }
    public String getDisplayName(){
        return name;
        
    }
}
