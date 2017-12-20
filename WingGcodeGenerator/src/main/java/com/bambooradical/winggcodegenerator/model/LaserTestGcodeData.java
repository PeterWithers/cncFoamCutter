/**
 * Copyright (C) 2017 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

/**
 * @since Dec 20, 2017 19:48 PM (creation date)
 * @author Peter Withers <peter-gthb@bambooradical.com>
 */
public class LaserTestGcodeData {

    final private String newLine = "\n";
    private int propDiameter = 50;
    private int propThickness = 5;
    private int bladeCount = 5;
    private float shaftDiameter = 5f;
    private float layerHeight = 0.3f;

    public int getPropDiameter() {
        return propDiameter;
    }

    public void setPropDiameter(int propDiameter) {
        this.propDiameter = propDiameter;
    }

    public int getPropThickness() {
        return propThickness;
    }

    public void setPropThickness(int propThickness) {
        this.propThickness = propThickness;
    }

    public int getBladeCount() {
        return bladeCount;
    }

    public void setBladeCount(int bladeCount) {
        this.bladeCount = bladeCount;
    }

    public float getShaftDiameter() {
        return shaftDiameter;
    }

    public void setShaftDiameter(float shaftDiameter) {
        this.shaftDiameter = shaftDiameter;
    }

    public float getLayerHeight() {
        return layerHeight;
    }

    public void setLayerHeight(float layerHeight) {
        this.layerHeight = layerHeight;
    }

    public String getGcode() {
        //        for (double distance = 0.1; distance < 1; distance += 0.1) {
//            for (double pause = 0; pause < 1; pause += 0.1) {
        double distance = 1;
        StringBuilder stringBuilder = new StringBuilder();
//        PrintWriter writer = new PrintWriter("target/distance" + distance + ".gcode", "UTF-8");
        stringBuilder.append("M05 S0"); // turn off laser
        stringBuilder.append(newLine);
        stringBuilder.append("G90"); // Set to Absolute Positioning
        stringBuilder.append(newLine);
//        stringBuilder.append("G1 Z0"); // linear move
        stringBuilder.append("G21"); // Millimeters
        stringBuilder.append(newLine);
        for (double xPos = 0; xPos < 50; xPos += 5) {
//            stringBuilder.append("G4 P0 "); // dwell
//            stringBuilder.append("M05 S0"); // turn off laser
            stringBuilder.append("G0  X").append(xPos).append(" Y0 F3500"); // move
            stringBuilder.append(newLine);
            stringBuilder.append("G4 P0"); // dwell
            stringBuilder.append(newLine);
            double power = 100 + (xPos * 10);
            stringBuilder.append("M04 S").append(power); // turn on laser at power x
            stringBuilder.append(newLine);
            for (double yPos = 0; yPos < 50; yPos += distance) {
                double speed = 500 + (yPos * 20);
                // start loop
//                        stringBuilder.append("G1 F" + speed); // set speed 
                stringBuilder.append("G1 X").append(xPos).append(" Y").append(yPos).append(" F").append(speed); // move at speed
                stringBuilder.append(newLine);
//                    stringBuilder.append("G4 P0 "); // dwell
//                        stringBuilder.append("M05 S0"); // turn off laser
//                        stringBuilder.append("G4 P" + pause); // pause in ms
                // end loop
            }
        }
        stringBuilder.append("G4 P0 "); // dwell
        stringBuilder.append(newLine);
        stringBuilder.append("M05 S0"); // turn off laser
        stringBuilder.append(newLine);
        stringBuilder.append("G1 F3500"); // set speed
        stringBuilder.append(newLine);
        stringBuilder.append("G0 X0 Y0"); // go to home
        stringBuilder.append(newLine);
        stringBuilder.append("M30"); // perhaps this can be used as a repeat command
        stringBuilder.append(newLine);
//        }
//        }
        return stringBuilder.toString();
    }

    public String getSvgPointsClass(int index, int size) {
        return new String[]{"gcodeAll", "gcodeG0", "gcodeG1"}[index];
    }

    public String[] getSvgPoints() {
        String svgPointsAll = "M";
        String svgPointsG0 = "";
        String svgPointsG1 = "";
        String xPos = "0";
        String yPos = "0";
        for (String gcodeLine : getGcode().split(newLine)) {
            if (gcodeLine.startsWith("G0")) {
                svgPointsG0 += "M" + xPos + ", " + yPos + " ";
            }
            if (gcodeLine.startsWith("G1")) {
                svgPointsG1 += "M" + xPos + ", " + yPos + " ";
            }
            for (String gcode : gcodeLine.split(" ")) {
                if (gcode.startsWith("X")) {
                    xPos = gcode.substring(1);
                }
                if (gcode.startsWith("Y")) {
                    yPos = gcode.substring(1);
                }
            }
            if (gcodeLine.startsWith("G0")) {
                svgPointsG0 += xPos + ", " + yPos + " ";
            }
            if (gcodeLine.startsWith("G1")) {
                svgPointsG1 += xPos + ", " + yPos + " ";
            }
            svgPointsAll += xPos + ", " + yPos + " ";
        }
        return new String[]{svgPointsAll, svgPointsG0, svgPointsG1};
    }
}
