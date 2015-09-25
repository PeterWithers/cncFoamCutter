/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bambooradical.winggcodegenerator.dao;

import com.bambooradical.winggcodegenerator.model.AerofoilData;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * @since Sep 25, 2015 22:12:50 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public interface AerofoilRepository  extends CrudRepository<AerofoilData, Long> {

    AerofoilData findByName(String name);
//    List<String> selectDistinctNameDesc();

}
