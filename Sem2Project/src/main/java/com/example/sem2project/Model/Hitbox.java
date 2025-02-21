package com.example.sem2project.Model;


import com.example.sem2project.Controllers.DisplayedScreenController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.util.ArrayList;


public class Hitbox extends StackPane{
    public static int noOfHitboxes = 0;
    private int hitboxID;
    private Atom originalAtom; //atom the hitbox belongs to
    private int atomHitboxID; //ID of the hitbox on the originalAtom
    private double xPos, yPos; //position of the hitbox - follows the atom it belongs to

    private boolean connected; //is the hitbox connected to another hitbox?
    private int connectedAtom; //atom the hitbox connects to
    private int connectedHitboxID; //ID of the hitbox being connected to

    public static Hitbox startHitbox, endHitbox;

    private static ArrayList<Hitbox> hitboxDB = new ArrayList<Hitbox>();




    Hitbox(Atom atom, int atomHitboxID) {
        this.originalAtom = atom;
        this.atomHitboxID = atomHitboxID;
        this.noOfHitboxes += 1;
        this.hitboxID = noOfHitboxes;
        this.connected = false;
        hitboxDB.add(this);
        System.out.println("hitbox made " + this.hitboxID + " " + this.originalAtom.getAtomID());
        this.enableBonding();

    }

    Hitbox(Hitbox hitbox) {
        this(hitbox.originalAtom, hitbox.atomHitboxID);
    }

    //Getter and setter for connected
    public void setConnected(boolean status) {this.connected = status;}
    public boolean getConnected() {return this.connected;}

    //Getter and setter for xPos & yPos
    public void setxPos(double xPos) {this.xPos = xPos;}
    public double getxPos() {return this.xPos;}
    public void setyPos(double yPos) {this.yPos = yPos;}
    public double getyPos() {return this.yPos;}

    //Getter and setter for originalAtom
    public void setOriginalAtom(Atom atom) {this.originalAtom = atom;}
    public Atom getOriginalAtom() {return this.originalAtom;}

    //Getter for atomHitboxID
    public int getAtomHitboxID() {return this.atomHitboxID;}
    //Getter for hitboxID
    public int getHitboxID() {return this.hitboxID;}

    //Getter for hitboxDB
    public static ArrayList<Hitbox> getHitboxDB() {return hitboxDB;}
    //Reset hitboxDB
    public static void resetHitboxDB() {hitboxDB = new ArrayList<>();}
    public void drawHitbox() {
        if(!connected) {
            //Draw the hitbox circle
            Circle circle = new Circle(5);
            circle.setCenterX(5);
            circle.setCenterY(5);
            circle.setFill(Color.web("#00a6ff"));
            circle.setStroke(Color.web("#00a6ff"));





            this.xPos = originalAtom.getxPos();
            this.yPos = originalAtom.getyPos();
            //Change location of hitbox based on it's ID on the atom
            switch (atomHitboxID) {
                case 1:
                    this.yPos -= 30;
                    break;    //top hitbox
                case 2:
                    this.xPos += 30;
                    break;   //right hitbox
                case 3:
                    this.yPos += 30;
                    break;   //bottom hitbox
                case 4:
                    this.xPos -= 30;
                    break;   //left hitbox
            }
            //Offset location by -5 so it is in the center
            this.setTranslateX(xPos - 5);
            this.setTranslateY(yPos - 5);
            getChildren().clear();
            getChildren().addAll(circle);
            System.out.printf("Hitbox %d drawn\n", this.hitboxID);
        }
    }

    public void hideHitbox() {
        getChildren().clear();
    }

    public void enableBonding() {
        this.setOnMouseClicked(e -> {
            if (Atom.bondButtonSelected) { //bonding only allowed if any of the 3 bondButtons are selected
                if (startHitbox != null) { //check if startHitbox has been selected
                    if (checkFirstAtom()) {
                        if (this.originalAtom != startHitbox.originalAtom) { //check if endHitbox's atom is the same as startHitbox's atom: prevents bonding to self
                            if (!startHitbox.originalAtom.getConnectedAtoms().contains(this.originalAtom)) { //check if endHitbox's atom is already bonded to startHitbox's atom
                                if (startHitbox.originalAtom.getMaxBonds() - startHitbox.originalAtom.getCurrentBonds() >= DisplayedScreenController.bondOrder && this.originalAtom.getMaxBonds() - this.originalAtom.getCurrentBonds() >= DisplayedScreenController.bondOrder) { //check if number of bonds available >= bondOrder
                                    if (checkNumberOfCarbonsBonded()) {
                                        endHitbox = this;
                                        System.out.printf("2nd Hitbox %d selected\n", this.hitboxID);
                                        BondLine line = new BondLine(startHitbox, endHitbox, DisplayedScreenController.bondOrder);
                                        DisplayedScreenController.bondCreated = true;
                                    }
                                    else {
                                        Alert alert = new Alert(AlertType.ERROR);
                                        alert.setHeaderText("Unable to bond!");
                                        alert.setContentText("Side chains are not allowed in this simulation. Try again!");
                                        alert.show();
                                    }
                                } else {
                                    //alert - Atom will exceed full octet!
                                    Alert alert = new Alert(AlertType.ERROR);
                                    alert.setHeaderText("Unable to bond!");
                                    alert.setContentText("Atom cannot exceed full octet. Try again!");
                                    alert.show();

                                }
                            } else {
                                //alert - "These 2 atoms are already bonded to each other!"
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setHeaderText("Unable to bond!");
                                alert.setContentText("These 2 atoms are already bonded to each other. Try again!");
                                alert.show();
                            }
                        } else {
                            //alert - Cannot bond to self!
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setHeaderText("Unable to bond!");
                            alert.setContentText("Atom cannot be bonded to itself. Try again!");
                            alert.show();
                        }
                    }
                    else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setHeaderText("Unable to bond!");
                        alert.setContentText("Possible Reasons:\n1. Default carbon atom must be on either end of the carbon\nTry again!");
                        alert.show();
                    }
                }
                else {
                    startHitbox = this;
                    endHitbox = null;
                    System.out.printf("1st Hitbox %d selected\n", this.hitboxID);
                }
            }
        });
    }

    public void disableBonding() {
        this.setOnMouseClicked(null);
        this.setOnMouseClicked(null);
        startHitbox = null;
        endHitbox = null;
        Atom.bondButtonSelected = false;
    }

    public boolean checkFirstAtom() {
        if (this.originalAtom.getAtomID()==1) {
            if (startHitbox.originalAtom.getAtomType().equals("Carbon")) {
                for (Pair<Atom, Integer> i : this.originalAtom.connectedAtoms) {
                    if (i.getKey().getAtomType().equals("Carbon")) {
                        return false;
                    }
                }
            }
        }
        else if (startHitbox.originalAtom.getAtomID()==1) {
            if (this.originalAtom.getAtomType().equals("Carbon")) {
                for (Pair<Atom, Integer> i : startHitbox.originalAtom.connectedAtoms) {
                    if (i.getKey().getAtomType().equals("Carbon")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkNumberOfCarbonsBonded() {
        int endAtomCarbonCount=0, startAtomCarbonCount=0;
        if (this.originalAtom.getAtomType().equals("Carbon") && startHitbox.originalAtom.getAtomType().equals("Carbon")) {
            for (Pair<Atom, Integer> i : this.originalAtom.connectedAtoms) {
                if (i.getKey().getAtomType().equals("Carbon")) endAtomCarbonCount+=1;
            }
            for (Pair<Atom, Integer> i : startHitbox.originalAtom.connectedAtoms) {
                if (i.getKey().getAtomType().equals("Carbon")) startAtomCarbonCount+=1;
            }
            if (endAtomCarbonCount >= 2 || startAtomCarbonCount >= 2) return false;
        }
        return true;
    }


}
