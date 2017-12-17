/*
 * Copyright (C) 2017 Peter Withers
 */
package com.bambooradical.winggcodegenerator.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @since Dec 17, 2017 18:25 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class LaserGcodeTest {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.io.UnsupportedEncodingException
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
//        for (double distance = 0.1; distance < 1; distance += 0.1) {
//            for (double pause = 0; pause < 1; pause += 0.1) {
        double distance = 1;
        PrintWriter writer = new PrintWriter("distance" + distance + ".gcode", "UTF-8");
        writer.println("M05 S0"); // turn off laser
        writer.println("G90"); // Set to Absolute Positioning
        writer.println("G1Z0"); // linear move
        writer.println("G21"); // Millimeters
        for (double xPos = 0; xPos < 50; xPos += 5) {
            writer.println("G4 P0 "); // dwell
            writer.println("M05 S0"); // turn off laser
            writer.println("G1 F3500"); // set speed
            writer.println("G1  X" + xPos + " Y0"); // move
            writer.println("G4 P0"); // dwell
            double power = 100 + (xPos * 10);
            writer.println("M03 G1Z0 S" + power); // turn on laser at power x
            for (double yPos = 0; yPos < 50; yPos += distance) {
                double speed = 500 + (yPos * 10);
                // start loop
//                        writer.println("G1 F" + speed); // set speed 
                writer.println("G1  X" + xPos + " Y" + yPos + "F" + speed); // move at speed
//                    writer.println("G4 P0 "); // dwell
//                        writer.println("M05 S0"); // turn off laser
//                        writer.println("G4 P" + pause); // pause in ms
                // end loop
            }
        }
        writer.println("G1 F3500"); // set speed
        writer.println("G1 X0 Y0"); // go to home
        writer.println("M30"); // perhaps this can be used as a repeat command
        writer.close();
//        }
//        }
    }
}
