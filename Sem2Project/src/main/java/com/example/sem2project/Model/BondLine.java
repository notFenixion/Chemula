package com.example.sem2project.Model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Pair;

public class BondLine extends Pane {

    private double startX, startY, endX, endY;
    private Atom connectedAtom1, connectedAtom2;
    private Hitbox startHitbox, endHitbox;
    private int bondOrder;

    public static BondLine currentlyGeneratedLine;

    public BondLine(Hitbox startHitbox, Hitbox endHitbox, int bondOrder) {
        this.bondOrder = bondOrder;
        this.connectedAtom1 = startHitbox.getOriginalAtom();
        this.connectedAtom2 = endHitbox.getOriginalAtom();
        //Add atoms to connectedAtom arraylists + add this bondLine to bondLine arraylists + increase currentBonds of atoms by bondOrder
        connectedAtom1.connectedAtoms.add(new Pair<>(connectedAtom2, bondOrder));
        connectedAtom2.connectedAtoms.add(new Pair<>(connectedAtom1, bondOrder));
        connectedAtom1.bondLines.add(this);
        connectedAtom2.bondLines.add(this);
        connectedAtom1.setCurrentBonds(connectedAtom1.getCurrentBonds()+bondOrder);
        connectedAtom2.setCurrentBonds(connectedAtom2.getCurrentBonds()+bondOrder);

        //if any of the atoms are connected to the stationary 1st atom (Carbon) or is the 1st atom, set ConnectedToFirsmAtom to true
        if (connectedAtom1.getAtomID()==1 || connectedAtom2.getAtomID()==1 || connectedAtom1.getConnectedToFirstAtom() || connectedAtom2.getConnectedToFirstAtom()) {
            connectedAtom1.setConnectedToFirstAtom(true);
            connectedAtom2.setConnectedToFirstAtom(true);

        }

        this.startX = connectedAtom1.getxPos();
        this.startY = connectedAtom1.getyPos();
        this.endX = connectedAtom2.getxPos();
        this.endY = connectedAtom2.getyPos();
        this.startHitbox = startHitbox; this.startHitbox.setConnected(true);
        this.endHitbox = endHitbox; this.endHitbox.setConnected(true);
        currentlyGeneratedLine = this;
        this.drawBondLine();

        System.out.printf(String.format("BondLine made: %s to %s",connectedAtom1, connectedAtom2));
    }

    //Getters for startHitboxID, endHitboxID, bondOrder
    public int getStartHitboxID() {return this.startHitbox.getHitboxID();}
    public int getEndHitboxID() {return this.endHitbox.getHitboxID();}
    public int getBondOrder() {return this.bondOrder;}


    private Line line1_1=new Line(), line2_1=new Line(), line3_1=new Line(), line1_2=new Line(), line2_2=new Line(), line3_2=new Line();
    public void drawBondLine() {
        /*
        Terminology:
        Line 1,2,3 --> Top, middle, bottom lines (top and bottom spacing is achieved through some calculations)
        Line X_1 --> start to middle of a line
        Line X_2 -->  middle to end of a line

        Example:
        If bondLine goes from (0,0) to (100,100),
        line2_1 = (0,0) to (50,50)
        line2_2 = (50,50) to (100,100)

        */
        this.startX = connectedAtom1.getxPos();
        this.startY = connectedAtom1.getyPos();
        this.endX = connectedAtom2.getxPos();
        this.endY = connectedAtom2.getyPos();
        if (startX <= endX && startY <= endY) {//top left to bottom right  (0,0) to (endX-startX, endY-startY)
            if (Math.abs(endY-startY) <= Math.abs(endX-startX)) {
                line1_1 = new Line(0, -5, (endX-startX)/2, (endY-startY)/2-5);
                line1_2 = new Line((endX-startX)/2, (endY-startY)/2-5, endX-startX, endY-startY-5);
                line3_1 = new Line(0, 5, (endX-startX)/2, (endY-startY)/2+5);
                line3_2 = new Line((endX-startX)/2, (endY-startY)/2+5, endX-startX, endY-startY+5);
            }
            else {
                line1_1 = new Line(-5, 0, (endX-startX)/2-5, (endY-startY)/2);
                line1_2 = new Line((endX-startX)/2-5, (endY-startY)/2, endX-startX-5, endY-startY);
                line3_1 = new Line(5, 0, (endX-startX)/2+5, (endY-startY)/2);
                line3_2 = new Line((endX-startX)/2+5, (endY-startY)/2, endX-startX+5, endY-startY);
            }

            line2_1 = new Line(0, 0, (endX-startX)/2, (endY-startY)/2);
            line2_2 = new Line((endX-startX)/2, (endY-startY)/2, endX-startX, endY-startY);

            line1_1.setStroke(connectedAtom1.getAtomColor());
            line1_2.setStroke(connectedAtom2.getAtomColor());
            line2_1.setStroke(connectedAtom1.getAtomColor());
            line2_2.setStroke(connectedAtom2.getAtomColor());
            line3_1.setStroke(connectedAtom1.getAtomColor());
            line3_2.setStroke(connectedAtom2.getAtomColor());
        }

        else if (startX >= endX && startY >= endY) { //bottom right to top left  (0,0) to (startX-endX, startY-endY)
            if (Math.abs(endY-startY) <= Math.abs(endX-startX)) {
                line1_1 = new Line(0, -5, (startX - endX) / 2, (startY - endY) / 2 - 5);
                line1_2 = new Line((startX - endX) / 2, (startY - endY) / 2 - 5, startX - endX, startY - endY - 5);
                line3_1 = new Line(0, 5, (startX - endX) / 2, (startY - endY) / 2 + 5);
                line3_2 = new Line((startX - endX) / 2, (startY - endY) / 2 + 5, startX - endX, startY - endY + 5);
            }
            else {
                line1_1 = new Line(-5, 0, (startX - endX) / 2 - 5, (startY - endY) / 2);
                line1_2 = new Line((startX - endX) / 2 - 5, (startY - endY) / 2, startX - endX - 5, startY - endY);
                line3_1 = new Line(5, 0, (startX - endX) / 2 + 5, (startY - endY) / 2);
                line3_2 = new Line((startX - endX) / 2 + 5, (startY - endY) / 2, startX - endX + 5, startY - endY);
            }

            line2_1 = new Line(0, 0, (startX - endX) / 2, (startY - endY) / 2);
            line2_2 = new Line((startX - endX) / 2, (startY - endY) / 2, startX - endX, startY - endY);

            line1_1.setStroke(connectedAtom2.getAtomColor());
            line1_2.setStroke(connectedAtom1.getAtomColor());
            line2_1.setStroke(connectedAtom2.getAtomColor());
            line2_2.setStroke(connectedAtom1.getAtomColor());
            line3_1.setStroke(connectedAtom2.getAtomColor());
            line3_2.setStroke(connectedAtom1.getAtomColor());
        }

        else if (startX <= endX && startY >= endY) { //bottom left to top right
            if (Math.abs(endY-startY) <= Math.abs(endX-startX)) {
                line1_1 = new Line(0, startY-endY-5, (endX-startX)/2, (startY-endY)/2-5);
                line1_2 = new Line((endX-startX)/2, (startY-endY)/2-5, endX-startX, -5);
                line3_1 = new Line(0, startY-endY+5, (endX-startX)/2, (startY-endY)/2+5);
                line3_2 = new Line((endX-startX)/2, (startY-endY)/2+5, endX-startX, 5);
            }
            else {
                line1_1 = new Line(-5, startY-endY, (endX-startX)/2-5, (startY-endY)/2);
                line1_2 = new Line((endX-startX)/2-5, (startY-endY)/2, endX-startX-5, 0);
                line3_1 = new Line(5, startY-endY, (endX-startX)/2+5, (startY-endY)/2);
                line3_2 = new Line((endX-startX)/2+5, (startY-endY)/2, endX-startX+5, 0);
            }

            line2_1 = new Line(0, startY-endY, (endX-startX)/2, (startY-endY)/2);
            line2_2 = new Line((endX-startX)/2, (startY-endY)/2, endX-startX, 0);

            line1_1.setStroke(connectedAtom1.getAtomColor());
            line1_2.setStroke(connectedAtom2.getAtomColor());
            line2_1.setStroke(connectedAtom1.getAtomColor());
            line2_2.setStroke(connectedAtom2.getAtomColor());
            line3_1.setStroke(connectedAtom1.getAtomColor());
            line3_2.setStroke(connectedAtom2.getAtomColor());
        }

        else { //top right to bottom left
            if (Math.abs(endY-startY) <= Math.abs(endX-startX)) {
                line1_1 = new Line(0,endY-startY-5,(startX-endX)/2,(endY-startY)/2-5);
                line1_2 = new Line((startX-endX)/2, (endY-startY)/2-5, startX-endX, -5);
                line3_1 = new Line(0,endY-startY+5,(startX-endX)/2,(endY-startY)/2+5);
                line3_2 = new Line((startX-endX)/2, (endY-startY)/2+5, startX-endX, 5);
            }
            else {
                line1_1 = new Line(-5,endY-startY,(startX-endX)/2-5,(endY-startY)/2);
                line1_2 = new Line((startX-endX)/2-5, (endY-startY)/2, startX-endX-5, 0);
                line3_1 = new Line(5,endY-startY,(startX-endX)/2+5,(endY-startY)/2);
                line3_2 = new Line((startX-endX)/2+5, (endY-startY)/2, startX-endX+5, 0);
            }

            line2_1 = new Line(0,endY-startY,(startX-endX)/2,(endY-startY)/2);
            line2_2 = new Line((startX-endX)/2, (endY-startY)/2, startX-endX, 0);

            line1_1.setStroke(connectedAtom2.getAtomColor());
            line1_2.setStroke(connectedAtom1.getAtomColor());
            line2_1.setStroke(connectedAtom2.getAtomColor());
            line2_2.setStroke(connectedAtom1.getAtomColor());
            line3_1.setStroke(connectedAtom2.getAtomColor());
            line3_2.setStroke(connectedAtom1.getAtomColor());
        }
        line1_1.setStrokeWidth(2.0);
        line1_2.setStrokeWidth(2.0);
        line2_1.setStrokeWidth(2.0);
        line2_2.setStrokeWidth(2.0);
        line3_1.setStrokeWidth(2.0);
        line3_2.setStrokeWidth(2.0);

        if (endX >= startX) this.setLayoutX(startX);
        else this.setLayoutX(endX);
        if (endY >= startY) this.setLayoutY(startY);
        else this.setLayoutY(endY);
        getChildren().clear();
        switch (bondOrder) {
            case 1:
                getChildren().addAll(line2_1,line2_2);
                break;
            case 2:
                getChildren().addAll(line1_1,line1_2,line3_1,line3_2);
                break;
            case 3:
                getChildren().addAll(line1_1, line1_2, line2_1, line2_2, line3_1, line3_2);
                break;
        }
        //this.setBackground(new Background(new BackgroundFill(new Color(1, 0, 0, 0.3), new CornerRadii(0), new Insets(0))));
    }


    public void hideBondLine() {
        getChildren().clear();
    }
}
