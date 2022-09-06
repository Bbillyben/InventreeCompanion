/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.element.renderer.table;

import data.CONSTANT;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author blegendre
 */
public class ActionRenderer extends JPanel implements TableCellRenderer {
    private JLabel lab;
    private Color col;
    public ActionRenderer() {
        //this.setLayout(new BorderLayout());
        this.setLayout(null);
        lab= new JLabel();
        
        this.add(lab, BorderLayout.LINE_END);
    }
     @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
      lab.setText((value == null) ? " " : value.toString());
      switch((String) value){
          case CONSTANT.MODE_ADD:
              col = Color.GREEN;
              break;
          case CONSTANT.MODE_REMOVE:
              col = Color.RED;
              break;
          case CONSTANT.MODE_TRANSFERT:
              col = Color.MAGENTA;
              break;
           case CONSTANT.STATUS_DELETED:
              col = Color.DARK_GRAY;
              break;
          default:
              col = Color.white;
      }
      return this;
     
    }
    
    @Override
    public void setBackground(Color bg){
        if(bg == Color.GRAY){
            col = Color.DARK_GRAY;
        }
        super.setBackground(bg);
        //System.out.println("view.element.renderer.table.ActionRenderer.setBackground() color :"+bg);
    }
     @Override
        public void paintComponent(Graphics g) {
            
            super.paintComponent(g);
            
             Dimension d = new Dimension(this.getWidth(), this.getHeight());
            lab.setSize(d);
            lab.setLocation(d.height, 0);
            g.setColor(col);
            g.fillOval(d.height/4, d.height/4, d.height/2, d.height/2);
            
}

}
