package com.example.sem2project.Model;

import com.example.sem2project.Controllers.DisplayedScreenController;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

public abstract class Atom extends StackPane implements Interactive{
    private String atomType;
    private int atomID;
    public static int numOfAtoms = 0;
    private int maxBonds; //Carbon atoms can have 4 bonds max
    private int currentBonds; //current no. of bonds the Carbon atom has formed
    private double xPos; //x-coordinate of the atom
    private double yPos; //y-coordinate of the atom
    private String atomText; //text inside atom
    private Color atomColor; //color of atom

    private Hitbox[] hitboxes; //Hitboxes of the atom: index corresponds to hitboxes in a clockwise manner from the top
    public ArrayList<Pair<Atom, Integer>> connectedAtoms; //Atoms that been connected to
    public ArrayList<BondLine> bondLines; //bondLines connected to this atom

    public static int inputType = 0; //0 = displayed, 1 = skeletal, 2 = condensed

    private boolean connectedToFirstAtom = false;

    public static boolean bondButtonSelected = false;

    //Setter and getter for atomType
    public void setAtomType(String atomType) {this.atomType = atomType;}
    public String getAtomType() {return this.atomType;}

    //Setter and getter for AtomID
    public void setAtomID(int atomID) {this.atomID = atomID;}
    public int getAtomID() {return this.atomID;}

    //Setter and getter for xPos
    public void setxPos(double xPos) {this.xPos = xPos;}
    public double getxPos() {return this.xPos;}

    //Setter and getter for yPos
    public void setyPos(double yPos) {this.yPos = yPos;}
    public double getyPos() {return this.yPos;}

    //Setter and getter for Hitboxes
    public void setHitboxes(Hitbox[] hitboxes) {this.hitboxes = hitboxes;} //unused
    public Hitbox[] getHitboxes() {return this.hitboxes;}

    //Setter and getter for connectedAtoms
    public void setConnectedAtoms(ArrayList<Pair<Atom,Integer>> connectedAtoms) {this.connectedAtoms = connectedAtoms;}
    public ArrayList<Pair<Atom,Integer>>  getConnectedAtoms() {return this.connectedAtoms;}

    //Setter and getter for connectedToFirstAtom
    public void setConnectedToFirstAtom(boolean status) {this.connectedToFirstAtom = status;}
    public boolean getConnectedToFirstAtom() {return this.connectedToFirstAtom;}


    //Setter and getter for max & current number of bonds
    public void setMaxBonds(int maxBonds) {this.maxBonds = maxBonds;}
    public int getMaxBonds() {return this.maxBonds;}
    public void setCurrentBonds(int currentBonds) {this.currentBonds = currentBonds;}
    public int getCurrentBonds() {return this.currentBonds;}

    //Setter and getter for atom text
    public void setAtomText(String text) {this.atomText = text;}
    public String getAtomText() {return this.atomText;}

    //Setter and getter for atom color
    public void setAtomColor(Color color) {this.atomColor = color;}
    public Color getAtomColor() {return this.atomColor;}

    //Creates a new atom
    public void createNewAtom() {
        this.numOfAtoms += 1; //updates current number of atoms
        this.setAtomID(numOfAtoms); //sets atomID to current number of atoms
        this.hitboxes = new Hitbox[] {new Hitbox(this, 1), new Hitbox(this, 2), new Hitbox(this, 3), new Hitbox(this, 4)};
        this.connectedAtoms = new ArrayList<Pair<Atom,Integer>>();
                this.bondLines = new ArrayList<BondLine>();
    }

    // Draws/redraws the atom
    public abstract void drawAtomDisplayed();
    //Draw available hitboxes of atom - none drawn if full already
    public void drawHitboxes() {
        if (this.maxBonds != this.currentBonds) {
            for (Hitbox i : this.getHitboxes()) {
                if (!i.getConnected()) i.drawHitbox();
            }
        }

    }
    //Hide hitboxes of atom
    public void hideHitboxes(){
        for (Hitbox i : hitboxes) {
            i.hideHitbox();
            i.disableBonding();
        }
    }
    public void drawBondLines() {
        for (BondLine i : bondLines) i.drawBondLine();
    }

    public void hideBondLines() {
        for (BondLine i : bondLines) i.hideBondLine();
    }

    //Implement draggable functionality in atoms
    private double startX, startY;
    private double startxPos, startyPos;
    public void makeDraggable() {
        //Store the original position of the atom & hide hitboxes
        this.setOnMousePressed(e -> {
            startX = e.getSceneX() - this.getLayoutX();
            startY = e.getSceneY() - this.getLayoutY();
            startxPos = this.xPos;
            startyPos = this.yPos;
            this.hideHitboxes();

        });

        //Change translate values so the atom follows the mouse icon - boardStartX=210, boardStartY=10, boardEndX=1045, boardEndY = 590;
        //If mouse is dragged outside canvas, keep the atom within the canvas
        this.setOnMouseDragged(e -> {
            if (e.isPrimaryButtonDown()) {
                if (e.getSceneX() < 230) {
                    //this.setLayoutX(210);
                    this.setxPos(230);
                }
                else if (e.getSceneX() > 1025) {
                    //this.setLayoutX(1005);
                    this.setxPos(1025);
                }
                else {
                    //this.setLayoutX(e.getSceneX() - startX);
                    this.setxPos(e.getSceneX()-startX+20);
                }

                if (e.getSceneY() < 30) {
                    //this.setLayoutY(10);
                    this.setyPos(30);
                }
                else if (e.getSceneY() > 570) {
                    //this.setLayoutY(550);
                    this.setyPos(570);
                }
                else {
                    //this.setLayoutY(e.getSceneY() - startY);
                    this.setyPos(e.getSceneY()-startY+20);
                }
                this.drawAtomDisplayed();
                this.drawBondLines();

            }
        });

        this.setOnMouseReleased(e -> {
            DisplayedScreenController checker = new DisplayedScreenController();
            //Keep the atom inside the canvas when released
            if (e.getSceneX() < 230) {
                this.setxPos(230);
            }
            else if (e.getSceneX() > 1045) {
                this.setxPos(1025);
            }
            else {
                this.setxPos(e.getSceneX());
            }

            if (e.getSceneY() < 10) {
                this.setyPos(30);
            }
            else if (e.getSceneY() > 590) {
                this.setyPos(570);
            }
            else {
                this.setyPos(e.getSceneY());
            }
            this.drawAtomDisplayed();
            if (bondButtonSelected) this.drawHitboxes();
            this.drawBondLines();
        });
    }


    public void disableDraggable() {
        this.setOnMousePressed(null);
        this.setOnMouseDragged(null);
        this.setOnMouseReleased(null);
    }

    public boolean connectedAtomsContainsType(String atomType){
        for (Pair<Atom, Integer> i : connectedAtoms) {
            if (i.getKey().atomType.equals(atomType)) return true;
        }
        return false;
    }
    public boolean connectedAtomsContainsTypeAndBond(String atomType, int bondOrder) {
        for (Pair<Atom, Integer> i : connectedAtoms) {
            if (i.getKey().atomType.equals(atomType) && i.getValue() == bondOrder) return true;
        }
        return false;
    }


}
