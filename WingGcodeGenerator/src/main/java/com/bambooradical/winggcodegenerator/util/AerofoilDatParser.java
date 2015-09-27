/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.util;

import com.bambooradical.winggcodegenerator.model.AerofoilData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.springframework.web.multipart.MultipartFile;

/**
 * @since Sep 25, 2015 21:39:38 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public class AerofoilDatParser {

    public AerofoilData parseFile(final MultipartFile datFile) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(datFile.getInputStream()));
        final String aerofoilName = reader.readLine();
        final ArrayList<double[]> dataPoints = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            final String[] splitLine = line.trim().split("[^0-9\\.-]+");
            if (splitLine.length != 2) {
                break;
            }
            final double xPoint = Double.parseDouble(splitLine[0]);
            final double yPoint = Double.parseDouble(splitLine[1]);
            dataPoints.add(new double[]{xPoint, yPoint});
        }
        return new AerofoilData(aerofoilName, dataPoints.toArray(new double[][]{}));
    }
}
