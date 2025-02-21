package com.example.sem2project.Controllers;

import com.example.sem2project.Algorithm;
import com.example.sem2project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OutputScreenController implements Initializable {

    @FXML
    private TextArea nameTextArea;
    @FXML
    private Button backButton;


    @FXML
    public void setNameTextArea() {
        String compoundName = Algorithm.generateName();
        nameTextArea.setEditable(true);
        nameTextArea.setText(compoundName);
        nameTextArea.setEditable(false);
    }


    @FXML
    public void closeStage(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNameTextArea();
    }



}
