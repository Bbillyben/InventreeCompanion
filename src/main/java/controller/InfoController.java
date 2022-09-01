/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import listeners.ConnectionListener;
import listeners.InfoListener;
import model.Model;
import view.StatusView;
import view.iView;

/**
 *
 * @author blegendre
 */
public class InfoController extends iController {

    
     public InfoController(Model md){
        super(md);

    }
     @Override
    public void setView(iView vi){
        super.setView(vi);
        model.addEventListener(view, InfoListener.class);
        model.addEventListener(view, ConnectionListener.class);
    }
}
