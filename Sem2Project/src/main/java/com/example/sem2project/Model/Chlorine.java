package com.example.sem2project.Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;

public class Chlorine extends Atom{


    public Chlorine(double xPos, double yPos) {
        this.setxPos(xPos);
        this.setyPos(yPos);
        this.createNewAtom();
        this.setMaxBonds(1);
        this.setCurrentBonds(0);
        this.setAtomText("Cl");
        this.setAtomColor(Color.web("#17BEBB"));
        this.setAtomType("Chlorine");
        if (inputType == 0) this.drawAtomDisplayed(); //calls createNewAtom() from Atom class and draws the atom
        this.makeDraggable();


    }

    public void drawAtomDisplayed() {
        //Draw the atom cirle
        Circle circle = new Circle();
        circle.setCenterX(20);
        circle.setCenterY(20);
        circle.setRadius(20);
        circle.setFill(Color.web("17BEBB"));
        circle.setStroke(Color.BLACK);
        //Draw text inside circle
        Text atomText = new Text("Cl");
        atomText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        atomText.setTextAlignment(TextAlignment.CENTER);

        //Debugging to check space of Pane -> this.setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(0), new Insets(0))));

        //Offset location by -20 so that positioning is based on the center
        this.setLayoutX(this.getxPos()-20);
        this.setLayoutY(this.getyPos()-20);
        getChildren().clear();
        getChildren().addAll(circle, atomText);

        if (bondButtonSelected) this.drawHitboxes();


    }

}
