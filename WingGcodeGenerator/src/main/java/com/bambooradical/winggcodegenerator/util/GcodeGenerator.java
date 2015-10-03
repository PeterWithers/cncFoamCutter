/*
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.util;

import com.bambooradical.winggcodegenerator.model.AerofoilData;
import com.bambooradical.winggcodegenerator.model.MachineData;
import java.util.ArrayList;

/**
 * @since Sep 11, 2015 21:33:06 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class GcodeGenerator {

    ArrayList<double[]> xYpath;
    ArrayList<double[]> zEpath;

    public GcodeGenerator(AerofoilData aerofoilDataRoot, final int rootChord, AerofoilData aerofoilDataTip, final int tipGcodeChord, int machineHeight, int initialCutHeight, int initialCutLength) {
        xYpath = aerofoilDataRoot.getTransformedPoints(initialCutLength, machineHeight - initialCutHeight, rootChord);
        zEpath = aerofoilDataTip.getTransformedPoints(initialCutLength, machineHeight - initialCutHeight, tipGcodeChord);
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

    public String toGcode(MachineData machineData) {
        StringBuilder builder = new StringBuilder();
        startGcode(builder);
        setHeat(builder, machineData.getHeaterPercent());
        pause(builder, 5);
        beep(builder);
        pause(builder, 5);
        // todo: this will not reliably work with differing aerofoils
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
        startGcode(builder);
        int steps = 50 / thickness;
        move(builder, 0, 0, 0, 0, machineData);
        move(builder, 0, 50, 50, 0, machineData);
        for (int currentStep = 0; currentStep < steps; currentStep++) {
            int pwm = (((endPwm - startPwm) / steps) * currentStep) + startPwm;
            int height = 50 - ((50 / steps) * currentStep);
            setHeat(builder, pwm);
            move(builder, currentStep % 2 * 50, height, height, currentStep % 2 * 50, machineData);
            waitForCurrentMovesToFinish(builder);
        }
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
