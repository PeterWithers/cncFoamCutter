/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.controller;

import com.bambooradical.winggcodegenerator.dao.AccessDataRepository;
import com.bambooradical.winggcodegenerator.model.AccessData;
import com.bambooradical.winggcodegenerator.model.PistachioPropData;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

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
                        @ModelAttribute PistachioPropData pistachioPropData,
            //            @ModelAttribute int hubDiameter,
            //            @ModelAttribute int pistachioPropData.getPropDiameter(),
            @RequestHeader("Accept-Language") String acceptLang,
            @RequestHeader("User-Agent") String userAgent,
            HttpServletRequest request) {
        final String remoteAddr = request.getRemoteAddr();
        final String requestURI = request.getRequestURI();
        final Date accessDate = new java.util.Date();
        accessDataRepository.save(new AccessData(accessDate, remoteAddr, userAgent, acceptLang, requestURI));
        String svgPoints = "M";
        // draw outer ring
        final double radPerIndex = Math.PI * 2 / 360;
        for (int degreeIndex = 0; degreeIndex <= 360; degreeIndex++) {
            final double xPos = Math.sin(radPerIndex * degreeIndex) * pistachioPropData.getPropDiameter() / 2;
            final double yPos = Math.cos(radPerIndex * degreeIndex) * pistachioPropData.getPropDiameter() / 2;
            svgPoints += (50 + xPos) + ", " + yPos + " ";
        }
        final double radPerBlade = Math.PI * 2 / pistachioPropData.getBladeCount();
//        for (int layerCount = 0; layerCount < propThickness / layerHeight; layerCount += layerHeight) {
        for (double layerCount = 0; layerCount < 10; layerCount++) {
            for (double bladeIndex = 0; bladeIndex <= pistachioPropData.getBladeCount(); bladeIndex++) {
                final double layerTipRotation = layerCount / 100;
                final double xOuterPos = Math.sin(radPerBlade * (bladeIndex + layerTipRotation)) * pistachioPropData.getPropDiameter() / 2;
                final double yOuterPos = Math.cos(radPerBlade * (bladeIndex + layerTipRotation)) * pistachioPropData.getPropDiameter() / 2;
                final double xInnerPos = Math.sin(radPerBlade * bladeIndex) * pistachioPropData.getShaftDiameter() / 2;
                final double yInnerPos = Math.cos(radPerBlade * bladeIndex) * pistachioPropData.getShaftDiameter() / 2;
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
