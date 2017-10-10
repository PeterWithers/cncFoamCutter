/**
 * Copyright (C) 2017 Peter Withers
 */
package com.bambooradical.winggcodegenerator.dao;

import com.bambooradical.winggcodegenerator.model.AerofoilData;
import com.google.cloud.datastore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

/**
 * @since Oct 10, 2017 9:50:27 PM (creation date)
 * @author Peter Withers <peter-gthb@bambooradical.com>
 */
@Service
public class AerofoilService {

    @Autowired
    Datastore datastore;

    private KeyFactory aerofoilKeyFactory;

    @PostConstruct
    public void initializeKeyFactories() {
        aerofoilKeyFactory = datastore.newKeyFactory().kind("AerofoilData");
    }

    public Entity storeAerofoil(AerofoilData aerofoilData) {
        Key key = aerofoilKeyFactory.newKey(aerofoilData.getName());
        Entity entity = Entity.builder(key)
                .set("Name", aerofoilData.getName())
                .set("ParentId", aerofoilData.getParentId())
                .set("RemoteAddress", aerofoilData.getRemoteAddress())
                .set("isBezier", aerofoilData.isBezier())
                .set("isEditable", aerofoilData.isEditable())
                .set("isHidden", aerofoilData.isHidden())
                .build();
        return datastore.put(entity);
    }
}
