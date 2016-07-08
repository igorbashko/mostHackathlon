/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmetric;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author igorbahko 
 * The data for car speed history
 */
public class tableData {

    private final SimpleStringProperty time;
    private final SimpleStringProperty speed;
    
    public tableData(String time, String speed){
    this.time = new SimpleStringProperty(time);
    this.speed = new SimpleStringProperty(speed);
    }
    
    public void setTime(String value){
        this.time.set(value);
    }
    public String getTime(){
        return this.time.get();
    }
    public void setSpeed(String value){
        this.speed.set(value);
    }
    public String getSpeed(){
        return this.speed.get();
    }
}
