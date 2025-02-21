package com.example.sem2project.Controllers;

import com.example.sem2project.Model.Atom;
import com.example.sem2project.Model.BondLine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileWriter;

public class FileNameController {
    @FXML
    TextField fileNameField;
    @FXML
    Button submitButton;
    @FXML
    Label errorLabel;
    @FXML
    Button criteriaButton;

    @FXML
    public void createFile(ActionEvent event) {
        //If name is valid create/overwrite the File and close this stage, else change errorLabel & clear textField
        //File contents format - stores the info of each atom
        //For each line: "atomID, atomType, xPos, yPos, (connectedAtomID, bondOrder) pairs for all connected atoms to this particular atom

        String regex = "[A-Za-z][\\w-]{1,18}[a-zA-Z0-9]";
        if (fileNameField.getText().matches(regex)) {
            try {
                //Create or overwrite the file with this name
                FileWriter output = new FileWriter(String.format("%s.chem", fileNameField.getText()), false);
                //For each atom i except 1st atom:
                boolean firstAtomVisited = false;
                for (Atom i : DisplayedScreenController.atoms) {
                    if (firstAtomVisited) {
                        //add "Atom", atomType, xPos, yPos
                        String outputString = "Atom," +i.getAtomType()+ "," +i.getxPos()+ "," +i.getyPos();
                        //Write line to file
                        output.write(outputString + "\n");
                    }
                    else {
                        firstAtomVisited = true;
                    }


                }
                for (BondLine i : DisplayedScreenController.bondLines) {
                    String outputString = "BondLine," +i.getStartHitboxID()+ "," +i.getEndHitboxID()+ "," + i.getBondOrder();
                    output.write(outputString + "\n");
                }
                output.close();

            } catch (Exception e) {System.out.println(e.getMessage());}
            Stage currentStage = (Stage) submitButton.getScene().getWindow();
            currentStage.close();
        }
        else {
            errorLabel.setText("Invalid file name! Refer to the naming criteria in the button below.");
        }
    }

    @FXML
    public void showCriteria(ActionEvent event) {

        Label criteriaLabel = new Label("- File name must be 3-20 characters long\n" +
                "- Characters must be letters, numbers, hyphens(-) or underscores(_)\n" +
                "- First character must be a letter\n" +
                "- File name cannot end with a hyphen or underscore");
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(criteriaLabel);
        pane.setMinSize(500, 200);
        pane.setMaxSize( 500, 200);
        Stage currStage = new Stage();
        currStage.setScene(new Scene(pane));
        currStage.initModality(Modality.APPLICATION_MODAL);
        currStage.show();

    }

}
