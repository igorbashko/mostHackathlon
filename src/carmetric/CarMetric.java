/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmetric;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author igorbashka
 */
public class CarMetric extends Application {
    private Label accValue;
    private TableView table;
    private ObservableList<tableData> tableD;
    private readCalculate runner = new readCalculate("report.xls");
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                runner.readFile();
                runner.calculateAcc();
                fillTable();
                setAccValue(Double.toString(runner.getdangerRating()));
            }
        });
        
        VBox root = new VBox();
        root.alignmentProperty().setValue(Pos.CENTER);
        root.getChildren().addAll(Table(),btn, addGraphics());
        
        Scene scene = new Scene(root, 700, 1250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * Adds other view items to Vbox
     */
    private HBox addGraphics(){
        HBox accValueC = new HBox();//container which will be held acceleration value label and value
        accValueC.alignmentProperty().setValue(Pos.CENTER);
        Label accLabelN = new Label("Среднее ускорение");
        accValue = new Label(); //acceleration value itself
        accValueC.getChildren().addAll(accLabelN, accValue);
        return accValueC;
    }
 
    private void setAccValue(String value){
        this.accValue.setText(value);
    }
    /**
     * Creating table with data
     */
    private TableView Table(){
         this.table = new TableView();
        TableColumn time = new TableColumn("Время");
        time.setMinWidth(100);
        time.setCellValueFactory(
        new PropertyValueFactory<>("time"));
        TableColumn speed = new TableColumn("Скорость");
        speed.setMinWidth(100);
        speed.setCellValueFactory(new PropertyValueFactory<>("speed"));
        table.getColumns().addAll(time, speed);
        return table;
    }
    /**
     * Fill table with the data
     */
    private void fillTable(){
        List <Integer> speedData = new ArrayList(runner.getSpeed());
        List <Date> timeData = new ArrayList(runner.getTime());
        int size = speedData.size();
        this.tableD = FXCollections.observableArrayList();
        for(int i=0; i<size; i++){
            tableData tableValue = new tableData(timeData.get(i).toString(),speedData.get(i).toString());
            
            this.tableD.add(tableValue);
        }
        this.table.setItems(tableD);
    }
       /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
