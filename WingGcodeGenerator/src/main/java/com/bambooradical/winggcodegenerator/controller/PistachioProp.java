/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.controller;

import com.bambooradical.winggcodegenerator.dao.AccessDataRepository;
import com.bambooradical.winggcodegenerator.model.AccessData;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @since Jan 14, 2016 10:08:29 PM (creation date)
 * @author petwit
 */
@Controller
public class PistachioProp {

    @Autowired
    AccessDataRepository accessDataRepository;

    @RequestMapping("/PistachioProp")
    public String designView(
            Model model,
            //            @ModelAttribute int bladeCount,
            //            @ModelAttribute int hubDiameter,
            //            @ModelAttribute int propDiameter,
            @RequestParam(value = "propDiameter", required = true, defaultValue = "50") final int propDiameter,
            @RequestParam(value = "propThickness", required = true, defaultValue = "5") final int propThickness,
            @RequestParam(value = "bladeCount", required = true, defaultValue = "5") final int bladeCount,
            @RequestParam(value = "shaftDiameter", required = true, defaultValue = "5") final float shaftDiameter,
            @RequestParam(value = "layerHeight", required = true, defaultValue = "0.3") final float layerHeight,
            @RequestHeader("Accept-Language") String acceptLang,
            @RequestHeader("User-Agent") String userAgent,
            HttpServletRequest request) {
        final String remoteAddr = request.getRemoteAddr();
        final String requestURI = request.getRequestURI();
        final Date accessDate = new java.util.Date();
        accessDataRepository.save(new AccessData(accessDate, remoteAddr, userAgent, acceptLang, requestURI));
        model.addAttribute("shaftDiameter", shaftDiameter);
        model.addAttribute("propThickness", propThickness);
        model.addAttribute("propDiameter", propDiameter);
        model.addAttribute("bladeCount", bladeCount);
        model.addAttribute("layerHeight", layerHeight);
        String svgPoints = "M";
        // draw outer ring
        final double radPerIndex = Math.PI * 2 / 360;
        for (int degreeIndex = 0; degreeIndex <= 360; degreeIndex++) {
            final double xPos = Math.sin(radPerIndex * degreeIndex) * propDiameter / 2;
            final double yPos = Math.cos(radPerIndex * degreeIndex) * propDiameter / 2;
            svgPoints += (50 + xPos) + ", " + yPos + " ";
        }
        final double radPerBlade = Math.PI * 2 / bladeCount;
//        for (int layerCount = 0; layerCount < propThickness / layerHeight; layerCount += layerHeight) {
        for (double layerCount = 0; layerCount < 10; layerCount++) {
            for (double bladeIndex = 0; bladeIndex <= bladeCount; bladeIndex++) {
                final double layerTipRotation = layerCount / 100;
                final double xOuterPos = Math.sin(radPerBlade * (bladeIndex + layerTipRotation)) * propDiameter / 2;
                final double yOuterPos = Math.cos(radPerBlade * (bladeIndex + layerTipRotation)) * propDiameter / 2;
                final double xInnerPos = Math.sin(radPerBlade * bladeIndex) * shaftDiameter / 2;
                final double yInnerPos = Math.cos(radPerBlade * bladeIndex) * shaftDiameter / 2;
                svgPoints += (50 + xInnerPos) + ", " + yInnerPos + " ";
                svgPoints += (50 + xOuterPos) + ", " + yOuterPos + " ";
                svgPoints += (50 + xInnerPos) + ", " + yInnerPos + " ";
            }
        }
        model.addAttribute("propPath", svgPoints);
        return "PistachioProp";
    }

    @RequestMapping("/updatePropSettings")
    public String updatePropSettings() {
        return "PistachioProp :: propEditor";
    }
}
