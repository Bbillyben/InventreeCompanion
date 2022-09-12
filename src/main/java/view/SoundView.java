/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view;

import Sound.SoundUtils;
import controller.SoundController;
import data.CONSTANT;
import data.IniStruct;
import events.InfoEvent;
import events.IniEvent;
import events.iEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import listeners.BarcodeListener;
import listeners.InfoListener;
import listeners.ListenerI;

/**
 *
 * @author blegendre
 */
public class SoundView implements iView, BarcodeListener, ListenerI, InfoListener {

    private final SoundController controller;
    private boolean enabled;
    
     public SoundView(SoundController ctrl){
        controller = ctrl;
        enabled=false;
        //buildView();
    }
     
    @Override
    public void setVisible(boolean state) {}

    private void toneSound(int hz, int ms){
        if(!enabled)
            return;
        try {
            SoundUtils.tone(hz, ms);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(SoundView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void eventRecept(iEvent e) {
        switch(e.type){
             case IniEvent.INI_LOADED, IniEvent.INI_CHANGED:
                updateFromIni(((IniEvent) e).ini);
                break;
            case InfoEvent.NEW_SCAN://BarcodeEvent.NEW_BARCODE:
                toneSound(784,200);
                break;
            
        }
        
    }

    private void updateFromIni(IniStruct ini) {
            String isEnabled = ini.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_PLAY_SOUND, "false");
            
            enabled = Boolean.valueOf(isEnabled);
    }
    
}
