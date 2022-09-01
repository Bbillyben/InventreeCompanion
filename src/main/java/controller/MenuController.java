/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import data.CONSTANT;
import events.ConnectionEvent;
import events.NavEvent;
import events.iEvent;
import listeners.NavigationListener;
import model.Model;
import view.MenuView;

/**
 *
 * @author legen
 */
public class MenuController extends iController implements NavigationListener  {
    
    public MenuController(Model md){
        super(md);
        md.addEventListener(this, NavigationListener.class);
    }
    public void setView(MenuView vi){
        super.setView(vi);
    }
    public void setPage(String pageName){
        //System.out.println("Menu Cont, page req :"+pageName);
        model.navigate(pageName);
    }

    
    
    @Override
    public void eventRecept(iEvent e) {
        switch(e.type){
            case ConnectionEvent.CONNECTION_ERROR:
                break;
            case ConnectionEvent.CONNECTION_SUCCESS:
                break;
            case NavEvent.NAVIGATE :
                String page = ((NavEvent) e).destPage;
                this.display(!page.equals(CONSTANT.PAGE_LOGIN) && !page.equals(CONSTANT.PAGE_NULL) && !page.equals(CONSTANT.ACTION_LOGOUT) );
                break;
        }
    }

}
