/*
 *  Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;


import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * @since Aug 17, 2015 9:59:18 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
@Entity
public class AerofoilData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Lob
    private double[][] points;

    public AerofoilData() {
    }

    public AerofoilData(String name, double[][] points) {
        this.name = name;
        this.points = points;
    }

    public long getId() {
        return id;
    }

    public double[][] getPoints() {
        return points;
    }

    public void setPoints(double[][] points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Transient
    public ArrayList<double[]> getTransformedPoints(int xOffset, int yOffset, int chord) {
        final Bounds svgBounds = getSvgBounds();
//            final double initialX = svgBounds.getMaxX();
//            final double initialY = svgBounds.getMaxY();
        final double initialX = points[0][0];
        final double initialY = points[0][1];
        ArrayList<double[]> transformedPoints = new ArrayList<>();
        for (int index = points.length - 1; index > -1; index--) {
            double[] currentPoint = points[index];
            transformedPoints.add(new double[]{(initialX - currentPoint[0]) * chord + xOffset, (initialY - currentPoint[1]) * chord + yOffset});
        }
        return transformedPoints;
    }

//    @Transient
    public Bounds getSvgBounds() {
        final Bounds bounds = new Bounds(points[0][0], (points[0][1]));
        for (double[] current : points) {
            bounds.updateX(current[0]);
            bounds.updateY((current[1]));
        }
        return bounds;
    }

//    @Transient
    public String toSvgPoints(int xOffset, int yOffset, int chord) {
        final Bounds svgBounds = getSvgBounds();
        StringBuilder builder = new StringBuilder();
        for (double[] currentPoint : getTransformedPoints(xOffset, yOffset, chord)) {
            builder.append(currentPoint[0]);
            builder.append(",");
            builder.append(currentPoint[1]);
            builder.append(" ");
        }
        return builder.toString();
    }
}
