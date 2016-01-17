/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bambooradical.winggcodegenerator.model;

/**
 * @since Jan 17, 2016 10:28:31 AM (creation date)
 * @author @author Peter Withers <peter@bambooradical.com>
 */
public class PistachioPropData {

    private int propDiameter = 50;
    private int propThickness = 5;
    private int bladeCount = 5;
    private float shaftDiameter = 5f;
    private float layerHeight = 0.3f;

    public int getPropDiameter() {
        return propDiameter;
    }

    public void setPropDiameter(int propDiameter) {
        this.propDiameter = propDiameter;
    }

    public int getPropThickness() {
        return propThickness;
    }

    public void setPropThickness(int propThickness) {
        this.propThickness = propThickness;
    }

    public int getBladeCount() {
        return bladeCount;
    }

    public void setBladeCount(int bladeCount) {
        this.bladeCount = bladeCount;
    }

    public float getShaftDiameter() {
        return shaftDiameter;
    }

    public void setShaftDiameter(float shaftDiameter) {
        this.shaftDiameter = shaftDiameter;
    }

    public float getLayerHeight() {
        return layerHeight;
    }

    public void setLayerHeight(float layerHeight) {
        this.layerHeight = layerHeight;
    }

    public String getSvgPoints() {
        String svgPoints = "M";
        // draw outer ring
        final double radPerIndex = Math.PI * 2 / 360;
        for (int degreeIndex = 0; degreeIndex <= 360; degreeIndex++) {
            final double xPos = Math.sin(radPerIndex * degreeIndex) * getPropDiameter() / 2;
            final double yPos = Math.cos(radPerIndex * degreeIndex) * getPropDiameter() / 2;
            svgPoints += (50 + xPos) + ", " + yPos + " ";
        }
        final double radPerBlade = Math.PI * 2 / getBladeCount();
//        for (int layerCount = 0; layerCount < propThickness / layerHeight; layerCount += layerHeight) {
        for (double layerCount = 0; layerCount < 10; layerCount++) {
            for (double bladeIndex = 0; bladeIndex <= getBladeCount(); bladeIndex++) {
                final double layerTipRotation = layerCount / 100;
                final double xOuterPos = Math.sin(radPerBlade * (bladeIndex + layerTipRotation)) * getPropDiameter() / 2;
                final double yOuterPos = Math.cos(radPerBlade * (bladeIndex + layerTipRotation)) * getPropDiameter() / 2;
                final double xInnerPos = Math.sin(radPerBlade * bladeIndex) * getShaftDiameter() / 2;
                final double yInnerPos = Math.cos(radPerBlade * bladeIndex) * getShaftDiameter() / 2;
                svgPoints += (50 + xInnerPos) + ", " + yInnerPos + " ";
                svgPoints += (50 + xOuterPos) + ", " + yOuterPos + " ";
                svgPoints += (50 + xInnerPos) + ", " + yInnerPos + " ";
            }
        }
        return svgPoints;
    }
}
