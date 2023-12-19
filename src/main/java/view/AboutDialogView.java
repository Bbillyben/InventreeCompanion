package view;


import events.iEvent;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.element.CenterDialog;
import view.element.JHyperlink;

/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */

/**
 *
 * @author blegendre
 */
public class AboutDialogView implements iView {
    protected JFrame mainFrame;
    protected CenterDialog dialog;
    
    public AboutDialogView(JFrame frame){
        mainFrame = frame;
        buildView();
    }
    private void buildView(){
        
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("project.properties"));
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                System.out.println(key + ": " + value);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
        dialog = new CenterDialog(mainFrame, "About", true);
        JPanel jp = new JPanel();
        
        // for inventree verions
        JPanel infos = new JPanel();
        
        JLabel ivVersion = new JLabel("Inventree Version : " +  properties.getProperty("version"));
        ivVersion.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        jp.add(ivVersion);
        //jp.add(new JLabel("author : " +  properties.getProperty("author"))); 
        
        //for Git and release
        JPanel liens = new JPanel();
        JHyperlink git= new JHyperlink("Github", "https://github.com/Bbillyben/InventreeCompanion/");
        JHyperlink release= new JHyperlink("Releases", "https://github.com/Bbillyben/InventreeCompanion/releases");
        JHyperlink issue= new JHyperlink("Issue", "https://github.com/Bbillyben/InventreeCompanion/issues");
        
        liens.add(git);
        liens.add(new JLabel(" - "));
        liens.add(release);
        liens.add(new JLabel(" - "));
        liens.add(issue);
        
        liens.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//        jp.add(infos, "General Information");
        jp.add(liens, "links");
        jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));
        jp.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "About Companion"));
        
        dialog.getContentPane().add(jp, BorderLayout.CENTER);
        dialog.pack();
        dialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    @Override
    public void setVisible(boolean state) {
        //centerDialog(dialog,mainFrame);
        dialog.setVisible(state);
    }

    @Override
    public void eventRecept(iEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
