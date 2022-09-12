/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view;

import com.opencsv.CSVWriter;
import controller.ExportController;
import data.CONSTANT;
import events.ExportEvent;
import events.iEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import listeners.ExportListener;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author blegendre
 */
public class ExportView implements iView, ExportListener {

     ExportController controller;
    private final JFrame mainFrame;
    
    public ExportView(ExportController ctrl, JFrame f){
        controller = ctrl;
        mainFrame = f;
    }
    
    private void launchSave(String type, ArrayList<String[]> datas){
        System.out.println("view.ExportView.launchSave()");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save"); 
        
        FileNameExtensionFilter xmlFilter = null;
        String extension="";
        switch(type){
            case CONSTANT.ACTION_EXPORT_EXCEL:
                xmlFilter = new FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx");
                extension="xlsx";
                break;
            case CONSTANT.ACTION_EXPORT_CSV:
                xmlFilter = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
                extension="csv";
                break;
        }

        fileChooser.addChoosableFileFilter(xmlFilter);
        fileChooser.setFileFilter(xmlFilter);

        
        int saveChooser = fileChooser.showSaveDialog(mainFrame);
        
        if (saveChooser != JFileChooser.APPROVE_OPTION) 
            return;
        
        File fileToSave = fileChooser.getSelectedFile();
        // check if csv and force to csv if not
        if (!FilenameUtils.getExtension(fileToSave.getName()).equalsIgnoreCase(extension)) {
            fileToSave = new File(fileToSave.getParentFile(), FilenameUtils.getBaseName(fileToSave.getName())+"."+extension);
        }
        System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        
        switch(type){
            case CONSTANT.ACTION_EXPORT_EXCEL:
                exportEXCEL(fileToSave, datas);
                break;
            case CONSTANT.ACTION_EXPORT_CSV:
                exportCSV(fileToSave, datas);
                break;
        }
        
    }
    
    private void exportCSV(File fileToSave, ArrayList<String[]> datas) {
        CSVWriter writer;
    
        try{
            writer = new CSVWriter(new FileWriter(fileToSave.getAbsolutePath()));
            writer.writeAll(datas);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
             //Logger.getLogger(ExportView.class.getName()).log(Level.SEVERE, null, ex);
             controller.exportFeedback(CONSTANT.STATUS_ERROR, fileToSave.getAbsolutePath(), null);
         }
        controller.exportFeedback(CONSTANT.STATUS_OK, fileToSave.getAbsolutePath(), null);
        
    
    }
    private void exportEXCEL(File fileToSave, ArrayList<String[]> datas) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Export");
        Row tmpRow;
        Cell cell;
        for(int row = 0; row < datas.size(); row++ ){
            String[] strL = datas.get(row);
            tmpRow = sheet.createRow(row);
            for(int col = 0; col < strL.length; col++){
                cell = tmpRow.createCell(col);
                cell.setCellValue(strL[col]);
            }
        }
        FileOutputStream outputStream;
         try {
            outputStream = new FileOutputStream(fileToSave.getAbsolutePath());
            workbook.write(outputStream);
            workbook.close();
         } catch (IOException ex) {
             //Logger.getLogger(ExportView.class.getName()).log(Level.SEVERE, null, ex);
             controller.exportFeedback(CONSTANT.STATUS_ERROR, fileToSave.getAbsolutePath(), null);
         } 
        controller.exportFeedback(CONSTANT.STATUS_OK, fileToSave.getAbsolutePath(), null);
    }
    
    
    
    @Override
    public void eventRecept(iEvent e) {
         switch (e.type) {
            case ExportEvent.EXPORT_DATA:
                launchSave(((ExportEvent) e).export_type, ((ExportEvent) e).export_data );
                break;
          
        }
    }
    @Override
    public void setVisible(boolean state) {}

    

    
    
}
