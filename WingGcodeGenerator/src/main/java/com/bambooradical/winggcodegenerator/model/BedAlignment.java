/*
 *  Copyright (C) 2015 Peter Withers
 */

package com.bambooradical.winggcodegenerator.model;

/**
 * @since Oct 11, 2015 21:36:00 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class BedAlignment {
//    can these parameters be passed into multiple objects by spring mvc?
private int wireLength = 600;
private int bedAlignment = 0; // positive values are aligned left offset by the value, nevative values are aligned right offset by the value, zero centre aligns with no offset

private final int rootChord;
private final int tipChord;
private final int rootGcodeChord;
private final int tipGcodeChord;
private final int rootSweep;
private final int tipSweep;
private final int rootGcodeSweep;
private final int tipGcodeSweep;
private final int rootWash;
private final int tipWash;
private final int rootGcodeWash;
private final int tipGcodeWash;

    public BedAlignment(int rootChord, int tipChord, int rootGcodeChord, int tipGcodeChord, int rootSweep, int tipSweep, int rootGcodeSweep, int tipGcodeSweep, int rootWash, int tipWash, int rootGcodeWash, int tipGcodeWash) {
        this.rootChord = rootChord;
        this.tipChord = tipChord;
        this.rootGcodeChord = rootGcodeChord;
        this.tipGcodeChord = tipGcodeChord;
        this.rootSweep = rootSweep;
        this.tipSweep = tipSweep;
        this.rootGcodeSweep = rootGcodeSweep;
        this.tipGcodeSweep = tipGcodeSweep;
        this.rootWash = rootWash;
        this.tipWash = tipWash;
        this.rootGcodeWash = rootGcodeWash;
        this.tipGcodeWash = tipGcodeWash;
    }

    public int getWireLength() {
        return wireLength;
    }

    public int getBedAlignment() {
        return bedAlignment;
    }

    public int getRootChord() {
        return rootChord;
    }

    public int getTipChord() {
        return tipChord;
    }

    public int getRootGcodeChord() {
        return rootGcodeChord;
    }

    public int getTipGcodeChord() {
        return tipGcodeChord;
    }

    public int getRootSweep() {
        return rootSweep;
    }

    public int getTipSweep() {
        return tipSweep;
    }

    public int getRootGcodeSweep() {
        return rootGcodeSweep;
    }

    public int getTipGcodeSweep() {
        return tipGcodeSweep;
    }

    public int getRootWash() {
        return rootWash;
    }

    public int getTipWash() {
        return tipWash;
    }

    public int getRootGcodeWash() {
        return rootGcodeWash;
    }

    public int getTipGcodeWash() {
        return tipGcodeWash;
    }
}
