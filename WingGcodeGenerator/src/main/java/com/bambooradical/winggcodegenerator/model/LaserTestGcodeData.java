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

    private int gridSize = 50;
    private int lineSpacing = 5;
    private double lineSteps = 5;
    private double lineZigzag = 2;
    private int chordLength = 100;
    private int flySpeed = 3500;
    private int minPower = 100;
    private int maxPower = 300;
    private int minSpeed = 500;
    private int maxSpeed = 1000;
    private AerofoilData aerofoilData = null;

    public LaserTestGcodeData() {
    }

    public AerofoilData getAerofoilData() {
        return aerofoilData;
    }

    public void setAerofoilData(AerofoilData aerofoilData) {
        this.aerofoilData = aerofoilData;
    }

    public int getChordLength() {
        return chordLength;
    }

    public void setChordLength(int chordLength) {
        this.chordLength = chordLength;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public double getLineSteps() {
        return lineSteps;
    }

    public void setLineSteps(double lineSteps) {
        this.lineSteps = lineSteps;
    }

    public double getLineZigzag() {
        return lineZigzag;
    }

    public void setLineZigzag(double lineZigzag) {
        this.lineZigzag = lineZigzag;
    }

    public int getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(int flySpeed) {
        this.flySpeed = flySpeed;
    }

    public int getMinPower() {
        return minPower;
    }

    public void setMinPower(int minPower) {
        this.minPower = minPower;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(int minSpeed) {
        this.minSpeed = minSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getGcode() {
        //        for (double lineSteps = 0.1; lineSteps < 1; lineSteps += 0.1) {
//            for (double pause = 0; pause < 1; pause += 0.1) {
        StringBuilder stringBuilder = new StringBuilder();
//        PrintWriter writer = new PrintWriter("target/lineSteps" + lineSteps + ".gcode", "UTF-8");
        stringBuilder.append("M05 S0"); // turn off laser
        stringBuilder.append(newLine);
        stringBuilder.append("G90"); // Set to Absolute Positioning
        stringBuilder.append(newLine);
//        stringBuilder.append("G1 Z0"); // linear move
        stringBuilder.append("G21"); // Millimeters
        stringBuilder.append(newLine);
        for (double xPos = 0; xPos < gridSize; xPos += lineSpacing) {
//            stringBuilder.append("G4 P0 "); // dwell
//            stringBuilder.append("M05 S0"); // turn off laser
            stringBuilder.append("G0  X").append(xPos).append(" Y0 F").append(flySpeed); // move
            stringBuilder.append(newLine);
            stringBuilder.append("G4 P0"); // dwell
            stringBuilder.append(newLine);
            double power = minPower + (xPos * (maxPower - minPower) / (gridSize / lineSpacing));
            if (aerofoilData == null) {
                stringBuilder.append("M04 S").append(power); // turn on laser at power x
                stringBuilder.append(newLine);
                boolean isOdd = false;
                for (double yPos = 0; yPos < gridSize; yPos += lineSteps) {
                    double speed = minSpeed + (yPos * (maxSpeed - minSpeed) / (gridSize / lineSteps));
                    // start loop
//                        stringBuilder.append("G1 F" + speed); // set speed 
                    stringBuilder.append("G1 X").append(xPos + ((isOdd) ? lineZigzag : 0)).append(" Y").append(yPos).append(" F").append(speed); // move at speed
                    stringBuilder.append(newLine);
                    isOdd = !isOdd;
//                    stringBuilder.append("G4 P0 "); // dwell
//                        stringBuilder.append("M05 S0"); // turn off laser
//                        stringBuilder.append("G4 P" + pause); // pause in ms
                    // end loop
                }
            } else {
                double maxX = xPos;
                double minX = xPos;
                for (double yPos = 0; yPos < gridSize; yPos += lineSteps) {
                    double speed = minSpeed + (yPos * (maxSpeed - minSpeed) / (gridSize / lineSteps));
                    stringBuilder.append(String.format("G0 X%.6f Y%.6f F%d", xPos, yPos, flySpeed)); // move
                    stringBuilder.append(newLine);
                    stringBuilder.append("G4 P0"); // dwell
                    stringBuilder.append(newLine);
                    stringBuilder.append("M04 S").append(power); // turn on laser at power x
                    stringBuilder.append(newLine);
                    double maxY = yPos;
                    double minY = yPos;
                    for (double[] transformedPoints : aerofoilData.getTransformedPoints((int) xPos, (int) yPos, chordLength, 0, 0)) {
                        minX = (minX < transformedPoints[0]) ? minX : transformedPoints[0];
                        minY = (minY < transformedPoints[1]) ? minY : transformedPoints[1];
                        maxX = (maxX > transformedPoints[0]) ? maxX : transformedPoints[0];
                        maxY = (maxY > transformedPoints[1]) ? maxY : transformedPoints[1];
                        stringBuilder.append(String.format("G1 X%.6f Y%.6f F%.6f", transformedPoints[0], transformedPoints[1], speed));
                        stringBuilder.append(newLine);
                    }
                    yPos += maxY - minY;
                }
                xPos += maxX - minX;
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
