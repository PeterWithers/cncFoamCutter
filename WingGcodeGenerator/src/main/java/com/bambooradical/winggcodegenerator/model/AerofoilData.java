/*
 *  Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;

/**
 * @since Aug 17, 2015 9:59:18 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
@Entity
public class AerofoilData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date accessDate;
    private String remoteAddress;
    private Boolean isBezier = false;
    private Boolean isEditable = false;
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

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public boolean isIsBezier() {
        return isBezier;
    }

    public void setIsBezier(boolean isBezier) {
        this.isBezier = isBezier;
    }

    public boolean isIsEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
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
    public ArrayList<double[]> getTransformedPoints(int xOffset, int yOffset, int chord, int sweep) {
        ArrayList<double[]> transformedPoints = new ArrayList<>();
        final double initialX = points[0][0] + (sweep / chord);
        final double initialY = points[0][1];
        if (isBezier != null && isBezier) {
            // linear
//            for (int index = points.length - 1; index > 0; index -= 1) {
//                final double xP0 = points[index - 0][0];
//                final double yP0 = points[index - 0][1];
//                final double xP1 = points[index - 1][0];
//                final double yP1 = points[index - 1][1];
////                final double xP2 = points[index - 2][0];
////                final double yP2 = points[index - 2][1];
////                final double xP3 = points[index - 3][0];
////                final double yP3 = points[index - 3][1];
//                for (double t = 0; t <= 1; t += 0.01) {
//                    final double xB = xP0 + t * (xP1 - xP0);
//                    final double yB = yP0 + t * (yP1 - yP0);
//                    // linear bezier
//                    // P0+t*(P1-P0)
//                    // cubic bezier
//                    // (1-t)3P0+3(1-t)2tP1+3(1-t)t2P2+t3P3
//                    transformedPoints.add(new double[]{(initialX - xB) * chord + xOffset, (initialY - yB) * chord + yOffset});
//                }
//            }
            // cubic
            for (int index = points.length - 1; index > 0; index -= 3) {
                System.out.println("index:" + index);
                final double xP0 = points[index - 0][0];
                final double yP0 = points[index - 0][1];
                final double xP1 = points[index - 1][0];
                final double yP1 = points[index - 1][1];
                final double xP2 = points[index - 2][0];
                final double yP2 = points[index - 2][1];
                final double xP3 = points[index - 3][0];
                final double yP3 = points[index - 3][1];
                for (double t = 0; t <= 1; t += 0.01) {
                    // linear bezier
                    // P0+t*(P1-P0)
//                    final double xB = xP0 + t * (xP1 - xP0);
//                    final double yB = yP0 + t * (yP1 - yP0);
                    // cubic bezier 
                    // (1-t)3P0+3(1-t)2tP1+3(1-t)t2P2+t3P3
                    final double xB = ((1 - t) * (1 - t) * (1 - t)) * xP0 + 3 * ((1 - t) * (1 - t)) * t * xP1 + 3 * (1 - t) * (t * t) * xP2 + (t * t * t) * xP3;
                    final double yB = ((1 - t) * (1 - t) * (1 - t)) * yP0 + 3 * ((1 - t) * (1 - t)) * t * yP1 + 3 * (1 - t) * (t * t) * yP2 + (t * t * t) * yP3;
                    transformedPoints.add(new double[]{(initialX - xB) * chord + xOffset, (initialY - yB) * chord + yOffset});
                }
            }
        } else {

//            final double initialX = svgBounds.getMaxX();
//            final double initialY = svgBounds.getMaxY();
            for (int index = points.length - 1; index > -1; index--) {
                double[] currentPoint = points[index];
                transformedPoints.add(new double[]{(initialX - currentPoint[0]) * chord + xOffset, (initialY - currentPoint[1]) * chord + yOffset});
            }
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
    public String toSvgPoints(int xOffset, int yOffset, int chord, int sweep) {
        StringBuilder builder = new StringBuilder();
        for (double[] currentPoint : getTransformedPoints(xOffset, yOffset, chord, sweep)) {
            builder.append(currentPoint[0]);
            builder.append(",");
            builder.append(currentPoint[1]);
            builder.append(" ");
        }
        return builder.toString();
    }

    public String toSvgLines(int xOffsetRoot, int yOffsetRoot, int chordRoot, int xOffsetTip, int yOffsetTip, int chordTip, int sweep) {
        StringBuilder builder = new StringBuilder();
        final ArrayList<double[]> transformedPointsRoot = getTransformedPoints(xOffsetRoot, yOffsetRoot, chordRoot, 0);
        final ArrayList<double[]> transformedPointsTip = getTransformedPoints(xOffsetTip, yOffsetTip, chordTip, sweep);
        for (int index = 0; index < transformedPointsRoot.size(); index++) {
            builder.append(transformedPointsRoot.get(index)[0]);
            builder.append(",");
            builder.append(transformedPointsRoot.get(index)[1]);
            builder.append(" ");
            builder.append(transformedPointsTip.get(index)[0]);
            builder.append(",");
            builder.append(transformedPointsTip.get(index)[1]);
            builder.append(" ");
        }
        return builder.toString();
    }
}
