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

    public int getRootGcodeChord() {
        return (int) (wingData.getRootChord() - ((((double) wingData.getTipChord() - wingData.getRootChord()) / wingData.getWingLength()) * (rootPosition)));
    }

    public int getTipGcodeChord() {
        return (int) (wingData.getTipChord() + ((((double) wingData.getTipChord() - wingData.getRootChord()) / wingData.getWingLength()) * (machineData.getWireLength() - tipPosition)));
    }

    public int getRootSweep() {
        return 0;
    }

    public int getTipSweep() {
        return wingData.getTipSweep();
    }

    public int getRootGcodeSweep() {
        return 0;
    }

    public int getTipGcodeSweep() {
        return (int) ((double) wingData.getTipSweep() / wingData.getWingLength() * machineData.getWireLength());
    }

    public int getRootWash() {
        return 0;
    }

    public int getTipWash() {
        return wingData.getTipWash();
    }

    public int getRootGcodeWash() {
        return 0;
    }

    public int getTipGcodeWash() {
        return (int) ((double) wingData.getTipWash() / wingData.getWingLength() * machineData.getWireLength());
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
