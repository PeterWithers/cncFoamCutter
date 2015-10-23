/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

/**
 * @since Sep 30, 2015 21:47:27 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class WingData {

    private int rootChord = 120;
    private int tipChord = 120;
    private int wingLength = 300;
    private long rootAerofoil = 1;
    private long tipAerofoil = 1;
    private int tipSweep = 0;
    private int tipWash = 0;

    public int getRootChord() {
        return rootChord;
    }

    public void setRootChord(int rootChord) {
        this.rootChord = rootChord;
    }

    public int getTipChord() {
        return tipChord;
    }

    public void setTipChord(int tipChord) {
        this.tipChord = tipChord;
    }

    public int getWingLength() {
        return wingLength;
    }

    public void setWingLength(int wingLength) {
        this.wingLength = wingLength;
    }

    public long getRootAerofoil() {
        return rootAerofoil;
    }

    public void setRootAerofoil(long rootAerofoil) {
        this.rootAerofoil = rootAerofoil;
    }

    public long getTipAerofoil() {
        return tipAerofoil;
    }

    public void setTipAerofoil(long tipAerofoil) {
        this.tipAerofoil = tipAerofoil;
    }

    public int getTipSweep() {
        return tipSweep;
    }

    public void setTipSweep(int tipSweep) {
        this.tipSweep = tipSweep;
    }

    public int getTipWash() {
        return tipWash;
    }

    public void setTipWash(int tipWash) {
        this.tipWash = tipWash;
    }   
}
