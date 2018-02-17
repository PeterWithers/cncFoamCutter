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
        String expResult = "G0 S0\n"
                + "G90\n"
                + "G21\n"
                + "G0 X0.00 Y0.00 F3500\n"
                + "G4 P0\n"
                + "G0 X0.00 Y45.00 F3500\n"
                + "G4 P0\n"
                + "G0 X47.00 Y45.00 F3500\n"
                + "G4 P0\n"
                + "G0 X47.00 Y0.00 F3500\n"
                + "G4 P0\n"
                + "G0 X0.00 Y0.00 F3500\n"
                + "G4 P0\n"
                + "G0 X0.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G1 S100\n"
                + "G1 X0.000 Y0.000 F500\n"
                + "G1 X2.000 Y5.000 F555\n"
                + "G1 X0.000 Y10.000 F611\n"
                + "G1 X2.000 Y15.000 F666\n"
                + "G1 X0.000 Y20.000 F722\n"
                + "G1 X2.000 Y25.000 F777\n"
                + "G1 X0.000 Y30.000 F833\n"
                + "G1 X2.000 Y35.000 F888\n"
                + "G1 X0.000 Y40.000 F944\n"
                + "G1 X2.000 Y45.000 F1000\n"
                + "G4 P0\n"
                + "G0 X5.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G1 S122\n"
                + "G1 X5.000 Y0.000 F500\n"
                + "G1 X7.000 Y5.000 F555\n"
                + "G1 X5.000 Y10.000 F611\n"
                + "G1 X7.000 Y15.000 F666\n"
                + "G1 X5.000 Y20.000 F722\n"
                + "G1 X7.000 Y25.000 F777\n"
                + "G1 X5.000 Y30.000 F833\n"
                + "G1 X7.000 Y35.000 F888\n"
                + "G1 X5.000 Y40.000 F944\n"
                + "G1 X7.000 Y45.000 F1000\n"
                + "G4 P0\n"
                + "G0 X10.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G1 S144\n"
                + "G1 X10.000 Y0.000 F500\n"
                + "G1 X12.000 Y5.000 F555\n"
                + "G1 X10.000 Y10.000 F611\n"
                + "G1 X12.000 Y15.000 F666\n"
                + "G1 X10.000 Y20.000 F722\n"
                + "G1 X12.000 Y25.000 F777\n"
                + "G1 X10.000 Y30.000 F833\n"
                + "G1 X12.000 Y35.000 F888\n"
                + "G1 X10.000 Y40.000 F944\n"
                + "G1 X12.000 Y45.000 F1000\n"
                + "G4 P0\n"
                + "G0 X15.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G1 S166\n"
                + "G1 X15.000 Y0.000 F500\n"
                + "G1 X17.000 Y5.000 F555\n"
                + "G1 X15.000 Y10.000 F611\n"
                + "G1 X17.000 Y15.000 F666\n"
                + "G1 X15.000 Y20.000 F722\n"
                + "G1 X17.000 Y25.000 F777\n"
                + "G1 X15.000 Y30.000 F833\n"
                + "G1 X17.000 Y35.000 F888\n"
                + "G1 X15.000 Y40.000 F944\n"
                + "G1 X17.000 Y45.000 F1000\n"
                + "G4 P0\n"
                + "G0 X20.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G1 S188\n"
                + "G1 X20.000 Y0.000 F500\n"
                + "G1 X22.000 Y5.000 F555\n"
                + "G1 X20.000 Y10.000 F611\n"
                + "G1 X22.000 Y15.000 F666\n"
                + "G1 X20.000 Y20.000 F722\n"
                + "G1 X22.000 Y25.000 F777\n"
                + "G1 X20.000 Y30.000 F833\n"
                + "G1 X22.000 Y35.000 F888\n"
                + "G1 X20.000 Y40.000 F944\n"
                + "G1 X22.000 Y45.000 F1000\n"
                + "G4 P0\n"
                + "G0 X25.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G1 S211\n"
                + "G1 X25.000 Y0.000 F500\n"
                + "G1 X27.000 Y5.000 F555\n"
                + "G1 X25.000 Y10.000 F611\n"
                + "G1 X27.000 Y15.000 F666\n"
                + "G1 X25.000 Y20.000 F722\n"
                + "G1 X27.000 Y25.000 F777\n"
                + "G1 X25.000 Y30.000 F833\n"
                + "G1 X27.000 Y35.000 F888\n"
                + "G1 X25.000 Y40.000 F944\n"
                + "G1 X27.000 Y45.000 F1000\n"
                + "G4 P0\n"
                + "G0 X30.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G1 S233\n"
                + "G1 X30.000 Y0.000 F500\n"
                + "G1 X32.000 Y5.000 F555\n"
                + "G1 X30.000 Y10.000 F611\n"
                + "G1 X32.000 Y15.000 F666\n"
                + "G1 X30.000 Y20.000 F722\n"
                + "G1 X32.000 Y25.000 F777\n"
                + "G1 X30.000 Y30.000 F833\n"
                + "G1 X32.000 Y35.000 F888\n"
                + "G1 X30.000 Y40.000 F944\n"
                + "G1 X32.000 Y45.000 F1000\n"
                + "G4 P0\n"
                + "G0 X35.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G1 S255\n"
                + "G1 X35.000 Y0.000 F500\n"
                + "G1 X37.000 Y5.000 F555\n"
                + "G1 X35.000 Y10.000 F611\n"
                + "G1 X37.000 Y15.000 F666\n"
                + "G1 X35.000 Y20.000 F722\n"
                + "G1 X37.000 Y25.000 F777\n"
                + "G1 X35.000 Y30.000 F833\n"
                + "G1 X37.000 Y35.000 F888\n"
                + "G1 X35.000 Y40.000 F944\n"
                + "G1 X37.000 Y45.000 F1000\n"
                + "G4 P0\n"
                + "G0 X40.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G1 S277\n"
                + "G1 X40.000 Y0.000 F500\n"
                + "G1 X42.000 Y5.000 F555\n"
                + "G1 X40.000 Y10.000 F611\n"
                + "G1 X42.000 Y15.000 F666\n"
                + "G1 X40.000 Y20.000 F722\n"
                + "G1 X42.000 Y25.000 F777\n"
                + "G1 X40.000 Y30.000 F833\n"
                + "G1 X42.000 Y35.000 F888\n"
                + "G1 X40.000 Y40.000 F944\n"
                + "G1 X42.000 Y45.000 F1000\n"
                + "G4 P0\n"
                + "G0 X45.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G1 S300\n"
                + "G1 X45.000 Y0.000 F500\n"
                + "G1 X47.000 Y5.000 F555\n"
                + "G1 X45.000 Y10.000 F611\n"
                + "G1 X47.000 Y15.000 F666\n"
                + "G1 X45.000 Y20.000 F722\n"
                + "G1 X47.000 Y25.000 F777\n"
                + "G1 X45.000 Y30.000 F833\n"
                + "G1 X47.000 Y35.000 F888\n"
                + "G1 X45.000 Y40.000 F944\n"
                + "G1 X47.000 Y45.000 F1000\n"
                + "G4 P0 \n"
                + "G1 S0\n"
                + "G1 F3500\n"
                + "G0 X0 Y0\n";
        String result = instance.getGcode();
        System.out.println("MinPower" + instance.getMinPower());
        System.out.println("MaxPower" + instance.getMaxPower());
        System.out.println("MinSpeed" + instance.getMinSpeed());
        System.out.println("MaxSpeed" + instance.getMaxSpeed());
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getGcode method, of class LaserTestGcodeData with aerofoil.
     */
    @Test
    public void testGetGcodeWithAerofoil() {
        System.out.println("getGcode");
        LaserTestGcodeData instance = new LaserTestGcodeData();
        instance.setChordLength(10);
        final AerofoilData aerofoilData = new AerofoilData();
        instance.setAerofoilData(aerofoilData);
        aerofoilData.setPoints(new double[][]{
            {1.000003, 0.002671},
            {0.3, 0.003641},
            {0.2, 0.1}
        });
        String expResult = "G0 S0\n"
                + "G90\n"
                + "G21\n"
                + "G0 X0.00 Y0.00 F3500\n"
                + "G4 P0\n"
                + "G0 X0.00 Y51.00 F3500\n"
                + "G4 P0\n"
                + "G0 X55.00 Y51.00 F3500\n"
                + "G4 P0\n"
                + "G0 X55.00 Y0.00 F3500\n"
                + "G4 P0\n"
                + "G0 X0.00 Y0.00 F3500\n"
                + "G4 P0\n"
                + "G0 X0.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G0 X10.00 Y1.00 F3500\n"
                + "G4 P0\n"
                + "G1 S100\n"
                + "G1 X10.000 Y1.000 F500\n"
                + "G1 X2.00 Y2.00\n"
                + "G1 X3.00 Y1.04\n"
                + "G1 X10.00 Y1.03\n"
                + "G4 P0\n"
                + "G0 X10.00 Y8.00 F3500\n"
                + "G4 P0\n"
                + "G1 S100\n"
                + "G1 X10.000 Y8.000 F577\n"
                + "G1 X2.00 Y9.00\n"
                + "G1 X3.00 Y8.04\n"
                + "G1 X10.00 Y8.03\n"
                + "G4 P0\n"
                + "G0 X10.00 Y15.00 F3500\n"
                + "G4 P0\n"
                + "G1 S100\n"
                + "G1 X10.000 Y15.000 F655\n"
                + "G1 X2.00 Y16.00\n"
                + "G1 X3.00 Y15.04\n"
                + "G1 X10.00 Y15.03\n"
                + "G4 P0\n"
                + "G0 X10.00 Y22.00 F3500\n"
                + "G4 P0\n"
                + "G1 S100\n"
                + "G1 X10.000 Y22.000 F733\n"
                + "G1 X2.00 Y23.00\n"
                + "G1 X3.00 Y22.04\n"
                + "G1 X10.00 Y22.03\n"
                + "G4 P0\n"
                + "G0 X10.00 Y29.00 F3500\n"
                + "G4 P0\n"
                + "G1 S100\n"
                + "G1 X10.000 Y29.000 F811\n"
                + "G1 X2.00 Y30.00\n"
                + "G1 X3.00 Y29.04\n"
                + "G1 X10.00 Y29.03\n"
                + "G4 P0\n"
                + "G0 X10.00 Y36.00 F3500\n"
                + "G4 P0\n"
                + "G1 S100\n"
                + "G1 X10.000 Y36.000 F888\n"
                + "G1 X2.00 Y37.00\n"
                + "G1 X3.00 Y36.04\n"
                + "G1 X10.00 Y36.03\n"
                + "G4 P0\n"
                + "G0 X10.00 Y43.00 F3500\n"
                + "G4 P0\n"
                + "G1 S100\n"
                + "G1 X10.000 Y43.000 F966\n"
                + "G1 X2.00 Y44.00\n"
                + "G1 X3.00 Y43.04\n"
                + "G1 X10.00 Y43.03\n"
                + "G4 P0\n"
                + "G0 X10.00 Y50.00 F3500\n"
                + "G4 P0\n"
                + "G1 S100\n"
                + "G1 X10.000 Y50.000 F1044\n"
                + "G1 X2.00 Y51.00\n"
                + "G1 X3.00 Y50.04\n"
                + "G1 X10.00 Y50.03\n"
                + "G4 P0\n"
                + "G0 X15.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G0 X25.00 Y1.00 F3500\n"
                + "G4 P0\n"
                + "G1 S166\n"
                + "G1 X25.000 Y1.000 F500\n"
                + "G1 X17.00 Y2.00\n"
                + "G1 X18.00 Y1.04\n"
                + "G1 X25.00 Y1.03\n"
                + "G4 P0\n"
                + "G0 X25.00 Y8.00 F3500\n"
                + "G4 P0\n"
                + "G1 S166\n"
                + "G1 X25.000 Y8.000 F577\n"
                + "G1 X17.00 Y9.00\n"
                + "G1 X18.00 Y8.04\n"
                + "G1 X25.00 Y8.03\n"
                + "G4 P0\n"
                + "G0 X25.00 Y15.00 F3500\n"
                + "G4 P0\n"
                + "G1 S166\n"
                + "G1 X25.000 Y15.000 F655\n"
                + "G1 X17.00 Y16.00\n"
                + "G1 X18.00 Y15.04\n"
                + "G1 X25.00 Y15.03\n"
                + "G4 P0\n"
                + "G0 X25.00 Y22.00 F3500\n"
                + "G4 P0\n"
                + "G1 S166\n"
                + "G1 X25.000 Y22.000 F733\n"
                + "G1 X17.00 Y23.00\n"
                + "G1 X18.00 Y22.04\n"
                + "G1 X25.00 Y22.03\n"
                + "G4 P0\n"
                + "G0 X25.00 Y29.00 F3500\n"
                + "G4 P0\n"
                + "G1 S166\n"
                + "G1 X25.000 Y29.000 F811\n"
                + "G1 X17.00 Y30.00\n"
                + "G1 X18.00 Y29.04\n"
                + "G1 X25.00 Y29.03\n"
                + "G4 P0\n"
                + "G0 X25.00 Y36.00 F3500\n"
                + "G4 P0\n"
                + "G1 S166\n"
                + "G1 X25.000 Y36.000 F888\n"
                + "G1 X17.00 Y37.00\n"
                + "G1 X18.00 Y36.04\n"
                + "G1 X25.00 Y36.03\n"
                + "G4 P0\n"
                + "G0 X25.00 Y43.00 F3500\n"
                + "G4 P0\n"
                + "G1 S166\n"
                + "G1 X25.000 Y43.000 F966\n"
                + "G1 X17.00 Y44.00\n"
                + "G1 X18.00 Y43.04\n"
                + "G1 X25.00 Y43.03\n"
                + "G4 P0\n"
                + "G0 X25.00 Y50.00 F3500\n"
                + "G4 P0\n"
                + "G1 S166\n"
                + "G1 X25.000 Y50.000 F1044\n"
                + "G1 X17.00 Y51.00\n"
                + "G1 X18.00 Y50.04\n"
                + "G1 X25.00 Y50.03\n"
                + "G4 P0\n"
                + "G0 X30.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G0 X40.00 Y1.00 F3500\n"
                + "G4 P0\n"
                + "G1 S233\n"
                + "G1 X40.000 Y1.000 F500\n"
                + "G1 X32.00 Y2.00\n"
                + "G1 X33.00 Y1.04\n"
                + "G1 X40.00 Y1.03\n"
                + "G4 P0\n"
                + "G0 X40.00 Y8.00 F3500\n"
                + "G4 P0\n"
                + "G1 S233\n"
                + "G1 X40.000 Y8.000 F577\n"
                + "G1 X32.00 Y9.00\n"
                + "G1 X33.00 Y8.04\n"
                + "G1 X40.00 Y8.03\n"
                + "G4 P0\n"
                + "G0 X40.00 Y15.00 F3500\n"
                + "G4 P0\n"
                + "G1 S233\n"
                + "G1 X40.000 Y15.000 F655\n"
                + "G1 X32.00 Y16.00\n"
                + "G1 X33.00 Y15.04\n"
                + "G1 X40.00 Y15.03\n"
                + "G4 P0\n"
                + "G0 X40.00 Y22.00 F3500\n"
                + "G4 P0\n"
                + "G1 S233\n"
                + "G1 X40.000 Y22.000 F733\n"
                + "G1 X32.00 Y23.00\n"
                + "G1 X33.00 Y22.04\n"
                + "G1 X40.00 Y22.03\n"
                + "G4 P0\n"
                + "G0 X40.00 Y29.00 F3500\n"
                + "G4 P0\n"
                + "G1 S233\n"
                + "G1 X40.000 Y29.000 F811\n"
                + "G1 X32.00 Y30.00\n"
                + "G1 X33.00 Y29.04\n"
                + "G1 X40.00 Y29.03\n"
                + "G4 P0\n"
                + "G0 X40.00 Y36.00 F3500\n"
                + "G4 P0\n"
                + "G1 S233\n"
                + "G1 X40.000 Y36.000 F888\n"
                + "G1 X32.00 Y37.00\n"
                + "G1 X33.00 Y36.04\n"
                + "G1 X40.00 Y36.03\n"
                + "G4 P0\n"
                + "G0 X40.00 Y43.00 F3500\n"
                + "G4 P0\n"
                + "G1 S233\n"
                + "G1 X40.000 Y43.000 F966\n"
                + "G1 X32.00 Y44.00\n"
                + "G1 X33.00 Y43.04\n"
                + "G1 X40.00 Y43.03\n"
                + "G4 P0\n"
                + "G0 X40.00 Y50.00 F3500\n"
                + "G4 P0\n"
                + "G1 S233\n"
                + "G1 X40.000 Y50.000 F1044\n"
                + "G1 X32.00 Y51.00\n"
                + "G1 X33.00 Y50.04\n"
                + "G1 X40.00 Y50.03\n"
                + "G4 P0\n"
                + "G0 X45.000 Y0 F3500\n"
                + "G4 P0\n"
                + "G0 X55.00 Y1.00 F3500\n"
                + "G4 P0\n"
                + "G1 S300\n"
                + "G1 X55.000 Y1.000 F500\n"
                + "G1 X47.00 Y2.00\n"
                + "G1 X48.00 Y1.04\n"
                + "G1 X55.00 Y1.03\n"
                + "G4 P0\n"
                + "G0 X55.00 Y8.00 F3500\n"
                + "G4 P0\n"
                + "G1 S300\n"
                + "G1 X55.000 Y8.000 F577\n"
                + "G1 X47.00 Y9.00\n"
                + "G1 X48.00 Y8.04\n"
                + "G1 X55.00 Y8.03\n"
                + "G4 P0\n"
                + "G0 X55.00 Y15.00 F3500\n"
                + "G4 P0\n"
                + "G1 S300\n"
                + "G1 X55.000 Y15.000 F655\n"
                + "G1 X47.00 Y16.00\n"
                + "G1 X48.00 Y15.04\n"
                + "G1 X55.00 Y15.03\n"
                + "G4 P0\n"
                + "G0 X55.00 Y22.00 F3500\n"
                + "G4 P0\n"
                + "G1 S300\n"
                + "G1 X55.000 Y22.000 F733\n"
                + "G1 X47.00 Y23.00\n"
                + "G1 X48.00 Y22.04\n"
                + "G1 X55.00 Y22.03\n"
                + "G4 P0\n"
                + "G0 X55.00 Y29.00 F3500\n"
                + "G4 P0\n"
                + "G1 S300\n"
                + "G1 X55.000 Y29.000 F811\n"
                + "G1 X47.00 Y30.00\n"
                + "G1 X48.00 Y29.04\n"
                + "G1 X55.00 Y29.03\n"
                + "G4 P0\n"
                + "G0 X55.00 Y36.00 F3500\n"
                + "G4 P0\n"
                + "G1 S300\n"
                + "G1 X55.000 Y36.000 F888\n"
                + "G1 X47.00 Y37.00\n"
                + "G1 X48.00 Y36.04\n"
                + "G1 X55.00 Y36.03\n"
                + "G4 P0\n"
                + "G0 X55.00 Y43.00 F3500\n"
                + "G4 P0\n"
                + "G1 S300\n"
                + "G1 X55.000 Y43.000 F966\n"
                + "G1 X47.00 Y44.00\n"
                + "G1 X48.00 Y43.04\n"
                + "G1 X55.00 Y43.03\n"
                + "G4 P0\n"
                + "G0 X55.00 Y50.00 F3500\n"
                + "G4 P0\n"
                + "G1 S300\n"
                + "G1 X55.000 Y50.000 F1044\n"
                + "G1 X47.00 Y51.00\n"
                + "G1 X48.00 Y50.04\n"
                + "G1 X55.00 Y50.03\n"
                + "G4 P0 \n"
                + "G1 S0\n"
                + "G1 F3500\n"
                + "G0 X0 Y0\n";
        String result = instance.getGcode();
        System.out.println("MinPower" + instance.getMinPower());
        System.out.println("MaxPower" + instance.getMaxPower());
        System.out.println("MinSpeed" + instance.getMinSpeed());
        System.out.println("MaxSpeed" + instance.getMaxSpeed());
        System.out.println(result);
        assertEquals(expResult, result);
    }
}
