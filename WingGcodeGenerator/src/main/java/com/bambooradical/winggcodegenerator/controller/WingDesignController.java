/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.controller;

import com.bambooradical.winggcodegenerator.model.AerofoilDataAG36;
import com.bambooradical.winggcodegenerator.model.Bounds;
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
        final AerofoilDataAG36 aerofoilData = new AerofoilDataAG36(chord);
        model.addAttribute("aerofoilchord", aerofoilData.getChord());
        model.addAttribute("aerofoilname", aerofoilData.getName());
        model.addAttribute("aerofoildata", aerofoilData.toSvgPoints());
        final Bounds svgBounds = aerofoilData.getSvgBounds();
        model.addAttribute("svgbounds", svgBounds.getMinX() + " " + svgBounds.getMinY() + " " + svgBounds.getWidth() + " " + svgBounds.getHeight());
        model.addAttribute("aerofoilbounds", svgBounds);
        model.addAttribute("diagramheight", aerofoilData.getChord());
        model.addAttribute("diagramwidth", aerofoilData.getChord() * 1.2);
        return "WingDesignView";
    }
}
