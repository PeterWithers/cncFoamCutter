/*
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since Oct 22, 2015 21:03:06 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class GcodeGeneratorTest {

    /**
     * Test of getAngleRadians method, of class GcodeGenerator.
     */
    @Test
    public void testGetRadians() {
        System.out.println("getRadians");
        double[] lastPoint = new double[]{0, 0};
        GcodeGenerator instance = new GcodeGenerator(0);
        assertEquals(-1.5707963267948966, instance.getAngleRadians(lastPoint, new double[]{1, 0}), 0.0);
        assertEquals(3.141592653589793, instance.getAngleRadians(lastPoint, new double[]{0, 1}), 0.0);
        assertEquals(-2.356194490192345, instance.getAngleRadians(lastPoint, new double[]{1, 1}), 0.0);
    }

    /**
     * Test of offsetPoint method, of class GcodeGenerator.
     */
    @Test
    public void testOffsetPoint() {
        System.out.println("offsetPoint");
        double[] point = new double[]{0, 0};
        double distance = 1.0;
        double angleRadians = 1.57079633;
        GcodeGenerator instance = new GcodeGenerator(0);
        assertArrayEquals(new double[]{1, 0}, instance.offsetPoint(point, distance, angleRadians), 0.1);
    }

    /**
     * Test of isConcave method, of class GcodeGenerator.
     */
    @Test
    public void testIsConcave() {
        System.out.println("isConcave");
        assertEquals(false, GcodeGenerator.isConcave(-2.44685437739309, -1.5707963267948966));
        assertEquals(true, GcodeGenerator.isConcave(-0.6947382761967033, -2.44685437739309));
        assertEquals(false, GcodeGenerator.isConcave(-2.44685437739309, -0.6947382761967033));
        assertEquals(true, GcodeGenerator.isConcave(0.4636476090008061, -2.44685437739309));
        assertEquals(true, GcodeGenerator.isConcave(2.5213431676069717, 0.4636476090008061));
        assertEquals(false, GcodeGenerator.isConcave(0.8960553845713439, 2.5213431676069717));
        assertEquals(true, GcodeGenerator.isConcave(1.5707963267948966, 0.8960553845713439));
        assertEquals(false, GcodeGenerator.isConcave(-2.44685437739309, -1.5707963267948966));
        assertEquals(true, GcodeGenerator.isConcave(-0.6947382761967033, -2.44685437739309));
        assertEquals(false, GcodeGenerator.isConcave(-2.44685437739309, -0.6947382761967033));
        assertEquals(true, GcodeGenerator.isConcave(0.4636476090008061, -2.44685437739309));
        assertEquals(true, GcodeGenerator.isConcave(2.5213431676069717, 0.4636476090008061));
        assertEquals(false, GcodeGenerator.isConcave(0.8960553845713439, 2.5213431676069717));
        assertEquals(true, GcodeGenerator.isConcave(1.5707963267948966, 0.8960553845713439));
    }
}
