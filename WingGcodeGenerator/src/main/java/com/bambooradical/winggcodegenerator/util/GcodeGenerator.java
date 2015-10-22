/*
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.util;

import com.bambooradical.winggcodegenerator.model.AerofoilData;
import com.bambooradical.winggcodegenerator.model.BedAlignment;
import com.bambooradical.winggcodegenerator.model.MachineData;
import java.util.ArrayList;

/**
 * @since Sep 11, 2015 21:33:06 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class GcodeGenerator {

    ArrayList<double[]> xYpath;
    ArrayList<double[]> zEpath;
    private final double offsetDistance = 10;

    public GcodeGenerator(AerofoilData aerofoilDataRoot, BedAlignment bedAlignment, AerofoilData aerofoilDataTip, int machineHeight, int initialCutHeight, int initialCutLength) {
        xYpath = applyCuttingToolOffset(aerofoilDataRoot.getTransformedPoints(initialCutLength, machineHeight - initialCutHeight, bedAlignment.getRootGcodeChord(), bedAlignment.getRootGcodeSweep(), bedAlignment.getRootGcodeWash()));
        zEpath = applyCuttingToolOffset(aerofoilDataTip.getTransformedPoints(initialCutLength, machineHeight - initialCutHeight, bedAlignment.getTipGcodeChord(), bedAlignment.getTipGcodeSweep(), bedAlignment.getTipGcodeWash()));
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

    private ArrayList<double[]> applyCuttingToolOffset(ArrayList<double[]> path) {
        ArrayList<double[]> returnPath = new ArrayList<>();
        for (int currentIndex = 0; currentIndex < path.size(); currentIndex++) {
            final double[] lastPoint = (currentIndex > 0) ? path.get(currentIndex - 1) : null;
            final double[] currentPoint = path.get(currentIndex);
            final double[] nextPoint = (currentIndex < path.size() - 1) ? path.get(currentIndex + 1) : null;
            final double lastRadians = getAngleRadians((lastPoint == null) ? new double[]{0, 0} : lastPoint, currentPoint)+1.57079633;
            final double nextRadians = getAngleRadians((nextPoint == null) ? new double[]{0, 0} : nextPoint, currentPoint)-1.57079633;
            // average the two angles and move the current point along a line defined by the resulting angle
            final double currentRadians = lastRadians + ((nextRadians - lastRadians) / 2);
            returnPath.add(new double[]{currentPoint[0], currentPoint[1]});
            returnPath.add(offsetPoint(currentPoint, offsetDistance, lastRadians));
            returnPath.add(offsetPoint(currentPoint, offsetDistance, currentRadians));
            returnPath.add(offsetPoint(currentPoint, offsetDistance, nextRadians));
            returnPath.add(new double[]{currentPoint[0], currentPoint[1]});
        }
        return returnPath;
    }

    public double[] offsetPoint(final double[] point, double distance, double angleRadians) {
        return new double[]{
            point[0] + (Math.sin(angleRadians) * distance),
            point[1] + (Math.cos(angleRadians) * distance)};
    }

    public double getAngleRadians(final double[] pointA, final double[] pointB) {
        double angleRadians;
        double deltaX = pointA[0] - pointB[0];
        double deltaY = pointA[1] - pointB[1];
        angleRadians = Math.atan2(deltaX, deltaY);
        return angleRadians;
    }

    public GcodeGenerator() {
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

    public String toGcode(MachineData machineData, String infoString) {
        StringBuilder builder = new StringBuilder();
        builder.append("# ").append(infoString).append("\n");
        builder.append("# CuttingSpeed: ").append(machineData.getCuttingSpeed()).append("\n");
        builder.append("# HeaterPercent: ").append(machineData.getHeaterPercent()).append("\n");
        builder.append("# MachineHeight: ").append(machineData.getMachineHeight()).append("\n");
        builder.append("# MachineDepth: ").append(machineData.getMachineDepth()).append("\n");
        builder.append("# WireLength: ").append(machineData.getWireLength()).append("\n");
        builder.append("\n");
        startGcode(builder);
        setHeat(builder, machineData.getHeaterPercent());
        pause(builder, 5);
        beep(builder);
        pause(builder, 5);
        // this requres that each aerofoil path is normalised to have the same number of points and relative intervals for each point
        for (int currentPoint = 0; currentPoint < xYpath.size(); currentPoint++) {
            double[] currentPointRoot = xYpath.get(currentPoint);
            double[] currentPointTip = zEpath.get(currentPoint);;
            move(builder,
                    currentPointRoot[0], currentPointRoot[1],
                    currentPointTip[0], currentPointTip[1],
                    machineData);
        }
        waitForCurrentMovesToFinish(builder);
        beep(builder);
        pause(builder, 1);
        setHeat(builder, 0);
        disableSteppers(builder);
        beep(builder);
        return builder.toString();
    }

    public String generateTestGcode(MachineData machineData, int thickness, int startPwm, int endPwm, int startFeed, int endFeed) {
        StringBuilder builder = new StringBuilder();
        builder.append("# Calibration Gcode\n");
        builder.append("# thickness: ").append(thickness).append("\n");
        builder.append("# startPwm: ").append(startPwm).append("\n");
        builder.append("# endPwm: ").append(endPwm).append("\n");
        builder.append("# startFeed: ").append(startFeed).append("\n");
        builder.append("# endFeed: ").append(endFeed).append("\n");
        builder.append("\n");
        startGcode(builder);
        move(builder, 0, -machineData.getMachineHeight(), 0, -machineData.getMachineHeight(), machineData);
        waitForCurrentMovesToFinish(builder);
        zeroVerticals(builder, machineData);
        zeroHorizontals(builder, machineData);
        waitForCurrentMovesToFinish(builder);
        int steps = machineData.getMachineHeight() / thickness;
        move(builder, 0, 0, 0, 0, machineData);
        move(builder, 0, machineData.getMachineHeight(), 0, machineData.getMachineHeight(), machineData);
        waitForCurrentMovesToFinish(builder);
        for (int currentStep = 0; currentStep < steps; currentStep++) {
            int pwm = (((endPwm - startPwm) / steps) * currentStep) + startPwm;
            int feed = (((endFeed - startFeed) / steps) * currentStep) + startFeed;
            machineData.setCuttingSpeed(feed);
            int height = machineData.getMachineHeight() - ((machineData.getMachineHeight() / steps) * currentStep);
            setHeat(builder, pwm);
            move(builder, currentStep % 2 * 50 + 10, height, currentStep % 2 * 50 + 10, height, machineData);
            waitForCurrentMovesToFinish(builder);
        }
        move(builder, 0, 0, 0, 0, machineData);
        waitForCurrentMovesToFinish(builder);
        setHeat(builder, 0);
        disableSteppers(builder);
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

    private void zeroVerticals(StringBuilder builder, MachineData machineData) {
        builder.append("G92 ").append(machineData.getVerticalAxis1()).append("0 ").append(machineData.getVerticalAxis2()).append("0 ").append("\n");
    }

    private void zeroHorizontals(StringBuilder builder, MachineData machineData) {
        builder.append("G92 ").append(machineData.getHorizontalAxis1()).append("0 ").append(machineData.getHorizontalAxis2()).append("0 ").append("\n");
    }

    private void pause(StringBuilder builder, int seconds) {
        builder.append("G4 S").append(seconds).append("\n");;
    }

    private void waitForCurrentMovesToFinish(StringBuilder builder) {
        builder.append("M400 # wait for current moves to finish").append("\n");;
    }

    private void disableSteppers(StringBuilder builder) {
        // todo: this is not supported by repetier
        builder.append("M18 # disable motors\n");
    }

    private void beep(StringBuilder builder) {
        builder.append("M300 S200 P100 # beep\n");
    }

    private void move(StringBuilder builder, double x, double y, double z, double e, MachineData machineData) {
        builder.append("G1 ").append(machineData.getHorizontalAxis1()).append(x).append(" ").append(machineData.getVerticalAxis1()).append(y).append(" ").append(machineData.getHorizontalAxis2()).append(z).append(" ").append(machineData.getVerticalAxis2()).append(e).append(" F").append(machineData.getCuttingSpeed()).append("\n");
    }
}
