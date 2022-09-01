/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventree;

import Inventree.item.StockItem;
import data.CONSTANT;
import model.Model;

/**
 *
 * @author legen
 */
public class InfoWorker extends Thread {
    private final Model model;
    private final APIConnector conn;
    
    private StockItem si;
    private String forceStockLoc;
    public InfoWorker(Model mod, APIConnector bConn){
        model = mod;
        conn = bConn;
    }
    public InfoWorker(Model mod, APIConnector bConn, StockItem sii, String forceStockLocA){
        model = mod;
        conn = bConn;
        si = sii;
        forceStockLoc=forceStockLocA;
    }
    public void setItem(StockItem sii, String forceStockLocA){
        si = sii;
        forceStockLoc=forceStockLocA;
    }
    
    @Override
    public void run() {
        si.setStatus(CONSTANT.STATUS_ON_UPDATE);
        conn.updateStockItemData(si, forceStockLoc);
    }
    
}
