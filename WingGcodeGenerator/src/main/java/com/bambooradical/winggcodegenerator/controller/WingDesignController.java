/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.controller;

import com.bambooradical.winggcodegenerator.dao.AerofoilRepository;
import com.bambooradical.winggcodegenerator.model.AerofoilData;
import com.bambooradical.winggcodegenerator.model.AerofoilDataAG36;
import com.bambooradical.winggcodegenerator.model.Bounds;
import com.bambooradical.winggcodegenerator.model.MachineData;
import com.bambooradical.winggcodegenerator.util.AerofoilDatParser;
import com.bambooradical.winggcodegenerator.util.GcodeGenerator;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @since Aug 17, 2015 8:37:40 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
@Controller
public class WingDesignController {

    @Autowired
    AerofoilRepository aerofoilRepository;

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
            @RequestParam(value = "rootAerofoil", required = false, defaultValue = "1") long rootAerofoil,
            @RequestParam(value = "tipAerofoil", required = false, defaultValue = "1") long tipAerofoil,
            Model model) {
        final int tipGcodeChord = (int) (rootChord + ((((double) tipChord - rootChord) / wingLength) * wireLength));
        final AerofoilData rootAerofoilData;
        final AerofoilData tipAerofoilData;
        if (aerofoilRepository.count() > 0) {
            rootAerofoilData = aerofoilRepository.findOne(rootAerofoil);
            tipAerofoilData = aerofoilRepository.findOne(rootAerofoil); // todo: when the gcode generator can process datafiles of different lenth this can be retured to tip 
        } else {
            rootAerofoilData = new AerofoilDataAG36();
            tipAerofoilData = new AerofoilDataAG36();
        }
        final MachineData machineData = new MachineData(machineDepth, machineHeight, wireLength, verticalAxis1, horizontalAxis1, verticalAxis2, horizontalAxis2, viewAngle, cuttingSpeed, heaterPercent);
        model.addAttribute("aerofoilList", aerofoilRepository.findAll());
        model.addAttribute("machineData", machineData);
        model.addAttribute("initialCutHeight", initialCutHeight);
        model.addAttribute("initialCutLength", initialCutLength);
        model.addAttribute("viewAngle", viewAngle);
        model.addAttribute("rootChord", rootChord);
        model.addAttribute("tipChord", tipChord);
        model.addAttribute("wingLength", wingLength);
        model.addAttribute("aerofoilname", tipAerofoilData.getName());
        model.addAttribute("rootAerofoilData", rootAerofoilData.toSvgPoints(initialCutLength, machineHeight - initialCutHeight, rootChord));
//        final Bounds rootBounds = rootAerofoilData.getSvgBounds();
//        model.addAttribute("rootAerofoilBounds", rootBounds.getMinX() + " " + rootBounds.getMinY() + " " + rootBounds.getWidth() + " " + rootBounds.getHeight());
        float percentOfWire = (float) wingLength / wireLength;
        model.addAttribute("tipAerofoilData", tipAerofoilData.toSvgPoints(initialCutLength + (int) (viewAngle * percentOfWire), (int) ((machineHeight - initialCutHeight) + (wireLength - viewAngle) * (percentOfWire)), tipChord));
        model.addAttribute("wingLinesData", tipAerofoilData.toSvgLines(initialCutLength, machineHeight - initialCutHeight, rootChord, initialCutLength + (int) (viewAngle * percentOfWire), (int) ((machineHeight - initialCutHeight) + (wireLength - viewAngle) * (percentOfWire)), tipChord));
        model.addAttribute("cuttingSpeed", cuttingSpeed);
        model.addAttribute("heaterPercent", heaterPercent);
        final Bounds svgBounds = machineData.getSvgBounds();
        model.addAttribute("svgbounds", svgBounds.getMinX() + " " + svgBounds.getMinY() + " " + svgBounds.getWidth() + " " + svgBounds.getHeight());
        model.addAttribute("diagramScale", diagramScale);
        final GcodeGenerator gcodeGenerator = new GcodeGenerator(rootAerofoilData, rootChord, tipAerofoilData, tipGcodeChord, machineHeight, initialCutHeight, initialCutLength);
        model.addAttribute("gcodeXY", gcodeGenerator.toSvgXy());
        model.addAttribute("gcodeZE", gcodeGenerator.toSvgZe());
        model.addAttribute("transformZE", "translate(" + (int) (viewAngle) + "," + (int) (wireLength - viewAngle) + ")");
        model.addAttribute("gcode", gcodeGenerator.toGcode(machineData));
        return "WingDesignView";
    }

    @RequestMapping(value = "/WingDesignView", method = RequestMethod.POST)
    public String aerofoilImport(@RequestParam("datFile") MultipartFile datFile, Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("diagramScale", 100);
        final AerofoilData uploadedAerofoil = new AerofoilDatParser().parseFile(datFile);
        aerofoilRepository.save(uploadedAerofoil);
        model.addAttribute("rootAerofoilData", uploadedAerofoil.toSvgPoints(0, 1, 100));
        final Bounds rootBounds = uploadedAerofoil.getSvgBounds();
        model.addAttribute("rootAerofoilBounds", rootBounds.getMinX() + " " + rootBounds.getMinY() + " " + rootBounds.getWidth() + " " + rootBounds.getHeight());
//        return "<svg><text>" + uploadedAerofoil.getName() + "</text><polyline points=\"" + uploadedAerofoil.toSvgPoints(0, 0) + "\" />";
//        return "WingDesignView";
//        return "WingDesignView :: rootAerofoilData";
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping(value = "/download",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @ResponseBody
    String downloadFile(
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
            @RequestParam(value = "rootAerofoil", required = false, defaultValue = "1") long rootAerofoil,
            @RequestParam(value = "tipAerofoil", required = false, defaultValue = "1") long tipAerofoil,
            HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment;filename=" + rootAerofoil + "_" + rootChord + "-" + tipChord + "_" + cuttingSpeed + "mms" + heaterPercent + "pwm");
        final int tipGcodeChord = (int) (rootChord + ((((double) tipChord - rootChord) / wingLength) * wireLength));
        final AerofoilData rootAerofoilData;
        final AerofoilData tipAerofoilData;
        if (aerofoilRepository.count() > 0) {
            rootAerofoilData = aerofoilRepository.findOne(rootAerofoil);
            tipAerofoilData = aerofoilRepository.findOne(rootAerofoil); // todo: when the gcode generator can process datafiles of different lenth this can be retured to tip 
        } else {
            rootAerofoilData = new AerofoilDataAG36();
            tipAerofoilData = new AerofoilDataAG36();
        }
        final MachineData machineData = new MachineData(machineDepth, machineHeight, wireLength, verticalAxis1, horizontalAxis1, verticalAxis2, horizontalAxis2, viewAngle, cuttingSpeed, heaterPercent);
        final GcodeGenerator gcodeGenerator = new GcodeGenerator(rootAerofoilData, rootChord, tipAerofoilData, tipGcodeChord, machineHeight, initialCutHeight, initialCutLength);
        return gcodeGenerator.toGcode(machineData);
    }
}
