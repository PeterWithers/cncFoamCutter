/*
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.util;

import com.bambooradical.winggcodegenerator.model.AerofoilData;
import java.util.ArrayList;

/**
 * @since Sep 11, 2015 21:33:06 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class GcodeGenerator {

    ArrayList<double[]> xYpath;
    ArrayList<double[]> zEpath;

    public GcodeGenerator(AerofoilData aerofoilDataRoot, AerofoilData aerofoilDataTip, int machineHeight, int initialCutHeight, int initialCutLength) {
        xYpath = aerofoilDataRoot.getTransformedPoints(initialCutLength, machineHeight - initialCutHeight);
        zEpath = aerofoilDataTip.getTransformedPoints(initialCutLength, machineHeight - initialCutHeight);
        xYpath.add(0, new double[]{0, 0});
        zEpath.add(0, new double[]{0, 0});
        xYpath.add(1, new double[]{0, machineHeight - initialCutHeight});
        zEpath.add(1, new double[]{0, machineHeight - initialCutHeight});
//        xYpath.add(2, new double[]{initialCutLength, machineHeight - initialCutHeight});
//        zEpath.add(2, new double[]{initialCutLength, machineHeight - initialCutHeight});
//        xYpath.add(new double[]{initialCutLength, machineHeight - initialCutHeight});
//        zEpath.add(new double[]{initialCutLength, machineHeight - initialCutHeight});
        xYpath.add(new double[]{0, machineHeight - initialCutHeight});
        zEpath.add(new double[]{0, machineHeight - initialCutHeight});
        xYpath.add(new double[]{0, 0});
        zEpath.add(new double[]{0, 0});
    }

    public String toSvgXy() {
        StringBuilder builder = new StringBuilder();
        for (double[] currentPoint : xYpath) {
            builder.append(currentPoint[0]);
            builder.append(",");
            builder.append(currentPoint[1]);
            builder.append(" ");
        }
        return builder.toString();
    }

    public String toSvgZe() {
        StringBuilder builder = new StringBuilder();
        for (double[] currentPoint : zEpath) {
            builder.append(currentPoint[0]);
            builder.append(",");
            builder.append(currentPoint[1]);
            builder.append(" ");
        }
        return builder.toString();
    }

    public String toGcode(final int cuttingSpeed, final int heaterPercent) {
        StringBuilder builder = new StringBuilder();
        startGcode(builder);
        setHeat(builder, heaterPercent);
        pause(builder, 60);
        beep(builder);
        pause(builder, 5);
        // todo: this will not reliably work with differing aerofoils
        for (int currentPoint = 0; currentPoint < xYpath.size(); currentPoint++) {
            double[] currentPointRoot = xYpath.get(currentPoint);
            double[] currentPointTip = zEpath.get(currentPoint);;
            move(builder,
                    currentPointRoot[0], currentPointRoot[1],
                    currentPointTip[0], currentPointTip[1],
                    cuttingSpeed);
        }
        setHeat(builder, 0);
        beep(builder);
        pause(builder, 1);
        beep(builder);
        return builder.toString();
    }

    private void startGcode(StringBuilder builder) {
        builder.append("G21 # M117 Set Units to Millimeters\n");
    }

    private void setHeat(StringBuilder builder, int heatPercent) {
        final int pwmValue = Math.max(0, (int) (Math.min(heatPercent, 100) * 0.01 * 256));
        builder.append("M117 setting PWM heater to ").append(heatPercent).append("% with S").append(pwmValue).append("\n");
        builder.append("M106 S").append(pwmValue).append("\n");;
    }

    private void pause(StringBuilder builder, int seconds) {
        builder.append("G4 S").append(seconds).append("\n");;
    }

    private void beep(StringBuilder builder) {
        builder.append("M300 S200 P100 # beep\n");
    }

    private void move(StringBuilder builder, double x, double y, double z, double e, double speed) {
        builder.append("G1 X").append(x).append(" Y").append(y).append(" Z").append(z).append(" E").append(e).append(" F").append(speed).append("\n");
    }
}
