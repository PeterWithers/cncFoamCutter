/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bambooradical.winggcodegenerator.model;

/**
 * @since Sep 4, 2015 21:10:22 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class MachineData {

    private final int machineDepth;
    private final int machineHeight;
    private final int wireLength;
    private final char verticalAxis1;
    private final char horizontalAxis1;
    private final char verticalAxis2;
    private final char horizontalAxis2;
    private final int viewAngle;
    private final int cuttingSpeed;
    private final int heaterPercent;

    public MachineData(int machineDepth, int machineHeight, int wireLength, char verticalAxis1, char horizontalAxis1, char verticalAxis2, char horizontalAxis2, int viewAngle, int cuttingSpeed, int heaterPercent) {
        this.machineDepth = machineDepth;
        this.machineHeight = machineHeight;
        this.wireLength = wireLength;
        this.verticalAxis1 = verticalAxis1;
        this.horizontalAxis1 = horizontalAxis1;
        this.verticalAxis2 = verticalAxis2;
        this.horizontalAxis2 = horizontalAxis2;
        this.viewAngle = viewAngle;
        this.cuttingSpeed = cuttingSpeed;
        this.heaterPercent = heaterPercent;
    }

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
