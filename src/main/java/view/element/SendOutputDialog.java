/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view.element;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author blegendre
 */
public class SendOutputDialog extends JDialog {
    
    private boolean allow_closing;
    JTextPane ta;
    JScrollPane uJSP;
    DefaultCaret caret;
    private String strShow="";
    int curr=0;
    public SendOutputDialog(Frame owner, String titre, boolean modal){
        super(owner, titre, modal);
        buildView();
    }
    private void buildView(){
        ta = new JTextPane();
        ta.setMargin(new Insets(5, 5, 5, 5));
        ta.setContentType("text/html");
        ta.setEditable(false);

        //prevent for scrolling down when prepend
        caret = (DefaultCaret) ta.getCaret();
        //caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        uJSP = new JScrollPane(ta);

        JPanel cont = new JPanel(new BorderLayout());
        cont.add(uJSP);
        cont.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Sending Information"));
        this.add(cont);
        
        this.setSize(600, 250);
        //this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    public void appendLine(String str){
        strShow = strShow+"<br/>"+str;
        
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        ta.setText("<html>"+strShow+"</html>");
    }
    public void prependLine(String str){
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        strShow = str+"<br/>"+strShow;
        ta.setText("<html>"+strShow+"</html>");
    }
    
    public void init(){
        ta.setText("");
        strShow = "";
    }
    public void allowClosing(boolean allow){
        allow_closing = allow;
    }
    
    @Override
    public void dispose(){
        System.out.println("view.element.SendOutputDialog.dispose()"+allow_closing);
        if(!allow_closing){
            int result = JOptionPane.showConfirmDialog(this,"Closing this windows will stop sending items to server, Are you sure ?", "Stop Sending",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE);
            
            if(result == 0)
                super.dispose();
        }else{
            super.dispose();
        }
    }
}
