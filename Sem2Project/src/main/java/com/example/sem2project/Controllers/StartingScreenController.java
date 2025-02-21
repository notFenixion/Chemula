package com.example.sem2project.Controllers;

import com.example.sem2project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartingScreenController {
    @FXML
    private Button aboutButton;
    @FXML
    private Button newCompoundButton;
    @FXML
    private Button loadCompoundButton;

    @FXML
    //Load SelectionScreen stage
    public void loadSelectionScreen(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) newCompoundButton.getScene().getWindow();
        currentStage.close();
        Stage mainStage = new Stage();
        //String startingScreenName =  "/startingscreen.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource( "View/displayedmainscreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Create Your Compound!");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
    @FXML
    public void loadCompound(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Sorry, this button doesn't work at the current moment. Please use the Load button inside the Compound Maker!");
        alert.setHeaderText("Oops!");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();
    }
    @FXML
    //Load AboutMe stage
    public void loadAboutMeScreen(ActionEvent event) throws IOException{
        Stage mainStage = new Stage();
        //String startingScreenName =  "/startingscreen.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource( "View/aboutmescreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("About the Creator!");
        mainStage.setScene(scene);
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.show();
    }
}
