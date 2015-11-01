/*
 *  Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

/**
 * @since Oct 11, 2015 21:36:00 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class BedAlignment {

    final private int bedAlignment; // positive values are aligned left offset by the value, nevative values are aligned right offset by the value, zero centre aligns with no offset
    final private MachineData machineData;
    final private WingData wingData;
    final private int rootPosition;
    final private int tipPosition;

    public BedAlignment(int bedAlignment, MachineData machineData, WingData wingData) {
        this.bedAlignment = bedAlignment;
        this.machineData = machineData;
        this.wingData = wingData;
        if (bedAlignment == 0) {
            rootPosition = (machineData.getWireLength() - wingData.getWingLength()) / 2;
            tipPosition = (machineData.getWireLength() + wingData.getWingLength()) / 2;
        } else if (bedAlignment > 0) {
            rootPosition = machineData.getWireLength() - bedAlignment - wingData.getWingLength();
            tipPosition = machineData.getWireLength() - bedAlignment;
        } else {
            rootPosition = 0 - bedAlignment;
            tipPosition = 0 - bedAlignment + wingData.getWingLength();
        }
    }

    public int getRootPosition() {
        return rootPosition;
    }

    public int getTipPosition() {
        return tipPosition;
    }

    public int getBedAlignment() {
        return bedAlignment;
    }

    public int getRootChord() {
        return wingData.getRootChord();
    }

    public int getTipChord() {
        return wingData.getTipChord();
    }

    public int getRootSweep() {
        return (int) (wingData.getTipSweep() >= 0 ? 0 : -wingData.getTipSweep());
    }

    public int getTipSweep() {
        return (int) (wingData.getTipSweep() <= 0 ? 0 : wingData.getTipSweep());
    }

    public int getRootWash() {
        return -wingData.getTipWash() / 2;
    }

    public int getTipWash() {
        return wingData.getTipWash() / 2;
    }

    public int getRootGcodeValue(double rootValue, double tipValue) {
        return (int) (rootValue - ((((double) tipValue - rootValue) / wingData.getWingLength()) * (rootPosition)));
    }

    public int getTipGcodeValue(double rootValue, double tipValue) {
        return (int) (tipValue + ((((double) tipValue - rootValue) / wingData.getWingLength()) * (machineData.getWireLength() - tipPosition)));
    }

    private float rootPercentOfWire() {
        return (float) getRootPosition() / machineData.getWireLength();
    }

    public int getProjectedRootXOffset() {
        return machineData.getInitialCutLength() + (int) (machineData.getViewAngle() * rootPercentOfWire());
    }

    public int getProjectedRootYOffset() {
        return (int) ((machineData.getMachineHeight() - machineData.getInitialCutHeight()) + (machineData.getWireLength() - machineData.getViewAngle()) * (rootPercentOfWire()));
    }

    private float tipPercentOfWire() {
        return (float) getTipPosition() / machineData.getWireLength();
    }

    public int getProjectedTipXOffset() {
        return machineData.getInitialCutLength() + (int) (machineData.getViewAngle() * tipPercentOfWire());
    }

    public int getProjectedTipYOffset() {
        return (int) ((machineData.getMachineHeight() - machineData.getInitialCutHeight()) + (machineData.getWireLength() - machineData.getViewAngle()) * (tipPercentOfWire()));
    }
}
