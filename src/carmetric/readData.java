/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmetric;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
 
    public readData(File inputFile){
        this.inputFile = inputFile;
   }
  /**
   * Read data with opc package
   */
    public void readFile(int startNumber, int endNumber){
        try{
        Workbook book = WorkbookFactory.create(inputFile);
        Sheet worksheet = book.getSheetAt(0);
        
        }catch(IOException | InvalidFormatException ex){
            ex.printStackTrace();
        }
    }
    
}
