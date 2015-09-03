/*
 *  Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

/**
 * @since Sep 1, 2015 20:17:49 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class Bounds {

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public Bounds(double initialX, double initialY) {
        this.minX = initialX;
        this.minY = initialX;
        this.maxX = initialY;
        this.maxY = initialY;
    }

    public void updateX(double currentX) {
        this.minX = Math.min(this.minX, currentX);
        this.maxX = Math.max(this.maxX, currentX);
    }

    public void updateY(double currentY) {
        this.minY = Math.min(this.minY, currentY);
        this.maxY = Math.max(this.maxY, currentY);
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getWidth() {
        return maxX - minX;
    }

    public double getHeight() {
        return maxY - minY;
    }
}
