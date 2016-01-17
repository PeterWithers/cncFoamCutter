/*
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.util;

import java.util.ArrayList;
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
        GcodeGenerator instance = new GcodeGenerator();
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
        GcodeGenerator instance = new GcodeGenerator();
        assertArrayEquals(new double[]{9.575844603750276, 50.90558942122368}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, -0.4380290252990968), 0.0);
        assertArrayEquals(new double[]{9.575844603750276, 50.90558942122368}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, -0.4380290252990968), 0.0);
        assertArrayEquals(new double[]{9.575844603750276, 50.90558942122368}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, -0.4380290252990968), 0.0);
        assertArrayEquals(new double[]{39.23177872040262, 86.64018439966448}, instance.offsetPoint(new double[]{40.0, 86.0}, 1.0, -0.8760580505981936), 0.0);
        assertArrayEquals(new double[]{40.0, 87.0}, instance.offsetPoint(new double[]{40.0, 86.0}, 1.0, 0.0), 0.0);
        assertArrayEquals(new double[]{40.76822127959738, 86.64018439966448}, instance.offsetPoint(new double[]{40.0, 86.0}, 1.0, 0.8760580505981933), 0.0);
        assertArrayEquals(new double[]{100.4168194241156, 13.091010688907764}, instance.offsetPoint(new double[]{100.0, 14.0}, 1.0, 2.711649176784128), 0.0);
        assertArrayEquals(new double[]{100.4168194241156, 13.091010688907764}, instance.offsetPoint(new double[]{100.0, 14.0}, 1.0, 2.711649176784128), 0.0);
        assertArrayEquals(new double[]{100.4168194241156, 13.091010688907764}, instance.offsetPoint(new double[]{100.0, 14.0}, 1.0, 2.711649176784128), 0.0);
        assertArrayEquals(new double[]{93.01360607616786, 49.835601012694646}, instance.offsetPoint(new double[]{94.0, 50.0}, 1.0, 4.547240302970063), 0.0);
        assertArrayEquals(new double[]{94.26945545529667, 49.036987145666856}, instance.offsetPoint(new double[]{94.0, 50.0}, 1.0, 2.868765126326297), 0.0);
        assertArrayEquals(new double[]{94.92847669088526, 50.3713906763541}, instance.offsetPoint(new double[]{94.0, 50.0}, 1.0, 1.1902899496825317), 0.0);
        assertArrayEquals(new double[]{118.92847669088526, -9.628609323645897}, instance.offsetPoint(new double[]{118.0, -10.0}, 1.0, 1.1902899496825317), 0.0);
        assertArrayEquals(new double[]{118.5349446316267, -10.84488711736762}, instance.offsetPoint(new double[]{118.0, -10.0}, 1.0, 2.5771504412718516), 0.0);
        assertArrayEquals(new double[]{117.2672065083737, -10.680451099367279}, instance.offsetPoint(new double[]{118.0, -10.0}, 1.0, 3.9640109328611715), 0.0);
        assertArrayEquals(new double[]{39.92622840975752, 73.00272483612942}, instance.offsetPoint(new double[]{40.0, 74.0}, 1.0, 3.215431322113706), 0.0);
        assertArrayEquals(new double[]{39.92622840975752, 73.00272483612942}, instance.offsetPoint(new double[]{40.0, 74.0}, 1.0, 3.215431322113706), 0.0);
        assertArrayEquals(new double[]{39.92622840975752, 73.00272483612942}, instance.offsetPoint(new double[]{40.0, 74.0}, 1.0, 3.215431322113706), 0.0);
        assertArrayEquals(new double[]{10.624695047554424, 49.21913119055697}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, 2.4668517113662407), 0.0);
        assertArrayEquals(new double[]{10.3310069414355, 49.05637168083958}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, 2.804222182478017), 0.0);
        assertArrayEquals(new double[]{10.0, 49.0}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, 3.141592653589793), 0.0);
        assertArrayEquals(new double[]{9.575844603750276, 50.90558942122368}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, -0.4380290252990968), 0.0);
        assertArrayEquals(new double[]{9.575844603750276, 50.90558942122368}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, -0.4380290252990968), 0.0);
        assertArrayEquals(new double[]{9.575844603750276, 50.90558942122368}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, -0.4380290252990968), 0.0);
        assertArrayEquals(new double[]{39.23177872040262, 86.64018439966448}, instance.offsetPoint(new double[]{40.0, 86.0}, 1.0, -0.8760580505981936), 0.0);
        assertArrayEquals(new double[]{40.0, 87.0}, instance.offsetPoint(new double[]{40.0, 86.0}, 1.0, 0.0), 0.0);
        assertArrayEquals(new double[]{40.76822127959738, 86.64018439966448}, instance.offsetPoint(new double[]{40.0, 86.0}, 1.0, 0.8760580505981933), 0.0);
        assertArrayEquals(new double[]{100.4168194241156, 13.091010688907764}, instance.offsetPoint(new double[]{100.0, 14.0}, 1.0, 2.711649176784128), 0.0);
        assertArrayEquals(new double[]{100.4168194241156, 13.091010688907764}, instance.offsetPoint(new double[]{100.0, 14.0}, 1.0, 2.711649176784128), 0.0);
        assertArrayEquals(new double[]{100.4168194241156, 13.091010688907764}, instance.offsetPoint(new double[]{100.0, 14.0}, 1.0, 2.711649176784128), 0.0);
        assertArrayEquals(new double[]{93.01360607616786, 49.835601012694646}, instance.offsetPoint(new double[]{94.0, 50.0}, 1.0, 4.547240302970063), 0.0);
        assertArrayEquals(new double[]{94.26945545529667, 49.036987145666856}, instance.offsetPoint(new double[]{94.0, 50.0}, 1.0, 2.868765126326297), 0.0);
        assertArrayEquals(new double[]{94.92847669088526, 50.3713906763541}, instance.offsetPoint(new double[]{94.0, 50.0}, 1.0, 1.1902899496825317), 0.0);
        assertArrayEquals(new double[]{118.92847669088526, -9.628609323645897}, instance.offsetPoint(new double[]{118.0, -10.0}, 1.0, 1.1902899496825317), 0.0);
        assertArrayEquals(new double[]{118.5349446316267, -10.84488711736762}, instance.offsetPoint(new double[]{118.0, -10.0}, 1.0, 2.5771504412718516), 0.0);
        assertArrayEquals(new double[]{117.2672065083737, -10.680451099367279}, instance.offsetPoint(new double[]{118.0, -10.0}, 1.0, 3.9640109328611715), 0.0);
        assertArrayEquals(new double[]{39.92622840975752, 73.00272483612942}, instance.offsetPoint(new double[]{40.0, 74.0}, 1.0, 3.215431322113706), 0.0);
        assertArrayEquals(new double[]{39.92622840975752, 73.00272483612942}, instance.offsetPoint(new double[]{40.0, 74.0}, 1.0, 3.215431322113706), 0.0);
        assertArrayEquals(new double[]{39.92622840975752, 73.00272483612942}, instance.offsetPoint(new double[]{40.0, 74.0}, 1.0, 3.215431322113706), 0.0);
        assertArrayEquals(new double[]{10.624695047554424, 49.21913119055697}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, 2.4668517113662407), 0.0);
        assertArrayEquals(new double[]{10.3310069414355, 49.05637168083958}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, 2.804222182478017), 0.0);
        assertArrayEquals(new double[]{10.0, 49.0}, instance.offsetPoint(new double[]{10.0, 50.0}, 1.0, 3.141592653589793), 0.0);
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
        assertEquals(true, GcodeGenerator.isConcave(2.393214606066275, -0.3805063771123649));
        assertEquals(false, GcodeGenerator.isConcave(0.8960553845713439, 2.393214606066275));
        assertEquals(true, GcodeGenerator.isConcave(-0.3805063771123649, 2.976443976175166));
        assertEquals(false, GcodeGenerator.isConcave(2.976443976175166, -0.6947382761967033));
    }

    /**
     * Test of applyCuttingToolOffset method, of class GcodeGenerator.
     */
    @Test
    public void testApplyCuttingToolOffset() {
        System.out.println("applyCuttingToolOffset");
        double cutDepth = 1.0;
        double offsetDistance = 3.0;
        ArrayList<double[]> path = new ArrayList<>();
        ArrayList<double[]> expected = new ArrayList<>();
        GcodeGenerator instance = new GcodeGenerator();
        path.add(new double[]{0, 0});
        path.add(new double[]{1, 1});
        expected.add(new double[]{1.1480502970952693, 2.77163859753386});
        expected.add(new double[]{1.1480502970952693, 2.77163859753386});
        expected.add(new double[]{1.1480502970952693, 2.77163859753386});
        expected.add(new double[]{-1.1213203435596424, 3.121320343559643});
        expected.add(new double[]{2.7714130481031556, 3.4211765348709884});
        expected.add(new double[]{3.663906720075566, -0.3797104720687732});
        expected.add(new double[]{3.204003229385975, -1.2676793787606286});
        expected.add(new double[]{0.6429289591629452, -2.886205969910394});
        expected.add(new double[]{-2.0230198961817725, -1.446953926471341});
        path.add(instance.offsetPoint(new double[]{1, 1}, 1, 123));
//        path.add(instance.offsetPoint(new double[]{1, 1}, 2, 123));
//        path.add(instance.offsetPoint(new double[]{1, 1}, 3, 123));
//        expected.add(instance.offsetPoint(new double[]{1, 1}, 1, 123));
//        expected.add(instance.offsetPoint(new double[]{1, 1}, 2, 123));
//        expected.add(instance.offsetPoint(new double[]{1, 1}, 3, 123));
        ArrayList<double[]> result = instance.applyCuttingToolOffset(cutDepth, offsetDistance, path);
        for (int i = 0; i < result.size(); i++) {
            System.out.println("expected.add(new double[]{" + result.get(i)[0] + ", " + result.get(i)[1] + "});");
            assertEquals(expected.get(i)[0], result.get(i)[0], 0.0);
            assertEquals(expected.get(i)[1], result.get(i)[1], 0.0);
        }
    }
}
