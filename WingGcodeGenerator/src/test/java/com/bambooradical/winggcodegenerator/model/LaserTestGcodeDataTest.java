/**
 * Copyright (C) 2018 Peter Withers
 */
package com.bambooradical.winggcodegenerator.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since Jan 25, 2018 21:44:30 PM (creation date)
 * @author Peter Withers <peter-gthb@bambooradical.com>
 */
public class LaserTestGcodeDataTest {

    /**
     * Test of getGcode method, of class LaserTestGcodeData.
     */
    @Test
    public void testGetGcode() {
        System.out.println("getGcode");
        LaserTestGcodeData instance = new LaserTestGcodeData();
        String expResult = "";
        String result = instance.getGcode();
        System.out.println("MinPower" + instance.getMinPower());
        System.out.println("MaxPower" + instance.getMaxPower());
        System.out.println("MinSpeed" + instance.getMinSpeed());
        System.out.println("MaxSpeed" + instance.getMaxSpeed());
        System.out.println(result);
        assertEquals(expResult, result);
    }
}
