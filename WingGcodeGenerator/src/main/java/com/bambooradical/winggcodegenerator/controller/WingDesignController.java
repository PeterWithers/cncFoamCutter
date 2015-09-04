/**
 * Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.controller;

import com.bambooradical.winggcodegenerator.model.AerofoilDataAG36;
import com.bambooradical.winggcodegenerator.model.Bounds;
import com.bambooradical.winggcodegenerator.model.MachineData;
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
    public String designView(
            @RequestParam(value = "machineDepth", required = false, defaultValue = "350") int machineDepth,
            @RequestParam(value = "machineHeight", required = false, defaultValue = "100") int machineHeight,
            @RequestParam(value = "wireLength", required = false, defaultValue = "600") int wireLength,
            @RequestParam(value = "viewAngle", required = false, defaultValue = "10") int viewAngle,
            @RequestParam(value = "rootChord", required = false, defaultValue = "120") int rootChord,
            @RequestParam(value = "tipChord", required = false, defaultValue = "120") int tipChord,
            @RequestParam(value = "wingSpan", required = false, defaultValue = "120") int wingSpan,
            Model model) {
        final AerofoilDataAG36 tipAerofoilData = new AerofoilDataAG36(tipChord);
        final AerofoilDataAG36 rootAerofoilData = new AerofoilDataAG36(rootChord);
        final MachineData machineData = new MachineData(machineDepth, machineHeight, wireLength);
        model.addAttribute("machineData", machineData);
        model.addAttribute("machineSvgPoints", machineData.toSvgPoints(viewAngle));
        model.addAttribute("viewAngle", viewAngle);
        model.addAttribute("rootChord", rootAerofoilData.getChord());
        model.addAttribute("tipChord", tipAerofoilData.getChord());
        model.addAttribute("wingSpan", wingSpan);
        model.addAttribute("aerofoilname", tipAerofoilData.getName());
        model.addAttribute("rootAerofoilData", rootAerofoilData.toSvgPoints(0, 0));
        model.addAttribute("tipAerofoilData", tipAerofoilData.toSvgPoints(viewAngle / wireLength * wingSpan, wingSpan));
        model.addAttribute("tipX", 0);
        model.addAttribute("tipY", 0);
        model.addAttribute("rootX", 0);
        model.addAttribute("rootY", wingSpan);
        final Bounds svgBounds = machineData.getSvgBounds(viewAngle);
        model.addAttribute("svgbounds", svgBounds.getMinX() + " " + svgBounds.getMinY() + " " + svgBounds.getWidth() + " " + svgBounds.getHeight());
        model.addAttribute("aerofoilbounds", svgBounds);
        model.addAttribute("diagramheight", "100%");
        model.addAttribute("diagramwidth", "100%");
        return "WingDesignView";
    }
}
