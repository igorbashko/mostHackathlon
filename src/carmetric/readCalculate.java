/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmetric;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.lang.Integer;
import java.util.Calendar;
import java.util.List;
/**
 * Read data from excel file about car speed and time information 
 * @author igorbashka
 */
public class readCalculate {
    private File inputFile;
    private List<Integer> speedCell;
    private List<Double> accelerationCells;
    private List<Double> dangerKCells;
    private List<Date> timeCells;
    private Map<Integer, Boolean> dayNight = new HashMap<Integer, Boolean>();
    private Double acc; //final acceleration of the car
    private Double dangerK; // koefficient of dangerous of driving style
 
    public readCalculate(String filePath){
        
        this.inputFile = new File(filePath);
   }
  /**
   * Read data with opc package
   */
    public void readFile(){
        this.speedCell = new ArrayList();
        this.timeCells = new ArrayList();
        try{
        Workbook book = WorkbookFactory.create(inputFile);
        Sheet worksheet = book.getSheetAt(0);
        CellReference speed = new CellReference("D");
        CellReference time = new CellReference("B");
        for(int n=11; n<60; n++){
            Row r = worksheet.getRow(n);
            Cell speedC = r.getCell(speed.getCol());
            speedCell.add((int) speedC.getNumericCellValue());
            Cell timeC = r.getCell(time.getCol());
            timeCells.add(timeDefinder(timeC.getStringCellValue()));
        }
        }catch(IOException | InvalidFormatException | ParseException ex){
            ex.printStackTrace();
        }
       }
   /**
     * Converts value from string to time format
     */
    private Date timeDefinder(String value) throws ParseException{
           DateFormat formatter = new SimpleDateFormat  ("HH:mm:ss");
           Date drivingTime = new Date(formatter.parse(value).getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(drivingTime);
        c.set(Calendar.YEAR, 2016);
        c.set(Calendar.MONTH, 5);
        drivingTime = c.getTime();
           return drivingTime;
        }
    /**
     * Define night or day time
     */
    private void dayOrNight(Date timeToCompare, int index){
        Calendar cUpper = Calendar.getInstance();
        Calendar cLower = Calendar.getInstance();
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
        try{
        Date upperLimit = parser.parse("8:00:00");
        Date lowerLimit = parser.parse("22:00:00");
        cUpper.setTime(upperLimit);
        cLower.setTime(lowerLimit);
        cUpper.set(2016, 05, 01); cLower.set(2016, 05, 01);
        upperLimit = cUpper.getTime();
        lowerLimit = cLower.getTime();
        if(timeToCompare.after(upperLimit) && timeToCompare.before(lowerLimit))
          this.dayNight.put(index, true);
        else
            this.dayNight.put(index, false);
        }catch(ParseException ex){
            ex.printStackTrace();
        }
    }
    /**
     * Calculate acceleration 
     */
   public void calculateAcc(){
       int index =0; //index of timeCells list
       this.acc = 0.0;
       this.dangerK = 0.0;
       for(Date time:timeCells){
           dayOrNight(time, index);
       index++;    
       }
       this.accelerationCells = new ArrayList();
       this.dangerKCells = new ArrayList();
      double k; //coefficient of danger if driver drives during a night it will be higher
        for(int i=0; i<this.speedCell.size()-1; i++){
           if(this.dayNight.get(i) && this.dayNight.get(i+1))
               k = 0.5;
               else
               k=0.3;
            double acceleration1 = (this.speedCell.get(i) - this.speedCell.get(i+1))/3.6;
             double td =((this.timeCells.get(i+1).getTime()-this.timeCells.get(i).getTime())/1000);
            double dangerC = acceleration1*k;
           
            this.accelerationCells.add(Math.abs(acceleration1/td));
            this.dangerKCells.add(Math.abs(dangerC));
          }
        for(Double acc:this.accelerationCells){
            this.acc+=acc;
        }
        this.acc = 
                Math.round((this.acc/this.accelerationCells.size()*1000/3600)*100d)/100d;
        for (Double dk:this.dangerKCells){
            this.dangerK +=dk;
        }
        this.dangerK = 
                Math.round((this.dangerK/this.dangerKCells.size()*1000/3600)*this.acc*100*100d)/100d;
   }
   
   public double getdangerRating(){
     return this.acc;  
   }
   public double getDanger(){
       return this.dangerK;
   }
 public List<Integer> getSpeed(){
    return this.speedCell; 
    }  

    public List<Date> getTime() {
       return this.timeCells;
        
    }
 }
