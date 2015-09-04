/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bambooradical.winggcodegenerator.model;

/**
 * @since Sep 4, 2015 21:10:22 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class MachineData {

    private final int machineDepth;
    private final int machineHeight;
    private final int wireLength;

    public MachineData(int machineDepth, int machineHeight, int wireLength) {
        this.machineDepth = machineDepth;
        this.machineHeight = machineHeight;
        this.wireLength = wireLength;
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

    public Bounds getSvgBounds(int viewAngle) {
        final Bounds bounds = new Bounds(0, 0);
        bounds.updateX(machineDepth + viewAngle);
        bounds.updateY(machineHeight + wireLength);
        return bounds;
    }

    public String toSvgPoints(int viewAngle) {
        StringBuilder builder = new StringBuilder();
        builder.append(viewAngle);
        builder.append(",");
        builder.append(0);
        builder.append(" ");

        builder.append(machineDepth+viewAngle);
        builder.append(",");
        builder.append(0);
        builder.append(" ");

        builder.append(machineDepth);
        builder.append(",");
        builder.append(machineHeight);
        builder.append(" ");

        builder.append(0);
        builder.append(",");
        builder.append(machineHeight+viewAngle);
        builder.append(" ");

        builder.append(viewAngle);
        builder.append(",");
        builder.append(0);
        builder.append(" ");

        builder.append(0);
        builder.append(",");
        builder.append(wireLength);
        builder.append(" ");

        builder.append(machineDepth);
        builder.append(",");
        builder.append(wireLength);
        builder.append(" ");

        return builder.toString();
    }
}
