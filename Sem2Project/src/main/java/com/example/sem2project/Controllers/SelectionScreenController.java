package com.example.sem2project.Controllers;

import com.example.sem2project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectionScreenController{

    @FXML
    private Button displayedButton;
    @FXML
    private Button condensedButton;
    @FXML
    private Button skeletalButton;
    @FXML
    private Button returnButton;

    @FXML
    public void loadDisplayedMainScreen(ActionEvent event) throws IOException{

        Stage currentStage = (Stage) displayedButton.getScene().getWindow();
        currentStage.close();
        Stage mainStage = new Stage();
        //String startingScreenName =  "/startingscreen.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource( "displayedmainscreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        mainStage.setTitle("Create Your Compound!");
        mainStage.setScene(scene);
        mainStage.setResizable(false);


        mainStage.show();


    }

    @FXML
    public void loadCondensedMainScreen(ActionEvent event) {

    }

    @FXML
    public void loadSkeletalMainScreen(ActionEvent event) {

    }

    @FXML
    public void loadStartingScreen(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) returnButton.getScene().getWindow();
        currentStage.close();
        Stage mainStage = new Stage();
        //String startingScreenName =  "/startingscreen.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource( "View/startingscreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Choose the type of input:");
        mainStage.setScene(scene);
        mainStage.show();
    }

}
