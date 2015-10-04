/**
 * Copyright (C) 2015 Peter Withers
 */

package com.bambooradical.winggcodegenerator.dao;

import com.bambooradical.winggcodegenerator.model.AerofoilData;
import org.springframework.data.repository.CrudRepository;

/**
 * @since Sep 25, 2015 22:12:50 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public interface AerofoilRepository  extends CrudRepository<AerofoilData, Long> {

    AerofoilData findByName(String name);
}
