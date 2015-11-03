/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

/**
 * @since Nov 1, 2015 7:24:36 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class GcodeMovement {

    final public double tipHorizontal;
    final public double tipVertical;
    final public double rootHorizontal;
    final public double rootVertical;
    final public double tipDistance;
    final public double rootDistance;
    final public double speed;

    public GcodeMovement(double tipHorizontal, double tipVertical, double rootHorizontal, double rootVertical, double tipDistance, double rootDistance, double speed) {
        this.tipHorizontal = tipHorizontal;
        this.tipVertical = tipVertical;
        this.rootHorizontal = rootHorizontal;
        this.rootVertical = rootVertical;
        this.tipDistance = tipDistance;
        this.rootDistance = rootDistance;
        this.speed = speed;
    }
}
