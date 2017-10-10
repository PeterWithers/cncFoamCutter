/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.controller;

import com.bambooradical.winggcodegenerator.dao.AccessDataRepository;
import com.bambooradical.winggcodegenerator.dao.AccessDataService;
import com.bambooradical.winggcodegenerator.dao.AerofoilRepository;
import com.bambooradical.winggcodegenerator.model.AccessData;
import com.bambooradical.winggcodegenerator.model.AerofoilData;
import com.bambooradical.winggcodegenerator.model.AerofoilDataAG36;
import com.bambooradical.winggcodegenerator.model.BedAlignment;
import com.bambooradical.winggcodegenerator.model.Bounds;
import com.bambooradical.winggcodegenerator.model.MachineData;
import com.bambooradical.winggcodegenerator.model.WingData;
import com.bambooradical.winggcodegenerator.util.AerofoilDatParser;
import com.bambooradical.winggcodegenerator.util.GcodeGenerator;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @Autowired
    AccessDataRepository accessDataRepository;
    @Autowired
    AccessDataService accessDataService;

//    @RequestMapping("/update")
//    public String update() {
//        int updateCount = 0;
//        for (AerofoilData aerofoilData : aerofoilRepository.findAll()) {
//            if (aerofoilData.isBezier() == null) {
//                aerofoilData.setBezier(false);
//                updateCount++;
//                aerofoilRepository.save(aerofoilData);
//            }
//            if (aerofoilData.isEditable() == null) {
//                aerofoilData.setEditable(false);
//                updateCount++;
//                aerofoilRepository.save(aerofoilData);
//            }
//        }
//        return "updated: " + updateCount;
//    }
    @RequestMapping("/WingDesignView")
    public String designView(
            @ModelAttribute MachineData machineData,
            @ModelAttribute WingData wingData,
            @RequestParam(value = "bedAlignment", required = true, defaultValue = "0") final int bedAlignment,
            Model model,
            @RequestHeader("Accept-Language") String acceptLang,
            @RequestHeader("User-Agent") String userAgent,
            HttpServletRequest request) {
        final String remoteAddr = request.getRemoteAddr();
        final String requestURI = request.getRequestURI();
        final Date accessDate = new java.util.Date();
        model.addAttribute("bedAlignment", bedAlignment);
        final BedAlignment bedAlignmentCalculator = new BedAlignment(bedAlignment, machineData, wingData);
        final AccessData accessData = new AccessData(accessDate, remoteAddr, userAgent, acceptLang, requestURI);
        accessDataRepository.save(accessData);
        accessDataService.storeAccessData(accessData);
        final AerofoilData rootAerofoilData;
        final AerofoilData tipAerofoilData;
        if (aerofoilRepository.exists(wingData.getRootAerofoil())) {
            rootAerofoilData = aerofoilRepository.findOne(wingData.getRootAerofoil());
        } else {
            rootAerofoilData = new AerofoilDataAG36();
        }
        if (aerofoilRepository.exists(wingData.getTipAerofoil())) {
            final AerofoilData tempAerofoilData = aerofoilRepository.findOne(wingData.getTipAerofoil()); // todo: when the gcode generator can process datafiles of different lenth this can be retured to tip 
            if (rootAerofoilData.isBezier() == tempAerofoilData.isBezier() && rootAerofoilData.getPoints().length == tempAerofoilData.getPoints().length) {
                tipAerofoilData = tempAerofoilData;
            } else {
                tipAerofoilData = rootAerofoilData;
            }
        } else {
            tipAerofoilData = rootAerofoilData;
        }
        model.addAttribute("aerofoilList", aerofoilRepository.findAll());
        model.addAttribute("rootAerofoilData", rootAerofoilData);
        model.addAttribute("tipAerofoilData", tipAerofoilData);
        model.addAttribute("tipAerofoilSvg", tipAerofoilData.toSvgPoints(bedAlignmentCalculator.getProjectedTipXOffset(), bedAlignmentCalculator.getProjectedTipYOffset(), wingData.getTipChord(), bedAlignmentCalculator.getTipSweep(), bedAlignmentCalculator.getTipWash()));
        model.addAttribute("rootAerofoilSvg", rootAerofoilData.toSvgPoints(bedAlignmentCalculator.getProjectedRootXOffset(), bedAlignmentCalculator.getProjectedRootYOffset(), wingData.getRootChord(), bedAlignmentCalculator.getRootSweep(), bedAlignmentCalculator.getRootWash()));
        model.addAttribute("wingLinesData", rootAerofoilData.toSvgLines(tipAerofoilData, bedAlignmentCalculator.getProjectedRootXOffset(), bedAlignmentCalculator.getProjectedRootYOffset(), wingData.getRootChord(), bedAlignmentCalculator.getRootSweep(), bedAlignmentCalculator.getRootWash(), bedAlignmentCalculator.getProjectedTipXOffset(), bedAlignmentCalculator.getProjectedTipYOffset(), wingData.getTipChord(), bedAlignmentCalculator.getTipSweep(), bedAlignmentCalculator.getTipWash()));
        model.addAttribute("tipAerofoilSvgSecondPart", rootAerofoilData.toSvgPoints(bedAlignmentCalculator.getProjectedRootXOffset(), bedAlignmentCalculator.getProjectedRootYOffset(), wingData.getRootChord(), bedAlignmentCalculator.getRootSweep(), bedAlignmentCalculator.getRootWash()));
        model.addAttribute("rootAerofoilSvgSecondPart", tipAerofoilData.toSvgPoints(bedAlignmentCalculator.getProjectedTipXOffset(), bedAlignmentCalculator.getProjectedTipYOffset(), wingData.getTipChord(), bedAlignmentCalculator.getTipSweep(), bedAlignmentCalculator.getTipWash()));
        model.addAttribute("wingLinesDataSecondPart", tipAerofoilData.toSvgLines(rootAerofoilData, bedAlignmentCalculator.getProjectedTipXOffset(), bedAlignmentCalculator.getProjectedTipYOffset(), wingData.getTipChord(), bedAlignmentCalculator.getTipSweep(), bedAlignmentCalculator.getTipWash(), bedAlignmentCalculator.getProjectedRootXOffset(), bedAlignmentCalculator.getProjectedRootYOffset(), wingData.getRootChord(), bedAlignmentCalculator.getRootSweep(), bedAlignmentCalculator.getRootWash()));
        final Bounds svgBounds = machineData.getSvgBounds();
        model.addAttribute("svgbounds", svgBounds.getMinX() + " " + svgBounds.getMinY() + " " + svgBounds.getWidth() + " " + svgBounds.getHeight());
        final GcodeGenerator gcodeGenerator = new GcodeGenerator(machineData, rootAerofoilData, bedAlignmentCalculator, tipAerofoilData, wingData.isCutTwoMirrored());
        model.addAttribute("gcodeGenerator", gcodeGenerator);
        model.addAttribute("transformZE", "translate(" + (int) (machineData.getViewAngle()) + "," + (int) (machineData.getWireLength() - machineData.getViewAngle()) + ")");
        model.addAttribute("gcode", gcodeGenerator.toGcode(machineData, requestURI));
        return "WingDesignView";
    }

    @RequestMapping(value = "/WingDesignView", method = RequestMethod.POST)
    public String aerofoilImport(@RequestParam("datFile") MultipartFile datFile, Model model, HttpServletRequest request) throws IOException {
        final AerofoilData uploadedAerofoil = new AerofoilDatParser().parseFile(datFile);
        uploadedAerofoil.setAccessDate(new java.util.Date());
        uploadedAerofoil.setRemoteAddress(request.getRequestURI());
        aerofoilRepository.save(uploadedAerofoil);
//        model.addAttribute("rootAerofoilData", uploadedAerofoil.toSvgPoints(0, 1, 100, 0));
//        return "<svg><text>" + uploadedAerofoil.getName() + "</text><polyline points=\"" + uploadedAerofoil.toSvgPoints(0, 0) + "\" />";
//        return "WingDesignView";
//        return "WingDesignView :: rootAerofoilData";
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping(value = "/saveAerofoil", method = RequestMethod.POST)
    public String aerofoilSave(@RequestParam("datText") String datText,
            @RequestParam(value = "name", required = true) final String aerofoilName,
            @RequestParam(value = "id", required = false, defaultValue = "-1") final long id,
            @RequestParam(value = "isBezier", required = false, defaultValue = "false") final boolean isBezier,
            @RequestParam(value = "isEditable", required = false, defaultValue = "false") final boolean isEditable,
            @RequestParam(value = "saveCopy", required = false, defaultValue = "false") final boolean saveCopy,
            HttpServletRequest request) throws IOException {
        final AerofoilData uploadedAerofoil = new AerofoilDatParser().parseString(datText, aerofoilName);
        uploadedAerofoil.setAccessDate(new java.util.Date());
        uploadedAerofoil.setRemoteAddress(request.getRequestURI());
        uploadedAerofoil.setEditable(isEditable);
        uploadedAerofoil.setBezier(isBezier);
        aerofoilRepository.save(uploadedAerofoil);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping(value = "/deleteAerofoil", method = RequestMethod.POST)
    public String aerofoilDelete(@RequestParam(value = "id", required = false, defaultValue = "-1") final long id,
            HttpServletRequest request) throws IOException {
        final AerofoilData findOne = aerofoilRepository.findOne(id);
        if (findOne.isEditable()) {
            aerofoilRepository.delete(findOne);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping(value = "/downloadGcode",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @ResponseBody
    String downloadFile(
            @ModelAttribute MachineData machineData,
            @ModelAttribute WingData wingData,
            @RequestParam(value = "bedAlignment", required = true, defaultValue = "0") final int bedAlignment,
            HttpServletResponse response,
            HttpServletRequest request) {
        response.setHeader("Content-Disposition", "attachment;filename=" + wingData.getRootAerofoil() + "_" + wingData.getRootChord() + "-" + wingData.getTipChord() + "_" + machineData.getCuttingSpeed() + "f" + machineData.getHeaterPercent() + "h");
        final BedAlignment bedAlignmentCalculator = new BedAlignment(bedAlignment, machineData, wingData);
        final AerofoilData rootAerofoilData;
        final AerofoilData tipAerofoilData;
        if (aerofoilRepository.exists(wingData.getRootAerofoil())) {
            rootAerofoilData = aerofoilRepository.findOne(wingData.getRootAerofoil());
        } else {
            rootAerofoilData = new AerofoilDataAG36();
        }
        if (aerofoilRepository.exists(wingData.getTipAerofoil())) {
            final AerofoilData tempAerofoilData = aerofoilRepository.findOne(wingData.getTipAerofoil()); // todo: when the gcode generator can process datafiles of different lenth this can be retured to tip 
            if (rootAerofoilData.isBezier() == tempAerofoilData.isBezier() && rootAerofoilData.getPoints().length == tempAerofoilData.getPoints().length) {
                tipAerofoilData = tempAerofoilData;
            } else {
                tipAerofoilData = rootAerofoilData;
            }
        } else {
            tipAerofoilData = rootAerofoilData;
        }
        final GcodeGenerator gcodeGenerator = new GcodeGenerator(machineData, rootAerofoilData, bedAlignmentCalculator, tipAerofoilData, wingData.isCutTwoMirrored());
        String referer = request.getHeader("Referer");
        return gcodeGenerator.toGcode(machineData, referer);
    }

    @RequestMapping(value = "/calibrationGcode",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @ResponseBody
    String downloadCalibrationGcode(
            @ModelAttribute MachineData machineData,
            @RequestParam(value = "layerThickeness", required = false, defaultValue = "false") final int layerThickeness,
            @RequestParam(value = "startSpeed", required = false, defaultValue = "false") final int startSpeed,
            @RequestParam(value = "endSpeed", required = false, defaultValue = "false") final int endSpeed,
            @RequestParam(value = "startPwm", required = false, defaultValue = "false") final int startPwm,
            @RequestParam(value = "endPwm", required = false, defaultValue = "false") final int endPwm,
            HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment;filename=" + startSpeed + "-" + endSpeed + "s" + startPwm + "-" + endPwm + "pwm");
        final GcodeGenerator gcodeGenerator = new GcodeGenerator();
        return gcodeGenerator.generateTestGcode(machineData, layerThickeness, startPwm, endPwm, startSpeed, endSpeed);
    }
}
