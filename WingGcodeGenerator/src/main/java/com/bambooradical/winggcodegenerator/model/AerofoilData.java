/*
 *  Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

import java.util.ArrayList;

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

    public ArrayList<double[]> getTransformedPoints(int xOffset, int yOffset) {
        final Bounds svgBounds = getSvgBounds();
        ArrayList<double[]> transformedPoints = new ArrayList<>();
        for (double[] currentPoint : points) {
            transformedPoints.add(new double[]{(svgBounds.getMaxX() - currentPoint[0]) * chord + xOffset, (svgBounds.getMaxY() - currentPoint[1]) * chord + yOffset});
        }
        return transformedPoints;
    }

    public Bounds getSvgBounds() {
        final Bounds bounds = new Bounds(points[0][0], (points[0][1]));
        for (double[] current : points) {
            bounds.updateX(current[0]);
            bounds.updateY((current[1]));
        }
        return bounds;
    }

    public String toSvgPoints(int xOffset, int yOffset) {
        final Bounds svgBounds = getSvgBounds();
        StringBuilder builder = new StringBuilder();
        for (double[] currentPoint : getTransformedPoints(xOffset, yOffset)) {
            builder.append(currentPoint[0]);
            builder.append(",");
            builder.append(currentPoint[1]);
            builder.append(" ");
        }
        return builder.toString();
    }
}
