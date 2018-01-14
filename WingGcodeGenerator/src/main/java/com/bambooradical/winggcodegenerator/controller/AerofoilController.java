/**
 * Copyright (C) 2018 Peter Withers
 */
package com.bambooradical.winggcodegenerator.controller;

import com.bambooradical.winggcodegenerator.dao.AccessDataService;
import com.bambooradical.winggcodegenerator.dao.AerofoilService;
import com.bambooradical.winggcodegenerator.model.AccessData;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @since Jan 14, 2018 1:58:31 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
@Controller
public class AerofoilController {

    @Autowired
    AerofoilService aerofoilRepository;
    @Autowired
    AccessDataService accessDataRepository;

    @RequestMapping("/AerofoilListing")
    public String pathListing(
            Model model,
            @RequestHeader("Accept-Language") String acceptLang,
            @RequestHeader("User-Agent") String userAgent,
            HttpServletRequest request) {
        final String remoteAddr = request.getRemoteAddr();
        final String requestURI = request.getRequestURI();
        final Date accessDate = new java.util.Date();
        final AccessData accessData = new AccessData(accessDate, remoteAddr, userAgent, acceptLang, requestURI);
        accessDataRepository.save(accessData);
//        aerofoilRepository.save(new AerofoilData("temp data", new AerofoilDataAG36().getPoints()));
        model.addAttribute("aerofoilList", aerofoilRepository.findAll());
        return "AerofoilListing";
    }
}
