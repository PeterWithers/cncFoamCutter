/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.controller;

import com.bambooradical.winggcodegenerator.model.AerofoilDataAG36;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @since Aug 17, 2015 8:37:40 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
@Controller
public class WingDesignController {

    @RequestMapping("/WingDesignView")
    public String greeting(@RequestParam(value = "chord", required = false, defaultValue = "120") int chord, Model model) {
        model.addAttribute("chord", chord);
        final AerofoilDataAG36 aerofoilData = new AerofoilDataAG36();
        model.addAttribute("aerofoilname", aerofoilData.getName());
        model.addAttribute("aerofoildata", aerofoilData.getPoints());
        return "WingDesignView";
    }
}
