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
import javafx.geometry.Insets;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;

/**
 *
 * @author igorbashka
 */
public class CarMetric extends Application {
    private Label accValue;
    private Label dangerk;
    private TableView table;
    private ObservableList<tableData> tableD;
    private readCalculate runner = new readCalculate("report.xls");
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Подсичтать риск");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Телематика");
                runner.readFile();
                runner.calculateAcc();
                fillTable();
                setAccValue(Double.toString(runner.getdangerRating()));
                setDangerK(Double.toString(runner.getDanger()));
            }
        });
        
        VBox root = new VBox();
        root.alignmentProperty().setValue(Pos.TOP_CENTER);
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setSpacing(15);
        Label dangerLabel = new Label("Риск");
        dangerLabel.setFont(new Font("Arial", 35));
        this.dangerk = new Label();
        this.dangerk.setFont(new Font("Arial", 35));
        this.dangerk.setTextFill(Color.BLUE);
        root.getChildren().addAll(Heading(), Table(),btn, addGraphics(), dangerLabel,
                this.dangerk);
        
        Scene scene = new Scene(root, 700, 1250);
        
        primaryStage.setTitle("Телематика");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * Adds other view items to Vbox
     */
    private HBox addGraphics(){
        HBox accValueC = new HBox();//container which will be held acceleration value label and value
        accValueC.alignmentProperty().setValue(Pos.CENTER);
        Label accLabelN = new Label("Ускорение ");
        accLabelN.setFont(new Font("Arial", 35));
        accValue = new Label(); //acceleration value itself
        accValue.setFont(new Font("Arial", 35));
        accValue.setTextFill(Color.RED);
        accValueC.getChildren().addAll(accLabelN, accValue);
        return accValueC;
    }
 
    private void setAccValue(String value){
        this.accValue.setText(value + " м/с^2");
    }
    /**
     * Sets k of danger
     */
    private void setDangerK(String value){
        this.dangerk.setText(value);
    }
    /**
     * Creating table with data
     */
    private TableView Table(){
         this.table = new TableView();
        TableColumn time = new TableColumn("Время");
        time.setMinWidth(300);
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
    private Label Heading(){
        Label heading = new Label("Машина с гос № *** EN");
        heading.setFont(new Font("Arial", 26));
        
        return heading;
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
