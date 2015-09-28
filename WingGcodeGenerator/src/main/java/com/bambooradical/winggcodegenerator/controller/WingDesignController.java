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
import org.springframework.web.bind.annotation.ModelAttribute;
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
            @ModelAttribute MachineData machineData,
            @RequestParam(value = "rootChord", required = false, defaultValue = "120") int rootChord,
            @RequestParam(value = "tipChord", required = false, defaultValue = "120") int tipChord,
            @RequestParam(value = "wingLength", required = false, defaultValue = "120") int wingLength,
            @RequestParam(value = "rootAerofoil", required = false, defaultValue = "1") long rootAerofoil,
            @RequestParam(value = "tipAerofoil", required = false, defaultValue = "1") long tipAerofoil,
            Model model) {
        final int tipGcodeChord = (int) (rootChord + ((((double) tipChord - rootChord) / wingLength) * machineData.getWireLength()));
        final AerofoilData rootAerofoilData;
        final AerofoilData tipAerofoilData;
        if (aerofoilRepository.count() > 0) {
            rootAerofoilData = aerofoilRepository.findOne(rootAerofoil);
            tipAerofoilData = aerofoilRepository.findOne(rootAerofoil); // todo: when the gcode generator can process datafiles of different lenth this can be retured to tip 
        } else {
            rootAerofoilData = new AerofoilDataAG36();
            tipAerofoilData = new AerofoilDataAG36();
        }
        model.addAttribute("aerofoilList", aerofoilRepository.findAll());
        model.addAttribute("rootChord", rootChord);
        model.addAttribute("tipChord", tipChord);
        model.addAttribute("wingLength", wingLength);
        model.addAttribute("aerofoilname", tipAerofoilData.getName());
        float percentOfWire = (float) wingLength / machineData.getWireLength();
        model.addAttribute("tipAerofoilData", tipAerofoilData.toSvgPoints(machineData.getInitialCutLength() + (int) (machineData.getViewAngle() * percentOfWire), (int) ((machineData.getMachineHeight() - machineData.getInitialCutHeight()) + (machineData.getWireLength() - machineData.getViewAngle()) * (percentOfWire)), tipChord));
        model.addAttribute("wingLinesData", tipAerofoilData.toSvgLines(machineData.getInitialCutLength(), machineData.getMachineHeight() - machineData.getInitialCutHeight(), rootChord, machineData.getInitialCutLength() + (int) (machineData.getViewAngle() * percentOfWire), (int) ((machineData.getMachineHeight() - machineData.getInitialCutHeight()) + (machineData.getWireLength() - machineData.getViewAngle()) * (percentOfWire)), tipChord));
        final Bounds svgBounds = machineData.getSvgBounds();
        model.addAttribute("svgbounds", svgBounds.getMinX() + " " + svgBounds.getMinY() + " " + svgBounds.getWidth() + " " + svgBounds.getHeight());
        final GcodeGenerator gcodeGenerator = new GcodeGenerator(rootAerofoilData, rootChord, tipAerofoilData, tipGcodeChord, machineData.getMachineHeight(), machineData.getInitialCutHeight(), machineData.getInitialCutLength());
        model.addAttribute("gcodeXY", gcodeGenerator.toSvgXy());
        model.addAttribute("gcodeZE", gcodeGenerator.toSvgZe());
        model.addAttribute("transformZE", "translate(" + (int) (machineData.getViewAngle()) + "," + (int) (machineData.getWireLength() - machineData.getViewAngle()) + ")");
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
            @ModelAttribute MachineData machineData,
            @RequestParam(value = "rootChord", required = false, defaultValue = "120") int rootChord,
            @RequestParam(value = "tipChord", required = false, defaultValue = "120") int tipChord,
            @RequestParam(value = "wingLength", required = false, defaultValue = "120") int wingLength,
            @RequestParam(value = "rootAerofoil", required = false, defaultValue = "1") long rootAerofoil,
            @RequestParam(value = "tipAerofoil", required = false, defaultValue = "1") long tipAerofoil,
            HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment;filename=" + rootAerofoil + "_" + rootChord + "-" + tipChord + "_" + machineData.getCuttingSpeed() + "mms" + machineData.getHeaterPercent() + "pwm");
        final int tipGcodeChord = (int) (rootChord + ((((double) tipChord - rootChord) / wingLength) * machineData.getWireLength()));
        final AerofoilData rootAerofoilData;
        final AerofoilData tipAerofoilData;
        if (aerofoilRepository.count() > 0) {
            rootAerofoilData = aerofoilRepository.findOne(rootAerofoil);
            tipAerofoilData = aerofoilRepository.findOne(rootAerofoil); // todo: when the gcode generator can process datafiles of different lenth this can be retured to tip 
        } else {
            rootAerofoilData = new AerofoilDataAG36();
            tipAerofoilData = new AerofoilDataAG36();
        }
        final GcodeGenerator gcodeGenerator = new GcodeGenerator(rootAerofoilData, rootChord, tipAerofoilData, tipGcodeChord, machineData.getMachineHeight(), machineData.getInitialCutHeight(), machineData.getInitialCutLength());
        return gcodeGenerator.toGcode(machineData);
    }
}
