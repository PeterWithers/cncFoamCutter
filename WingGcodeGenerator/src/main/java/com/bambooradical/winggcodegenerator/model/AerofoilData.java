/*
 *  Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

/**
 * @since Aug 17, 2015 9:59:18 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class AerofoilData {

    private final String name;
    private final double[][] points;
    private final int chord;

    public AerofoilData(String name, double[][] points, int chord) {
        this.name = name;
        this.points = points;
        this.chord = chord;
    }

    public int getChord() {
        return chord;
    }

    public String getName() {
        return name;
    }

    public double[][] getPoints() {
        return points;
    }

    public Bounds getSvgBounds() {
        final Bounds bounds = new Bounds(points[0][0] * chord, (1 - points[0][1]) * chord);
        for (double[] current : points) {
            bounds.updateX(current[0] * chord);
            bounds.updateY((1 - current[1]) * chord);
        }
        return bounds;
    }

    public String toSvgPoints(int xOffset, int yOffset) {
        StringBuilder builder = new StringBuilder();
        for (double[] currentPoint : points) {
            builder.append((currentPoint[0] * chord + xOffset));
            builder.append(",");
            builder.append(((1 - currentPoint[1]) * chord + yOffset));
            builder.append(" ");
        }
        return builder.toString();
    }
}
