/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.controller;

import com.bambooradical.winggcodegenerator.model.AerofoilDataAG36;
import com.bambooradical.winggcodegenerator.model.Bounds;
import com.bambooradical.winggcodegenerator.model.MachineData;
import com.bambooradical.winggcodegenerator.util.GcodeGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @since Aug 17, 2015 8:37:40 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
@Controller
public class WingDesignController {

    @RequestMapping("/WingDesignView")
    public String designView(
            @RequestParam(value = "machineDepth", required = false, defaultValue = "350") int machineDepth,
            @RequestParam(value = "machineHeight", required = false, defaultValue = "100") int machineHeight,
            @RequestParam(value = "wireLength", required = false, defaultValue = "600") int wireLength,
            @RequestParam(value = "initialCutHeight", required = false, defaultValue = "50") int initialCutHeight,
            @RequestParam(value = "initialCutLength", required = false, defaultValue = "10") int initialCutLength,
            @RequestParam(value = "viewAngle", required = false, defaultValue = "500") int viewAngle,
            @RequestParam(value = "rootChord", required = false, defaultValue = "120") int rootChord,
            @RequestParam(value = "tipChord", required = false, defaultValue = "120") int tipChord,
            @RequestParam(value = "wingLength", required = false, defaultValue = "120") int wingLength,
            @RequestParam(value = "diagramScale", required = false, defaultValue = "100") int diagramScale,
            @RequestParam(value = "cuttingSpeed", required = false, defaultValue = "250") int cuttingSpeed,
            @RequestParam(value = "heaterPercent", required = false, defaultValue = "100") int heaterPercent,
            @RequestParam(value = "verticalAxis1", required = false, defaultValue = "Y") char verticalAxis1,
            @RequestParam(value = "horizontalAxis1", required = false, defaultValue = "X") char horizontalAxis1,
            @RequestParam(value = "verticalAxis2", required = false, defaultValue = "Z") char verticalAxis2,
            @RequestParam(value = "horizontalAxis2", required = false, defaultValue = "E") char horizontalAxis2,
            Model model) {
        final AerofoilDataAG36 tipGcodeAerofoilData = new AerofoilDataAG36((int) (rootChord + ((((double) tipChord - rootChord) / wingLength) * wireLength)));
        final AerofoilDataAG36 tipAerofoilData = new AerofoilDataAG36(tipChord);
        final AerofoilDataAG36 rootAerofoilData = new AerofoilDataAG36(rootChord);
        final MachineData machineData = new MachineData(machineDepth, machineHeight, wireLength, verticalAxis1, horizontalAxis1, verticalAxis2, horizontalAxis2, viewAngle, cuttingSpeed, heaterPercent);
        model.addAttribute("machineData", machineData);
        model.addAttribute("initialCutHeight", initialCutHeight);
        model.addAttribute("initialCutLength", initialCutLength);
        model.addAttribute("viewAngle", viewAngle);
        model.addAttribute("rootChord", rootAerofoilData.getChord());
        model.addAttribute("tipChord", tipAerofoilData.getChord());
        model.addAttribute("wingLength", wingLength);
        model.addAttribute("aerofoilname", tipAerofoilData.getName());
        model.addAttribute("rootAerofoilData", rootAerofoilData.toSvgPoints(initialCutLength, machineHeight - initialCutHeight));
        float percentOfWire = (float) wingLength / wireLength;
        model.addAttribute("tipAerofoilData", tipAerofoilData.toSvgPoints(initialCutLength + (int) (viewAngle * percentOfWire), (int) ((machineHeight - initialCutHeight) + (wireLength - viewAngle) * (percentOfWire))));
        model.addAttribute("cuttingSpeed", cuttingSpeed);
        model.addAttribute("heaterPercent", heaterPercent);
        final Bounds svgBounds = machineData.getSvgBounds();
        model.addAttribute("svgbounds", svgBounds.getMinX() + " " + svgBounds.getMinY() + " " + svgBounds.getWidth() + " " + svgBounds.getHeight());
        model.addAttribute("diagramScale", diagramScale);
        final GcodeGenerator gcodeGenerator = new GcodeGenerator(rootAerofoilData, tipGcodeAerofoilData, machineHeight, initialCutHeight, initialCutLength);
        model.addAttribute("gcodeXY", gcodeGenerator.toSvgXy());
        model.addAttribute("gcodeZE", gcodeGenerator.toSvgZe());
        model.addAttribute("transformZE", "translate(" + (int) (viewAngle) + "," + (int) (wireLength - viewAngle) + ")");
        model.addAttribute("gcode", gcodeGenerator.toGcode(machineData));
        return "WingDesignView";
    }
}
