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

}
