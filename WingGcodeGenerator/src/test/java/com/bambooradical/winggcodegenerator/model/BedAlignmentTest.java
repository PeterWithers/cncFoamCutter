/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since Nov 1, 2015 20:46 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class BedAlignmentTest {

    /**
     * Test of getExtrapolatedSpeed method, of class BedAlignment.
     */
    @Test
    public void testGetExtrapolatedSpeed() {
        System.out.println("getExtrapolatedSpeed");
        final MachineData machineData = new MachineData();
        final WingData wingData = new WingData();
        wingData.setWingLength(machineData.getWireLength() / 3);
        BedAlignment instance = new BedAlignment(0, machineData, wingData);
        assertEquals(machineData.getCuttingSpeed(), instance.getExtrapolatedSpeed(new GcodeMovement(10, 10, 10, 10, 0, 0, 0), 10, 10, 10, 10).speed, 0.0);
        assertEquals(machineData.getCuttingSpeed() * 2, instance.getExtrapolatedSpeed(new GcodeMovement(10, 10, 10, 10, 0, 0, 0), 10, 10, 8, 8).speed, 0.0);
        assertEquals(machineData.getCuttingSpeed() * 2, instance.getExtrapolatedSpeed(new GcodeMovement(10, 10, 10, 10, 0, 0, 0), 10, 10, 10, 8).speed, 0.0);
        assertEquals(machineData.getCuttingSpeed(), instance.getExtrapolatedSpeed(new GcodeMovement(10, 10, 10, 10, 0, 0, 0), 10, 8, 8, 10).speed, 0.0);
        assertEquals(machineData.getCuttingSpeed() * 2, instance.getExtrapolatedSpeed(new GcodeMovement(10, 10, 10, 10, 0, 0, 0), 0, 0, 10, 10).speed, 0.0);
        assertEquals((int) (machineData.getCuttingSpeed() * 1.8), instance.getExtrapolatedSpeed(new GcodeMovement(10, 10, 10, 10, 0, 0, 0), 0, 0, 8, 8).speed, 0.0);
    }

    /**
     * Test of getRootGcodeValue method, of class BedAlignment.
     */
    @Test
    public void testGetRootGcodeValue() {
        System.out.println("getRootGcodeValue");
        final MachineData machineData = new MachineData();
        final WingData wingData = new WingData();
        wingData.setWingLength(machineData.getWireLength() / 3);
        BedAlignment instance = new BedAlignment(0, machineData, wingData);
        assertEquals(10, instance.getRootGcodeValue(5, 0));
        assertEquals(15, instance.getRootGcodeValue(5, -5));
    }

    /**
     * Test of getTipGcodeValue method, of class BedAlignment.
     */
    @Test
    public void testGetTipGcodeValue() {
        System.out.println("getTipGcodeValue");
        final MachineData machineData = new MachineData();
        final WingData wingData = new WingData();
        wingData.setWingLength(machineData.getWireLength() / 3);
        BedAlignment instance = new BedAlignment(0, machineData, wingData);
        assertEquals(-5, instance.getTipGcodeValue(5, 0));
        assertEquals(15, instance.getTipGcodeValue(5, 10));
    }
}
