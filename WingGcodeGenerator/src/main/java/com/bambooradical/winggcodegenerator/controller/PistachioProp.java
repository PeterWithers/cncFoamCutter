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
        return "PistachioProp";
    }

    @RequestMapping("/updatePropSettings")
    public String updatePropSettings() {
        return "PistachioProp :: propEditor";
    }
}
