package com.example.sem2project.Controllers;

import com.example.sem2project.Algorithm;
import com.example.sem2project.Model.*;
import com.example.sem2project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DisplayedScreenController implements Initializable{

    /*
    to-do:
    1. get actual atoms for the drag and drop
    2. implement the controls for it

    3. implement hitboxes bond joining fuckckkkkcqeojfeqifniqf
    4. suffer
     */



    @FXML
    private Button carbonButton;
    @FXML
    private Button hydrogenButton;
    @FXML
    private Button oxygenButton;
    @FXML
    private Button nitrogenButton;
    @FXML
    private Button chlorineButton;
    @FXML
    private Button bromineButton;
    @FXML
    private Button fluorineButton;
    @FXML
    private Button iodineButton;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button genButton;
    @FXML
    private Label textLabel;
    @FXML
    private Pane mainPane;
    @FXML
    private Button singleBondButton;
    @FXML
    private Button doubleBondButton;
    @FXML
    private Button tripleBondButton;


    public static Group compound = new Group();
    public static ArrayList<Atom> atoms = new ArrayList<Atom>();
    public static ArrayList<BondLine> bondLines = new ArrayList<BondLine>();


    private static final double boardStartX=210, boardStartY=10, boardEndX=1045, boardEndY = 590; //start and end coordinates of the board
    //check if the destination is within the coordinates of the board -> (210, 10) to (1045, 590)
    public static boolean checkValidLocation(double x, double y) {
        if (boardStartX <= x && x <= boardEndX && boardStartY <= y && y <= boardEndY) return true;
        return false;
    }

    private boolean[] typesSelected = {false, false, false, false, false, false, false, false, false, false, false};
    //@FXML
    //private Button[] selectButtons = {carbonButton, hydrogenButton, oxygenButton, nitrogenButton, chlorineButton, bromineButton, fluorineButton, iodineButton, singleBondButton, doubleBondButton, tripleBondButton};

    //Order (11): Carbon, Hydrogen, Oxygen, Nitrogen, Chlorine, Bromine, Fluorine, Iodine, Single/Double/Triple bonds
    @FXML
    public void selectCarbon(ActionEvent event) {
        if (!typesSelected[0]) {
            //if button has not been selected yet, select it and deselect others
            typesSelected[0] = true;
            toggleButton(carbonButton, true);
            textLabel.setText("Currently selected: Carbon");
            for (int i=0;i<typesSelected.length;i++) {if (i != 0) typesSelected[i] = false;}
            toggleAllExcept(carbonButton, false);
            Atom.bondButtonSelected = false;
            for (Atom i : atoms) {
                i.hideHitboxes();
            }

            //Add Event Handler for when mouse is clicked
            mainPane.setOnMouseClicked(e -> {
                //if the location is valid, create the atom
                if (checkValidLocation(e.getSceneX(), e.getSceneY())) {
                    Carbon atom = new Carbon(e.getSceneX(), e.getSceneY());
                    atoms.add(atom);
                    compound.getChildren().add(atom);

                    for (Hitbox i : atom.getHitboxes()) {
                        compound.getChildren().add(i);
                    }

                    System.out.println("Carbon placed " + atom.getAtomID());
                }
                else {
                    System.out.println("Invalid location for atom");
                }

                //reset status of button & label text
                typesSelected[0] = false;
                toggleButton(carbonButton, false);
                textLabel.setText("Select an atom and click to place it on the board!");
                mainPane.setOnMouseClicked(null);
            });
            System.out.println("Carbon selected");
        }
    }
    @FXML
    public void selectHydrogen(ActionEvent actionEvent) {
        if (!typesSelected[1]) {
            //if button has not been selected yet, select it and deselect others
            typesSelected[1] = true;
            toggleButton(hydrogenButton, true);
            textLabel.setText("Currently selected: Hydrogen");
            for (int i=0;i<typesSelected.length;i++) {if (i != 1) typesSelected[i] = false;}
            toggleAllExcept(hydrogenButton, false);
            Atom.bondButtonSelected = false;
            for (Atom i : atoms) {
                i.hideHitboxes();
            }

            //Add Event Handler for when mouse is clicked
            mainPane.setOnMouseClicked(e -> {
                //if the location is valid, create the atom
                if (checkValidLocation(e.getSceneX(), e.getSceneY())) {
                    Hydrogen atom = new Hydrogen(e.getSceneX(), e.getSceneY());
                    atoms.add(atom);
                    compound.getChildren().add(atom);
                    for (Hitbox i : atom.getHitboxes()) {
                        compound.getChildren().add(i);
                    }

                    System.out.println("Hydrogen placed " + atom.getAtomID());
                }
                else {
                    System.out.println("Invalid location for atom");
                }

                //reset status of button & label text
                typesSelected[1] = false;
                toggleButton(hydrogenButton, false);
                textLabel.setText("Select an atom and click to place it on the board!");
                mainPane.setOnMouseClicked(null);
            });
            System.out.println("Hydrogen selected");
        }
    }

    @FXML
    public void selectOxygen(ActionEvent actionEvent) {
        if (!typesSelected[2]) {
            //if button has not been selected yet, select it and deselect others
            typesSelected[2] = true;
            toggleButton(oxygenButton, true);
            textLabel.setText("Currently selected: Oxygen");
            for (int i=0;i<typesSelected.length;i++) {if (i != 2) typesSelected[i] = false;}
            toggleAllExcept(oxygenButton, false);
            Atom.bondButtonSelected = false;
            for (Atom i : atoms) {
                i.hideHitboxes();
            }

            //Add Event Handler for when mouse is clicked
            mainPane.setOnMouseClicked(e -> {
                //if the location is valid, create the atom
                if (checkValidLocation(e.getSceneX(), e.getSceneY())) {
                    Oxygen atom = new Oxygen(e.getSceneX(), e.getSceneY());
                    atoms.add(atom);
                    compound.getChildren().add(atom);
                    for (Hitbox i : atom.getHitboxes()) {
                        compound.getChildren().add(i);
                    }
                    System.out.println("Oxygen placed " + atom.getAtomID());
                }
                else {
                    System.out.println("Invalid location for atom");
                }

                //reset status of button & label text
                typesSelected[2] = false;
                toggleButton(oxygenButton, false);
                textLabel.setText("Select an atom and click to place it on the board!");
                mainPane.setOnMouseClicked(null);
            });
            System.out.println("Oxygen selected");
        }
    }

    @FXML
    public void selectNitrogen(ActionEvent actionEvent) {
        if (!typesSelected[3]) {
            //if button has not been selected yet, select it and deselect others
            typesSelected[3] = true;
            toggleButton(nitrogenButton, true);
            textLabel.setText("Currently selected: Nitrogen");
            for (int i=0;i<typesSelected.length;i++) {if (i != 3) typesSelected[i] = false;}
            toggleAllExcept(nitrogenButton, false);
            Atom.bondButtonSelected = false;
            for (Atom i : atoms) {
                i.hideHitboxes();
            }

            //Add Event Handler for when mouse is clicked
            mainPane.setOnMouseClicked(e -> {
                //if the location is valid, create the atom
                if (checkValidLocation(e.getSceneX(), e.getSceneY())) {
                    Nitrogen atom = new Nitrogen(e.getSceneX(), e.getSceneY());
                    atoms.add(atom);
                    compound.getChildren().add(atom);
                    for (Hitbox i : atom.getHitboxes()) {
                        compound.getChildren().add(i);
                    }
                    System.out.println("Nitrogen placed " + atom.getAtomID());
                }
                else {
                    System.out.println("Invalid location for atom");
                }

                //reset status of button & label text
                typesSelected[3] = false;
                toggleButton(nitrogenButton, false);
                textLabel.setText("Select an atom and click to place it on the board!");
                mainPane.setOnMouseClicked(null);
            });
            System.out.println("Nitrogen selected");
        }
    }

    @FXML
    public void selectChlorine(ActionEvent actionEvent) {
        if (!typesSelected[4]) {
            //if button has not been selected yet, select it and deselect others
            typesSelected[4] = true;
            toggleButton(chlorineButton, true);
            textLabel.setText("Currently selected: Chlorine");
            for (int i=0;i<typesSelected.length;i++) {if (i != 4) typesSelected[i] = false;}
            toggleAllExcept(chlorineButton, false);
            Atom.bondButtonSelected = false;
            for (Atom i : atoms) {
                i.hideHitboxes();
            }

            //Add Event Handler for when mouse is clicked
            mainPane.setOnMouseClicked(e -> {
                //if the location is valid, create the atom
                if (checkValidLocation(e.getSceneX(), e.getSceneY())) {
                    Chlorine atom = new Chlorine(e.getSceneX(), e.getSceneY());
                    atoms.add(atom);
                    compound.getChildren().add(atom);
                    for (Hitbox i : atom.getHitboxes()) {
                        compound.getChildren().add(i);
                    }
                    System.out.println("Chlorine placed " + atom.getAtomID());
                }
                else {
                    System.out.println("Invalid location for atom");
                }

                //reset status of button & label text
                typesSelected[4] = false;
                toggleButton(chlorineButton, false);
                textLabel.setText("Select an atom and click to place it on the board!");
                mainPane.setOnMouseClicked(null);
            });
            System.out.println("Chlorine selected");
        }
    }

    @FXML
    public void selectBromine(ActionEvent actionEvent) {
        if (!typesSelected[5]) {
            //if button has not been selected yet, select it and deselect others
            typesSelected[5] = true;
            toggleButton(bromineButton, true);
            textLabel.setText("Currently selected: Bromine");
            for (int i=0;i<typesSelected.length;i++) {if (i != 5) typesSelected[i] = false;}
            toggleAllExcept(bromineButton, false);
            Atom.bondButtonSelected = false;
            for (Atom i : atoms) {
                i.hideHitboxes();
            }

            //Add Event Handler for when mouse is clicked
            mainPane.setOnMouseClicked(e -> {
                //if the location is valid, create the atom
                if (checkValidLocation(e.getSceneX(), e.getSceneY())) {
                    Bromine atom = new Bromine(e.getSceneX(), e.getSceneY());
                    atoms.add(atom);
                    compound.getChildren().add(atom);
                    for (Hitbox i : atom.getHitboxes()) {
                        compound.getChildren().add(i);
                    }
                    System.out.println("Bromine placed " + atom.getAtomID());
                }
                else {
                    System.out.println("Invalid location for atom");
                }

                //reset status of button & label text
                typesSelected[5] = false;
                toggleButton(bromineButton, false);
                textLabel.setText("Select an atom and click to place it on the board!");
                mainPane.setOnMouseClicked(null);
            });
            System.out.println("Bromine selected");
        }
    }
    @FXML
    public void selectFluorine(ActionEvent actionEvent) {
        if (!typesSelected[6]) {
            //if button has not been selected yet, select it and deselect others
            typesSelected[6] = true;
            toggleButton(fluorineButton, true);
            textLabel.setText("Currently selected: Fluorine");
            for (int i=0;i<typesSelected.length;i++) {if (i != 6) typesSelected[i] = false;}
            toggleAllExcept(fluorineButton, false);
            Atom.bondButtonSelected = false;
            for (Atom i : atoms) {
                i.hideHitboxes();
            }

            //Add Event Handler for when mouse is clicked
            mainPane.setOnMouseClicked(e -> {
                //if the location is valid, create the atom
                if (checkValidLocation(e.getSceneX(), e.getSceneY())) {
                    Fluorine atom = new Fluorine(e.getSceneX(), e.getSceneY());
                    atoms.add(atom);
                    compound.getChildren().add(atom);
                    for (Hitbox i : atom.getHitboxes()) {
                        compound.getChildren().add(i);
                    }
                    System.out.println("Fluorine placed " + atom.getAtomID());
                }
                else {
                    System.out.println("Invalid location for atom");
                }

                //reset status of button & label text
                typesSelected[6] = false;
                toggleButton(fluorineButton, false);
                textLabel.setText("Select an atom and click to place it on the board!");
                mainPane.setOnMouseClicked(null);
            });
            System.out.println("Fluorine selected");
        }
    }

    @FXML
    public void selectIodine(ActionEvent actionEvent) {
        if (!typesSelected[7]) {
            //if button has not been selected yet, select it and deselect others
            typesSelected[7] = true;
            toggleButton(iodineButton, true);
            textLabel.setText("Currently selected: Iodine");
            for (int i=0;i<typesSelected.length;i++) {if (i != 7) typesSelected[i] = false;}
            toggleAllExcept(iodineButton, false);
            for (Atom i : atoms) {
                i.hideHitboxes();
            }

            //Add Event Handler for when mouse is clicked
            mainPane.setOnMouseClicked(e -> {
                //if the location is valid, create the atom
                if (checkValidLocation(e.getSceneX(), e.getSceneY())) {
                    Iodine atom = new Iodine(e.getSceneX(), e.getSceneY());
                    atoms.add(atom);
                    compound.getChildren().add(atom);
                    for (Hitbox i : atom.getHitboxes()) {
                        compound.getChildren().add(i);
                    }
                    System.out.println("Iodine placed " + atom.getAtomID());
                }
                else {
                    System.out.println("Invalid location for atom");
                }

                //reset status of button & label text
                typesSelected[7] = false;
                toggleButton(iodineButton, false);
                textLabel.setText("Select an atom and click to place it on the board!");
                mainPane.setOnMouseClicked(null);
            });
            System.out.println("Iodine selected");
        }

    }

    private int clickCount = 0;
    public static int bondOrder;
    public static boolean bondCreated = false;
    @FXML
    public void selectSingleBond(ActionEvent actionEvent) {
        if (!typesSelected[8]) {
            //if button has not been selected yet, select it and deselect others
            typesSelected[8] = true;
            toggleButton(singleBondButton, true);
            textLabel.setText("Currently selected: Single Bond\n(Click twice to cancel)");
            for (int i=0;i<typesSelected.length;i++) {if (i != 8) typesSelected[i] = false;}
            toggleAllExcept(singleBondButton, false);
            Atom.bondButtonSelected = true;
            for (Atom i : atoms) {
                i.drawHitboxes();
                for (Hitbox j : i.getHitboxes()) j.enableBonding();
            }
            bondOrder = 1;
            clickCount = 0;
            mainPane.setOnMouseClicked(e -> {
                clickCount += 1;
                if (clickCount >= 2) {
                    if (bondCreated) {
                        bondLines.add(BondLine.currentlyGeneratedLine);
                        compound.getChildren().add(0, BondLine.currentlyGeneratedLine);
                        bondCreated = false;
                        System.out.println("Single bond created");
                    }
                    Hitbox.startHitbox = null;
                    Hitbox.endHitbox = null;
                    typesSelected[8] = false;
                    toggleButton(singleBondButton, false);
                    textLabel.setText("Select an atom and click to place it on the board!");
                    mainPane.setOnMouseClicked(null);
                    Atom.bondButtonSelected = false;
                    for (Atom i : atoms) {
                        i.hideHitboxes();
                    }
                    clickCount = 0;

                }
            });
        }
    }

    @FXML
    public void selectDoubleBond(ActionEvent actionEvent) {
        if (!typesSelected[9]) {
            //if button has not been selected yet, select it and deselect others
            typesSelected[9] = true;
            toggleButton(doubleBondButton, true);
            textLabel.setText("Currently selected: Double Bond\n(Click twice to cancel)");
            for (int i=0;i<typesSelected.length;i++) {if (i != 9) typesSelected[i] = false;}
            toggleAllExcept(doubleBondButton, false);
            Atom.bondButtonSelected = true;
            for (Atom i : atoms) {
                i.drawHitboxes();
                for (Hitbox j : i.getHitboxes()) j.enableBonding();
            }
            bondOrder = 2;
            clickCount = 0;
            mainPane.setOnMouseClicked(e -> {
                clickCount += 1;
                if (clickCount >= 2) {
                    if (bondCreated) {
                        bondLines.add(BondLine.currentlyGeneratedLine);
                        compound.getChildren().add(0, BondLine.currentlyGeneratedLine);
                        bondCreated = false;
                        System.out.println("Double bond created");
                    }
                    Hitbox.startHitbox = null;
                    Hitbox.endHitbox = null;
                    typesSelected[9] = false;
                    toggleButton(doubleBondButton, false);
                    textLabel.setText("Select an atom and click to place it on the board!");
                    mainPane.setOnMouseClicked(null);
                    Atom.bondButtonSelected = false;
                    for (Atom i : atoms) {
                        i.hideHitboxes();
                    }
                    clickCount = 0;

                }
            });
        }
    }

    @FXML
    public void selectTripleBond(ActionEvent actionEvent) {
        if (!typesSelected[10]) {
            //if button has not been selected yet, select it and deselect others
            typesSelected[10] = true;
            toggleButton(tripleBondButton, true);
            textLabel.setText("Currently selected: Triple Bond\n(Click twice to cancel)");
            for (int i=0;i<typesSelected.length;i++) {if (i != 10) typesSelected[i] = false;}
            toggleAllExcept(tripleBondButton, false);
            Atom.bondButtonSelected = true;
            for (Atom i : atoms) {
                i.drawHitboxes();
                for (Hitbox j : i.getHitboxes()) j.enableBonding();
            }
            bondOrder = 3;
            clickCount = 0;
            mainPane.setOnMouseClicked(e -> {
                clickCount += 1;
                if (clickCount >= 2) {
                    if (bondCreated) {
                        bondLines.add(BondLine.currentlyGeneratedLine);
                        compound.getChildren().add(0, BondLine.currentlyGeneratedLine);
                        bondCreated = false;
                        System.out.println("Triple bond created");
                    }
                    Hitbox.startHitbox = null;
                    Hitbox.endHitbox = null;
                    typesSelected[10] = false;
                    toggleButton(tripleBondButton, false);
                    textLabel.setText("Select an atom and click to place it on the board!");
                    mainPane.setOnMouseClicked(null);
                    Atom.bondButtonSelected = false;
                    for (Atom i : atoms) {
                        i.hideHitboxes();
                    }
                    clickCount = 0;

                }
            });
        }
    }

    @FXML
    public void toggleButton(Button button, boolean toggleStatus) { //toggleStatus: true = selected, false = not selected
        if (!toggleStatus) {
            if (button == singleBondButton || button == doubleBondButton || button == tripleBondButton);
            else button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), new Insets(0))));
        }
        else {
            if (button == singleBondButton || button == doubleBondButton || button == tripleBondButton); //do nothing :>
            else button.setBackground(new Background(new BackgroundFill(new Color(1, 0, 0, 0.3), new CornerRadii(0), new Insets(0))));
        }

    }

    @FXML
    public void toggleAllExcept(Button button, boolean toggleStatus) {

        toggleButton(carbonButton, toggleStatus);
        toggleButton(hydrogenButton, toggleStatus);
        toggleButton(oxygenButton, toggleStatus);
        toggleButton(nitrogenButton, toggleStatus);
        toggleButton(chlorineButton, toggleStatus);
        toggleButton(bromineButton, toggleStatus);
        toggleButton(fluorineButton, toggleStatus);
        toggleButton(iodineButton, toggleStatus);
        toggleButton(singleBondButton, toggleStatus);
        toggleButton(doubleBondButton, toggleStatus);
        toggleButton(tripleBondButton, toggleStatus);
        toggleButton(button, !toggleStatus);
    }



    @FXML
    public void deleteAll(ActionEvent event) {
        Algorithm.resetAlgorithm();
        //Send an alert to confirm whether user wishes to delete or not
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you wish to delete?");
        alert.setContentText("Clicking OK will delete the entire compound!");


        if (alert.showAndWait().get() == ButtonType.OK) {
            //Reinitialize to 1 Carbon atom in the center
            reset();
            System.out.println("Compound deleted");
        }


    }
    @FXML
    public void loadStartingScreen(ActionEvent event) throws IOException{
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
        Stage mainStage = new Stage();
        //String startingScreenName =  "/startingscreen.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource( "View/startingscreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Chemula - An Organic Chem Simulator");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
    @FXML
    public void saveCompound(ActionEvent event) throws IOException{
        Stage mainStage = new Stage();
        //String startingScreenName =  "/startingscreen.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource( "View/filenamescreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Input File Name");
        mainStage.setScene(scene);
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.show();


    }

    @FXML
    public void loadCompound(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("All Files(*.chem)", "*.chem");
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showOpenDialog(stage);
        ArrayList<Pair<Integer,Integer>> visited = new ArrayList<>();
        if (file != null) {
            reset();
            BufferedReader br = null;
            try {
                String line;
                //get number of lines/atms for adjlist defining
                br = new BufferedReader(new FileReader(file));
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    String[] tokens = line.split(",");
                    Atom atom = null; BondLine bondline=null;
                    if (tokens[0].equals("Atom")) {
                        switch (tokens[1]) {
                            case "Carbon":
                                atom = new Carbon(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                                break;
                            case "Hydrogen":
                                atom = new Hydrogen(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                                break;
                            case "Oxygen":
                                atom = new Oxygen(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                                break;
                            case "Nitrogen":
                                atom = new Nitrogen(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                                break;
                            case "Chlorine":
                                atom = new Chlorine(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                                break;
                            case "Bromine":
                                atom = new Bromine(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                                break;
                            case "Fluorine":
                                atom = new Fluorine(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                                break;
                            case "Iodine":
                                atom = new Iodine(Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                                break;
                        }
                        atoms.add(atom);
                        compound.getChildren().add(atom);
                        for (Hitbox i : atom.getHitboxes()) {
                            compound.getChildren().add(i);
                        }
                    }
                    else {
                        if (!visited.isEmpty()) {
                            int size = visited.size();
                            boolean overallInside = false;
                            for (int i=0;i<size;i++){
                                if (visited.get(i).getKey().equals(Integer.parseInt(tokens[1])) && visited.get(i).getValue().equals(Integer.parseInt(tokens[2]))) {
                                    overallInside = true;
                                }
                                else if(visited.get(i).getKey().equals(Integer.parseInt(tokens[2])) && visited.get(i).getValue().equals(Integer.parseInt(tokens[1]))) {
                                    overallInside = true;
                                }
                            }
                            if (!overallInside) {
                                visited.add(new Pair<Integer,Integer>(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
                                bondline = new BondLine(Hitbox.getHitboxDB().get(Integer.parseInt(tokens[1])-1),Hitbox.getHitboxDB().get(Integer.parseInt(tokens[2])-1),Integer.parseInt(tokens[3]));
                                bondLines.add(bondline);
                                compound.getChildren().add(0,bondline);
                            }
                        }
                        else {
                            visited.add(new Pair<Integer,Integer>(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
                            bondline = new BondLine(Hitbox.getHitboxDB().get(Integer.parseInt(tokens[1])-1),Hitbox.getHitboxDB().get(Integer.parseInt(tokens[2])-1),Integer.parseInt(tokens[3]));
                            bondLines.add(bondline);
                            compound.getChildren().add(0,bondline);
                        }

                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean allowGenerate = true;
    @FXML
    public void generateOutput(ActionEvent event) throws IOException {
        //dont allow generate if not all the atoms are filled yet
        for (Atom i : atoms) {
            if (i.getMaxBonds() != i.getCurrentBonds()) {
                if (!i.getAtomType().equals("Carbon")) allowGenerate = false;
                else {
                    for (Pair<Atom, Integer> j : i.connectedAtoms) {
                        if (!(j.getKey().getAtomType().equals("Hydrogen") || j.getKey().getAtomType().equals("Carbon"))) allowGenerate = false;
                    }
                }
            }
        }
        if (allowGenerate) {
            Algorithm.resetAlgorithm();
            for (Atom i : atoms) {
                if (i.getConnectedToFirstAtom()) Algorithm.compoundAtoms.add(i);
            }
            Stage currentStage = (Stage) genButton.getScene().getWindow();
            //currentStage.close();
            Stage mainStage = new Stage();
            //String startingScreenName =  "/startingscreen.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource( "View/outputscreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            mainStage.setTitle("Result");
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.initModality(Modality.APPLICATION_MODAL);
            mainStage.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Unable to generate name!");
            alert.setContentText("All atoms need to be completely filled first. Try again!");
            alert.show();
        }
        allowGenerate = true;
    }


    Carbon firstAtom;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Reset everything
        reset();
        mainPane.getChildren().add(compound);
    }

    @FXML
    public void reset() {
        for (Atom i : atoms) i = null;
        atoms = new ArrayList<>();
        for (BondLine i : bondLines) i = null;
        bondLines = new ArrayList<>();
        compound.getChildren().clear();
        Atom.numOfAtoms = 0; //Change number of atoms back to 0 so that ID starts from 0
        Hitbox.noOfHitboxes = 0;
        Hitbox.resetHitboxDB();
        firstAtom = new Carbon(300, 300);
        firstAtom.disableDraggable();
        compound.getChildren().add(firstAtom);
        firstAtom.drawAtomDisplayed();
        atoms.add(firstAtom);
        for (Hitbox i : firstAtom.getHitboxes()) {
            compound.getChildren().add(i);
            i.hideHitbox();
        }
        toggleAllExcept(carbonButton, false);
        toggleButton(carbonButton, false);
        mainPane.setOnMouseClicked(null);
        Atom.bondButtonSelected = false;
    }


}
