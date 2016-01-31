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
    final private double rootPosition;
    final private double tipPosition;

    public BedAlignment(int bedAlignment, MachineData machineData, WingData wingData) {
        this.bedAlignment = bedAlignment;
        this.machineData = machineData;
        this.wingData = wingData;
        if (bedAlignment == 0) {
            rootPosition = (machineData.getWireLength() - wingData.getWingLength()) / 2d;
            tipPosition = (machineData.getWireLength() + wingData.getWingLength()) / 2d;
        } else if (bedAlignment > 0) {
            rootPosition = machineData.getWireLength() - bedAlignment - wingData.getWingLength();
            tipPosition = machineData.getWireLength() - bedAlignment;
        } else {
            rootPosition = 0 - bedAlignment;
            tipPosition = 0 - bedAlignment + wingData.getWingLength();
        }
    }

    public double getRootPosition() {
        return rootPosition;
    }

    public double getTipPosition() {
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

    public double getRootGcodeValue(double rootValue, double tipValue) {
        return (rootValue - ((((double) tipValue - rootValue) / wingData.getWingLength()) * (rootPosition)));
    }

    public double getTipGcodeValue(double rootValue, double tipValue) {
        return (tipValue + ((((double) tipValue - rootValue) / wingData.getWingLength()) * (machineData.getWireLength() - tipPosition)));
    }

    public GcodeMovement getExtrapolatedSpeed(GcodeMovement lastGcodeMovement, final double tipHorizontal, final double tipVertical, final double rootHorizontal, final double rootVertical) {
        final double extrapolatedSpeed;
        final double rootDistance;
        final double tipDistance;
        if (lastGcodeMovement == null) {
            extrapolatedSpeed = machineData.getCuttingSpeed();
            tipDistance = 0d;
            rootDistance = 0d;
        } else {
            final double rootHorDiff = lastGcodeMovement.rootHorizontal - rootHorizontal;
            final double rootVerDiff = lastGcodeMovement.rootVertical - rootVertical;
            rootDistance = Math.sqrt(rootHorDiff * rootHorDiff + rootVerDiff * rootVerDiff);
            final double tipHorDiff = lastGcodeMovement.tipHorizontal - tipHorizontal;
            final double tipVerDiff = lastGcodeMovement.tipVertical - tipVertical;
            tipDistance = Math.sqrt(tipHorDiff * tipHorDiff + tipVerDiff * tipVerDiff);
            if (rootDistance < tipDistance) {
                if (tipDistance == 0) {
                    extrapolatedSpeed = machineData.getCuttingSpeed();
                } else {
                    extrapolatedSpeed = getTipGcodeValue(machineData.getCuttingSpeed() * (rootDistance / tipDistance), machineData.getCuttingSpeed());
                }
            } else {
                if (rootDistance == 0) {
                    extrapolatedSpeed = machineData.getCuttingSpeed();
                } else {
                    extrapolatedSpeed = getRootGcodeValue(machineData.getCuttingSpeed(), machineData.getCuttingSpeed() * (tipDistance / rootDistance));
                }
            }
        }
        return new GcodeMovement(tipHorizontal, tipVertical, rootHorizontal, rootVertical, tipDistance, rootDistance, (extrapolatedSpeed < machineData.getCuttingSpeed()) ? machineData.getCuttingSpeed() : extrapolatedSpeed);
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
