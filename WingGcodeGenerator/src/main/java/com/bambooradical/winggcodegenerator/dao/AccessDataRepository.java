/*
 *  Copyright (C) 2015 Peter Withers
 */
package com.bambooradical.winggcodegenerator.dao;

import com.bambooradical.winggcodegenerator.model.AccessData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @since Oct 4, 2015 3:29:15 PM (creation date)
 * @author Peter Withers <peter@bambooradical.com>
 */
public interface AccessDataRepository extends JpaRepository<AccessData, Long> {

}
