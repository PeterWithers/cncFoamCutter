/*
 *  Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @since Sep 4, 2015 21:10:22 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
@Entity
public class MachineData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int machineDepth = 350;
    private int machineHeight = 100;
    private int wireLength = 600;
    private char verticalAxis1 = 'Y';
    private char horizontalAxis1 = 'X';
    private char verticalAxis2 = 'Z';
    private char horizontalAxis2 = 'E';
    private int viewAngle = 500;
    private int cuttingSpeed = 250;
    private int heaterPercent = 100;
    private int initialCutHeight = 50;
    private int initialCutLength = 10;
    private int diagramScale = 100;
    private int bedAlignment = 0; // positive values are aligned left offset by the value, nevative values are aligned right offset by the value, zero centre aligns with no offset
    private double wireOffset100Feed = 1;
    private double wireOffset50Feed = 1.5;

    public int getCuttingSpeed() {
        return cuttingSpeed;
    }

    public int getHeaterPercent() {
        return heaterPercent;
    }

    public int getMachineDepth() {
        return machineDepth;
    }

    public int getMachineHeight() {
        return machineHeight;
    }

    public int getWireLength() {
        return wireLength;
    }

    public char getVerticalAxis1() {
        return verticalAxis1;
    }

    public char getHorizontalAxis1() {
        return horizontalAxis1;
    }

    public char getVerticalAxis2() {
        return verticalAxis2;
    }

    public char getHorizontalAxis2() {
        return horizontalAxis2;
    }

    public Bounds getSvgBounds() {
        final Bounds bounds = new Bounds(-5, -5);
        bounds.updateX(machineDepth + viewAngle + 5);
        bounds.updateY(machineHeight + wireLength - viewAngle + 5);
        return bounds;
    }

    public int getHorizontalAxis1SvgPointX() {
        return (machineDepth / 2);
    }

    public int getHorizontalAxis1SvgPointY() {
        return 0;
    }

    public int getVerticalAxis1SvgPointX() {
        return machineDepth;
    }

    public int getVerticalAxis1SvgPointY() {
        return machineHeight / 2;
    }

    public int getHorizontalAxis2SvgPointX() {
        return ((machineDepth / 2) + viewAngle);
    }

    public int getHorizontalAxis2SvgPointY() {
        return (wireLength - viewAngle);
    }

    public int getVerticalAxis2SvgPointX() {
        return (machineDepth + viewAngle);
    }

    public int getVerticalAxis2SvgPointY() {
        return (wireLength - viewAngle + (machineHeight / 2));
    }

    public int getViewAngle() {
        return viewAngle;
    }

    public int getInitialCutHeight() {
        return initialCutHeight;
    }

    public int getInitialCutLength() {
        return initialCutLength;
    }

    public int getDiagramScale() {
        return diagramScale;
    }

    public double getWireOffset100Feed() {
        return wireOffset100Feed;
    }

    public void setWireOffset100Feed(double wireOffset100Feed) {
        this.wireOffset100Feed = wireOffset100Feed;
    }

    public double getWireOffset50Feed() {
        return wireOffset50Feed;
    }

    public void setWireOffset50Feed(double wireOffset50Feed) {
        this.wireOffset50Feed = wireOffset50Feed;
    }

    public void setMachineDepth(int machineDepth) {
        this.machineDepth = machineDepth;
    }

    public void setMachineHeight(int machineHeight) {
        this.machineHeight = machineHeight;
    }

    public void setWireLength(int wireLength) {
        this.wireLength = wireLength;
    }

    public void setVerticalAxis1(char verticalAxis1) {
        this.verticalAxis1 = verticalAxis1;
    }

    public void setHorizontalAxis1(char horizontalAxis1) {
        this.horizontalAxis1 = horizontalAxis1;
    }

    public void setVerticalAxis2(char verticalAxis2) {
        this.verticalAxis2 = verticalAxis2;
    }

    public void setHorizontalAxis2(char horizontalAxis2) {
        this.horizontalAxis2 = horizontalAxis2;
    }

    public void setViewAngle(int viewAngle) {
        this.viewAngle = viewAngle;
    }

    public void setCuttingSpeed(int cuttingSpeed) {
        this.cuttingSpeed = cuttingSpeed;
    }

    public void setHeaterPercent(int heaterPercent) {
        this.heaterPercent = heaterPercent;
    }

    public void setInitialCutHeight(int initialCutHeight) {
        this.initialCutHeight = initialCutHeight;
    }

    public void setInitialCutLength(int initialCutLength) {
        this.initialCutLength = initialCutLength;
    }

    public void setDiagramScale(int diagramScale) {
        this.diagramScale = diagramScale;
    }

    public int getBedAlignment() {
        return bedAlignment;
    }

    public void setBedAlignment(int bedAlignment) {
        this.bedAlignment = bedAlignment;
    }

    public String toSvgPoints() {
        StringBuilder builder = new StringBuilder();
        builder.append(0);
        builder.append(",");
        builder.append(0);
        builder.append(" ");

        builder.append(machineDepth);
        builder.append(",");
        builder.append(0);
        builder.append(" ");

        builder.append(machineDepth);
        builder.append(",");
        builder.append(machineHeight);
        builder.append(" ");

        builder.append(0);
        builder.append(",");
        builder.append(machineHeight);
        builder.append(" ");

        builder.append(0);
        builder.append(",");
        builder.append(0);
        builder.append(" ");

        builder.append(viewAngle);
        builder.append(",");
        builder.append(wireLength - viewAngle);
        builder.append(" ");

        builder.append(machineDepth + viewAngle);
        builder.append(",");
        builder.append(wireLength - viewAngle);
        builder.append(" ");

        builder.append(machineDepth + viewAngle);
        builder.append(",");
        builder.append(wireLength - viewAngle + machineHeight);
        builder.append(" ");

        builder.append(viewAngle);
        builder.append(",");
        builder.append(wireLength - viewAngle + machineHeight);
        builder.append(" ");

        builder.append(viewAngle);
        builder.append(",");
        builder.append(wireLength - viewAngle);
        builder.append(" ");

        builder.append(viewAngle);
        builder.append(",");
        builder.append(wireLength - viewAngle + machineHeight);
        builder.append(" ");

        builder.append(0);
        builder.append(",");
        builder.append(machineHeight);
        builder.append(" ");

        builder.append(machineDepth);
        builder.append(",");
        builder.append(machineHeight);
        builder.append(" ");

        builder.append(machineDepth + viewAngle);
        builder.append(",");
        builder.append(wireLength - viewAngle + machineHeight);
        builder.append(" ");

        builder.append(machineDepth + viewAngle);
        builder.append(",");
        builder.append(wireLength - viewAngle);
        builder.append(" ");

        builder.append(machineDepth);
        builder.append(",");
        builder.append(0);
        builder.append(" ");

        return builder.toString();
    }
}
