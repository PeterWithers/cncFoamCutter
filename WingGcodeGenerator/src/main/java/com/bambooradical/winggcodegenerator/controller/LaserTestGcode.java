/**
 * Copyright (C) 2017 Peter Withers
 */
package com.bambooradical.winggcodegenerator.controller;

import com.bambooradical.winggcodegenerator.dao.AccessDataRepository;
import com.bambooradical.winggcodegenerator.model.AccessData;
import com.bambooradical.winggcodegenerator.model.LaserTestGcodeData;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @since Dec 20, 2017 19:48 PM (creation date)
 * @author Peter Withers <peter-gthb@bambooradical.com>
 */
@Controller
public class LaserTestGcode {

    @Autowired
    AccessDataRepository accessDataRepository;

    @RequestMapping("/LaserTestGcode")
    public String laserTestGcode(
            @ModelAttribute LaserTestGcodeData laserTestGcodeData,
            @RequestHeader("Accept-Language") String acceptLang,
            @RequestHeader("User-Agent") String userAgent,
            HttpServletRequest request) {
        final String remoteAddr = request.getRemoteAddr();
        final String requestURI = request.getRequestURI();
        final Date accessDate = new java.util.Date();
        accessDataRepository.save(new AccessData(accessDate, remoteAddr, userAgent, acceptLang, requestURI));
        return "LaserTestGcode";
    }

//    @RequestMapping("/updatePropSettings")
//    public String updatePropSettings() {
//        return "PistachioProp :: propEditor";
//    }
}
