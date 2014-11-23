/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;

import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Gian
 */
public class newgui extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Titeltext");
        
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL, 4, 4);
        
        ColorPicker colorPicker = new ColorPicker();
        DatePicker datePicker = new DatePicker();
        ProgressBar progressBar = new ProgressBar();
        
        flowPane.getChildren().add(colorPicker);
        flowPane.getChildren().add(datePicker);
        flowPane.getChildren().add(progressBar);
        
        for (int i = 0; i < 5; i++) {
            Button button = new Button("Weiter");
            button.setDisable(true);
            flowPane.getChildren().add(button);
        }
        
        // Inhalt des Fensters
        Label label = new Label("Das ist ein Text.");
        Scene scene = new Scene(flowPane);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
