/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package listeners;

import events.iEvent;
import java.util.EventListener;

/**
 *
 * @author blegendre
 */
public interface ListenerI extends EventListener{
    void eventRecept(iEvent e);
}
