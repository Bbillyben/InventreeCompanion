/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.LayersItem;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.Timer;
import javax.swing.plaf.LayerUI;

/**
 *
 * @author blegendre
 */
public class BlinkingLayerUI extends LayerUI<JComponent> implements ActionListener{
    private static final float STROKE_START=(float)70;
    private static final float ALPHA_START=(float)1.0;
    private static final float ALPHA_DELTA=(float)0.1;
    
    private boolean isBlinking=false;
    float alpha=(float)0;
    private Timer mTimer;
    private Graphics2D g2;
    private Color color;
    
    public void startBlink(Color col){
        //Toolkit.getDefaultToolkit().beep();
        color = col;
        if(isBlinking)
            return;
        alpha=ALPHA_START;
        isBlinking = true;
        int fps = 24;
        int tick = 1000 / fps;
        mTimer = new Timer(tick, this);
        mTimer.addActionListener(this);
        mTimer.start();
    }
  
    @Override
    public void paint(Graphics g, JComponent c) {
        int w = c.getWidth();
        int h = c.getHeight();
        super.paint(g, c);
        if(isBlinking){
          g2 = (Graphics2D) g.create();


          g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)alpha));
          g2.setColor(color);
          Stroke stroke1 = new BasicStroke(STROKE_START*(float)alpha);
          g2.setStroke(stroke1);
          g2.drawRect(0, 0, w, h);

          g2.dispose();

        }
      
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isBlinking) {
            firePropertyChange("tick", 0, 1);
            alpha -= ALPHA_DELTA;
            if (alpha < 0.001) {
              isBlinking = false;
              alpha = (float)0.0;
              mTimer.stop();
            }
            
        }
    }
    @Override
  public void applyPropertyChange(PropertyChangeEvent pce, JLayer l) {
    if ("tick".equals(pce.getPropertyName())) {
      l.repaint();
    }
  }
}
