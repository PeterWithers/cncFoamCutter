/**
 * Copyright (C) 2017 Peter Withers
 */
package com.bambooradical.winggcodegenerator.dao;

import com.bambooradical.winggcodegenerator.model.AerofoilData;
import com.bambooradical.winggcodegenerator.util.AerofoilDatParser;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import java.util.Date;
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
        aerofoilKeyFactory = datastore.newKeyFactory().setKind("AerofoilData");
    }

    public Entity storeAerofoil(AerofoilData aerofoilData) {
        Key key = aerofoilKeyFactory.newKey(aerofoilData.getName());
        Entity entity = Entity.newBuilder(key)
                .set("Name", aerofoilData.getName())
                .set("ParentId", aerofoilData.getParentId())
                .set("RemoteAddress", aerofoilData.getRemoteAddress())
                .set("isBezier", aerofoilData.isBezier())
                .set("isEditable", aerofoilData.isEditable())
                .set("isHidden", aerofoilData.isHidden())
                .set("AccessDate()", Timestamp.of(aerofoilData.getAccessDate()))
                .set("Points", AerofoilDatParser.encodeString(aerofoilData.getPoints()))
                .build();
        return datastore.put(entity);
    }

    public boolean exists(Long id) {
        Key key = aerofoilKeyFactory.newKey(id);
        return datastore.get(key) != null;
    }

    public AerofoilData findOne(Long id) {
        Key key = aerofoilKeyFactory.newKey(id);
        Entity entity = datastore.get(key);
        AerofoilData aerofoilData = new AerofoilData(entity.getString("Name"), AerofoilDatParser.decodeString(entity.getString("Points")));
        aerofoilData.setAccessDate(new Date(entity.getTimestamp("AccessDate").getSeconds() * 100L));
        aerofoilData.setBezier(entity.getBoolean("isBezier"));
        aerofoilData.setEditable(entity.getBoolean("isEditable"));
        aerofoilData.setHidden(entity.getBoolean("isHidden"));
        aerofoilData.setBezier(entity.getBoolean("isBezier"));
        aerofoilData.setParentId(entity.getLong("ParentId"));
        aerofoilData.setRemoteAddress(entity.getString("RemoteAddress"));
        return aerofoilData;
    }
}
