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
    private Long parentId = null;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date accessDate;
    private String remoteAddress;
    private boolean isBezier = false;
    private boolean isEditable = false;
    private Boolean isHidden = false;
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

    public boolean isBezier() {
        return isBezier;
    }

    public void setBezier(boolean isBezier) {
        this.isBezier = isBezier;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
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

    private double[] rotate(double[] point, double washDeg) {
        double wash = Math.toRadians(washDeg);
        double locX = 0.33;
        double locY = 0;
        return new double[]{((point[0] - locX) * Math.cos(wash) - (point[1] - locY) * Math.sin(wash)) + locX,
            ((point[0] - locX) * Math.sin(wash) + (point[1] - locY) * Math.cos(wash)) + locY};
    }

    public ArrayList<double[]> getTransformedPoints(int xOffset, int yOffset, int chord, int sweep, double wash) {
        ArrayList<double[]> transformedPoints = new ArrayList<>();
        final double initialX = 1;
        final double initialY = 0;
        if (isBezier) {
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
            for (int index = points.length - 1; index > 2; index -= 3) {
                System.out.println("index:" + index);
                final double xP0 = rotate(points[index - 0], wash)[0];
                final double yP0 = rotate(points[index - 0], wash)[1];
                final double xP1 = rotate(points[index - 1], wash)[0];
                final double yP1 = rotate(points[index - 1], wash)[1];
                final double xP2 = rotate(points[index - 2], wash)[0];
                final double yP2 = rotate(points[index - 2], wash)[1];
                final double xP3 = rotate(points[index - 3], wash)[0];
                final double yP3 = rotate(points[index - 3], wash)[1];
                for (double t = 0; t <= 1; t += 0.01) {
                    // linear bezier
                    // P0+t*(P1-P0)
//                    final double xB = xP0 + t * (xP1 - xP0);
//                    final double yB = yP0 + t * (yP1 - yP0);
                    // cubic bezier 
                    // (1-t)3P0+3(1-t)2tP1+3(1-t)t2P2+t3P3
                    final double xB = ((1 - t) * (1 - t) * (1 - t)) * xP0 + 3 * ((1 - t) * (1 - t)) * t * xP1 + 3 * (1 - t) * (t * t) * xP2 + (t * t * t) * xP3;
                    final double yB = ((1 - t) * (1 - t) * (1 - t)) * yP0 + 3 * ((1 - t) * (1 - t)) * t * yP1 + 3 * (1 - t) * (t * t) * yP2 + (t * t * t) * yP3;
                    transformedPoints.add(new double[]{(initialX - xB) * chord + xOffset + sweep, (initialY - yB) * chord + yOffset});
                }
            }
        } else {

//            final double initialX = svgBounds.getMaxX();
//            final double initialY = svgBounds.getMaxY();
            for (int index = points.length - 1; index > -1; index--) {
                double[] currentPoint = rotate(points[index], wash);
                transformedPoints.add(new double[]{(initialX - currentPoint[0]) * chord + xOffset + sweep, (initialY - currentPoint[1]) * chord + yOffset});
            }
        }
        return transformedPoints;
    }

    //    @Transient
    public Bounds getBounds() {
        final Bounds bounds = new Bounds(points[0][0], (points[0][1]));
        for (double[] current : points) {
            bounds.updateX(current[0]);
            bounds.updateY((current[1]));
        }
        return bounds;
    }

//    @Transient
    public String toSvgPoints(int xOffset, int yOffset, int chord, int sweep, double wash) {
        StringBuilder builder = new StringBuilder();
        for (double[] currentPoint : getTransformedPoints(xOffset, yOffset, chord, sweep, wash)) {
            builder.append(currentPoint[0]);
            builder.append(",");
            builder.append(currentPoint[1]);
            builder.append(" ");
        }
        return builder.toString();
    }

    public String toSvgLines(AerofoilData tipData, int xOffsetRoot, int yOffsetRoot, int chordRoot, int sweepRoot, double washRoot, int xOffsetTip, int yOffsetTip, int chordTip, int sweepTip, double washTip) {
        StringBuilder builder = new StringBuilder();
        final ArrayList<double[]> transformedPointsRoot = getTransformedPoints(xOffsetRoot, yOffsetRoot, chordRoot, sweepRoot, washRoot);
        final ArrayList<double[]> transformedPointsTip = tipData.getTransformedPoints(xOffsetTip, yOffsetTip, chordTip, sweepTip, washTip);
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
