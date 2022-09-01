/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.element;

import Inventree.item.InventreeItem;
import view.element.CBFiltering.ComboBoxFilterDecorator;
import view.element.CBFiltering.FilterEditor;
import view.element.CBFiltering.InventreeItemCBFilterRenderer;

/**
 *
 * @author legen
 */
public class InventreeItemFilterCB extends InventreeItemCB {
    ComboBoxFilterDecorator<InventreeItem> decorate;
    public InventreeItemFilterCB(){
        decorate = ComboBoxFilterDecorator.decorate(this,
        InventreeItemCBFilterRenderer::getItemDisplayText,
        InventreeItemFilterCB::ivItemFilter);
        
        this.setRenderer(new InventreeItemCBFilterRenderer(decorate.getFilterTextSupplier()));
    }
    public void init(){
        decorate.updateElements();
    }
    
    /*@Override
    public void setSelectedItem(Object anObject){
    System.out.println("InventreeItemFilterCB.setSelectedItem() :"+anObject);
    super.setSelectedItem(anObject);
    
    System.out.println("Printing stack trace:");
    StackTraceElement[] elements = Thread.currentThread().getStackTrace();
    for (int i = 1; i < elements.length; i++) {
    StackTraceElement s = elements[i];
    System.out.println("\tat " + s.getClassName() + "." + s.getMethodName() + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
    }
    }*/
    @Override
    protected void fireActionEvent(){
        if(((FilterEditor) this.getEditor()).isEditing())
            return;
        super.fireActionEvent();
    }
    
    
    /// fonction de filtering
    public static boolean ivItemFilter(InventreeItem ivI, String textToFilter) {
        if (textToFilter.isEmpty()) {
            return true;
        }
        
        return ivI.getName().toLowerCase().contains(textToFilter.toLowerCase());
    }
}
