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
        StringBuilder stringBuilderOuter = new StringBuilder();
        StringBuilder stringBuilderInner = new StringBuilder();
        stringBuilderOuter.append("M05 S0"); // turn off laser
        stringBuilderOuter.append(newLine);
        stringBuilderOuter.append("G90"); // Set to Absolute Positioning
        stringBuilderOuter.append(newLine);
//        stringBuilderOuter.append("G1 Z0"); // linear move
        stringBuilderOuter.append("G21"); // Millimeters
        stringBuilderOuter.append(newLine);
        double minAreaX = 0;
        double minAreaY = 0;
        double maxAreaX = 0;
        double maxAreaY = 0;
        for (double xPos = 0; xPos < gridSize; xPos += lineSpacing) {
//            stringBuilderInner.append("G4 P0 "); // dwell
//            stringBuilderInner.append("M05 S0"); // turn off laser
            stringBuilderInner.append("G0  X").append(xPos).append(" Y0 F").append(flySpeed); // move
            stringBuilderInner.append(newLine);
            stringBuilderInner.append("G4 P0"); // dwell
            stringBuilderInner.append(newLine);
            double power = minPower + (xPos * (maxPower - minPower) / (gridSize / lineSpacing));
            if (aerofoilData == null) {
                stringBuilderInner.append("M04 S").append(power); // turn on laser at power x
                stringBuilderInner.append(newLine);
                boolean isOdd = false;
                for (double yPos = 0; yPos < gridSize; yPos += lineSteps) {
                    double speed = minSpeed + (yPos * (maxSpeed - minSpeed) / (gridSize / lineSteps));
                    final double calculatedX = xPos + ((isOdd) ? lineZigzag : 0);
                    // start loop
//                        stringBuilderInner.append("G1 F" + speed); // set speed 
                    stringBuilderInner.append("G1 X").append(calculatedX).append(" Y").append(yPos).append(" F").append(speed); // move at speed
                    stringBuilderInner.append(newLine);
                    isOdd = !isOdd;
//                    stringBuilderInner.append("G4 P0 "); // dwell
//                        stringBuilderInner.append("M05 S0"); // turn off laser
//                        stringBuilderInner.append("G4 P" + pause); // pause in ms
                    // end loop                    
                    minAreaY = (minAreaY < yPos) ? minAreaY : yPos;
                    maxAreaY = (maxAreaY > yPos) ? maxAreaY : yPos;
                    minAreaX = (minAreaX < calculatedX) ? minAreaX : calculatedX;
                    maxAreaX = (maxAreaX > calculatedX) ? maxAreaX : calculatedX;
                }
            } else {
                double maxX = xPos;
                double minX = xPos;
                for (double yPos = 0; yPos < gridSize; yPos += lineSteps) {
                    double speed = minSpeed + (yPos * (maxSpeed - minSpeed) / (gridSize / lineSteps));
                    yPos += aerofoilData.getBounds().getMaxY() * chordLength;
                    stringBuilderInner.append(String.format("G0 X%.3f Y%.3f F%d", xPos, yPos, flySpeed)); // move
                    stringBuilderInner.append(newLine);
                    stringBuilderInner.append("G4 P0"); // dwell
                    stringBuilderInner.append(newLine);
                    stringBuilderInner.append("M04 S").append(power); // turn on laser at power x
                    stringBuilderInner.append(newLine);
                    double maxY = yPos;
                    double minY = yPos;
                    for (double[] transformedPoints : aerofoilData.getTransformedPoints((int) xPos, (int) yPos, chordLength, 0, 0)) {
                        minX = (minX < transformedPoints[0]) ? minX : transformedPoints[0];
                        minY = (minY < transformedPoints[1]) ? minY : transformedPoints[1];
                        maxX = (maxX > transformedPoints[0]) ? maxX : transformedPoints[0];
                        maxY = (maxY > transformedPoints[1]) ? maxY : transformedPoints[1];
                        stringBuilderInner.append(String.format("G1 X%.3f Y%.3f F%d", transformedPoints[0], transformedPoints[1], (int) speed));
                        stringBuilderInner.append(newLine);
                    }
                    yPos += maxY - minY;
                    minAreaY = (minAreaY < minY) ? minAreaY : minY;
                    maxAreaY = (maxAreaY > maxY) ? maxAreaY : maxY;
                }
                xPos += maxX - minX;
                minAreaX = (minAreaX < minX) ? minAreaX : minX;
                maxAreaX = (maxAreaX > maxX) ? maxAreaX : maxX;
            }
        }
//        stringBuilderOuter.append("# fly around perimeter");
        stringBuilderOuter.append(String.format("G0 X%.3f Y%.3f F%d", minAreaX, minAreaY, flySpeed)); // fly around perimeter
        stringBuilderOuter.append(newLine);
        stringBuilderOuter.append(String.format("G0 X%.3f Y%.3f F%d", minAreaX, maxAreaY, flySpeed)); // fly around perimeter
        stringBuilderOuter.append(newLine);
        stringBuilderOuter.append(String.format("G0 X%.3f Y%.3f F%d", maxAreaX, maxAreaY, flySpeed)); // fly around perimeter
        stringBuilderOuter.append(newLine);
        stringBuilderOuter.append(String.format("G0 X%.3f Y%.3f F%d", maxAreaX, minAreaY, flySpeed)); // fly around perimeter
        stringBuilderOuter.append(newLine);
        stringBuilderOuter.append(String.format("G0 X%.3f Y%.3f F%d", minAreaX, minAreaY, flySpeed)); // fly around perimeter
        stringBuilderOuter.append(newLine);
        stringBuilderOuter.append("G4 P0"); // dwell
        stringBuilderOuter.append(newLine);
        stringBuilderOuter.append(stringBuilderInner);
        stringBuilderOuter.append("G4 P0 "); // dwell
        stringBuilderOuter.append(newLine);
        stringBuilderOuter.append("M05 S0"); // turn off laser
        stringBuilderOuter.append(newLine);
        stringBuilderOuter.append("G1 F3500"); // set speed
        stringBuilderOuter.append(newLine);
        stringBuilderOuter.append("G0 X0 Y0"); // go to home
        stringBuilderOuter.append(newLine);
        stringBuilderOuter.append("M30"); // perhaps this can be used as a repeat command
        stringBuilderOuter.append(newLine);
        return stringBuilderOuter.toString();
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
