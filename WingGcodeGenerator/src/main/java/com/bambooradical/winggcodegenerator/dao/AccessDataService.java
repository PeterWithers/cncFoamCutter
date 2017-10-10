/**
 * Copyright (C) 2017 Peter Withers
 */
package com.bambooradical.winggcodegenerator.dao;

import com.bambooradical.winggcodegenerator.model.AccessData;
import com.google.cloud.datastore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

/**
 * @since Oct 10, 2017 9:50:27 PM (creation date)
 * @author Peter Withers <peter-gthb@bambooradical.com>
 */
@Service
public class AccessDataService {

    @Autowired
    Datastore datastore;

    private KeyFactory accessDataKeyFactory;

    @PostConstruct
    public void initializeKeyFactories() {
        accessDataKeyFactory = datastore.newKeyFactory().kind("AccessData");
    }

    public Entity storeAccessData(AccessData accessData) {
        Key key = accessDataKeyFactory.newKey(accessData.getRemoteAddress());
        Entity entity = Entity.builder(key)
                .set("AcceptLanguage", accessData.getAcceptLanguage())
                .set("RemoteAddress", accessData.getRemoteAddress())
                .set("RequestUrl", accessData.getRequestUrl())
                .set("UserAgent", accessData.getUserAgent())
                .set("AccessDate()", DateTime.copyFrom(accessData.getAccessDate()))
                .build();
        return datastore.put(entity);
    }
}
