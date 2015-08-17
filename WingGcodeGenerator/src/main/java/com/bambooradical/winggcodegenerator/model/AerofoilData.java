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

    public AerofoilData(String name, double[][] points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public double[][] getPoints() {
        return points;
    }

}
