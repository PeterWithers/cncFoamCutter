/**
 * Copyright (C) 2017 Peter Withers
 */
package com.bambooradical.winggcodegenerator.controller;

import com.bambooradical.winggcodegenerator.dao.AccessDataService;
import com.bambooradical.winggcodegenerator.dao.AerofoilService;
import com.bambooradical.winggcodegenerator.model.AccessData;
import com.bambooradical.winggcodegenerator.model.AerofoilData;
import com.bambooradical.winggcodegenerator.model.LaserTestGcodeData;
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

/**
 * @since Dec 20, 2017 19:48 PM (creation date)
 * @author Peter Withers <peter-gthb@bambooradical.com>
 */
@Controller
public class LaserTestGcode {

    @Autowired
    AccessDataService accessDataRepository;
    @Autowired
    AerofoilService aerofoilRepository;

    @RequestMapping("/LaserTestGcode")
    public String laserTestGcode(
            Model model,
            @ModelAttribute LaserTestGcodeData laserTestGcodeData,
            @RequestParam(value = "aerofoilData", required = false, defaultValue = "-1") final long aerofoilId,
            @RequestParam(value = "minimal", required = false, defaultValue = "false") final boolean minimal,
            @RequestHeader("Accept-Language") String acceptLang,
            @RequestHeader("User-Agent") String userAgent,
            HttpServletRequest request) {
        if (aerofoilId >= 0) {
            final AerofoilData aerofoilData = aerofoilRepository.findOne(aerofoilId);
            laserTestGcodeData.setAerofoilData(aerofoilData);
        }
        model.addAttribute("aerofoilList", aerofoilRepository.findAll());
        model.addAttribute("minimal", minimal);
        final String remoteAddr = request.getRemoteAddr();
        final String requestURI = request.getRequestURI();
        final Date accessDate = new java.util.Date();
        accessDataRepository.save(new AccessData(accessDate, remoteAddr, userAgent, acceptLang, requestURI));
        return "LaserTestGcode";
    }

    @RequestMapping(value = "/laserGcode",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @ResponseBody
    String downloadFile(
            @ModelAttribute LaserTestGcodeData laserTestGcodeData,
            @RequestHeader("Accept-Language") String acceptLang,
            @RequestHeader("User-Agent") String userAgent,
            HttpServletResponse response,
            HttpServletRequest request) {
        response.setHeader("Content-Disposition", "attachment;filename=laser-test_s" + laserTestGcodeData.getMinPower()
                + "-s" + laserTestGcodeData.getMaxPower()
                + "_f" + laserTestGcodeData.getMinSpeed() + "-f" + laserTestGcodeData.getMaxSpeed() + ".gcode");
        final String remoteAddr = request.getRemoteAddr();
        final String requestURI = request.getRequestURI();
        final Date accessDate = new java.util.Date();
        accessDataRepository.save(new AccessData(accessDate, remoteAddr, userAgent, acceptLang, requestURI));
        return laserTestGcodeData.getGcode();
    }
}
