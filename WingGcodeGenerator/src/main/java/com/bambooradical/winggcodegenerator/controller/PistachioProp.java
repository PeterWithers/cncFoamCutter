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
            @RequestParam(value = "propDiameter", required = true, defaultValue = "25") final int propDiameter,
            @RequestParam(value = "propThickness", required = true, defaultValue = "5") final int propThickness,
            @RequestParam(value = "bladeCount", required = true, defaultValue = "5") final int bladeCount,
            @RequestParam(value = "shaftDiameter", required = true, defaultValue = "0.5") final float shaftDiameter,
            @RequestParam(value = "layerHeight", required = true, defaultValue = "0.3") final float layerHeight,
            @RequestHeader("Accept-Language") String acceptLang,
            @RequestHeader("User-Agent") String userAgent,
            HttpServletRequest request) {
        final String remoteAddr = request.getRemoteAddr();
        final String requestURI = request.getRequestURI();
        final Date accessDate = new java.util.Date();
        accessDataRepository.save(new AccessData(accessDate, remoteAddr, userAgent, acceptLang, requestURI));
        model.addAttribute("shaftDiameter", shaftDiameter);
        model.addAttribute("propThickness", shaftDiameter);
        model.addAttribute("propDiameter", propDiameter);
        model.addAttribute("bladeCount", bladeCount);
        model.addAttribute("layerHeight", layerHeight);
        model.addAttribute("propPath", "M0,0, 10,1 2,10");
        return "PistachioProp";
    }

    @RequestMapping("/updatePropSettings")
    public String updatePropSettings() {
        return "PistachioProp :: propEditor";
    }
}
