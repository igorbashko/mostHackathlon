/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmetric;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Read data from excel file about car speed and time information 
 * @author igorbashka
 */
public class readData {
    private File inputFile;
    private ArrayList speedCell;
 
    public readData(String filePath){
        
        this.inputFile = new File(filePath);
   }
  /**
   * Read data with opc package
   */
    public void readFile(){
        this.speedCell = new ArrayList();
        try{
        Workbook book = WorkbookFactory.create(inputFile);
        Sheet worksheet = book.getSheetAt(0);
        CellReference ref = new CellReference("K");
        for(int n=11; n<23; n++){
            Row r = worksheet.getRow(n);
            Cell c = r.getCell(ref.getCol());
            speedCell.add(c.getNumericCellValue());
        }
        }catch(IOException | InvalidFormatException ex){
            ex.printStackTrace();
        }
    }
    
}
