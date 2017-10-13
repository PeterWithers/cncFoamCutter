/**
 * Copyright (C) 2017 Peter Withers
 */
package com.bambooradical.winggcodegenerator.dao;

import com.bambooradical.winggcodegenerator.model.AccessData;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        accessDataKeyFactory = datastore.newKeyFactory().setKind("AccessData");
    }

    public Entity storeAccessData(AccessData accessData) {
//        Key key = accessDataKeyFactory.newKey(accessData.getRemoteAddress());
//        Entity entity = Entity.newBuilder(key)
        IncompleteKey key = accessDataKeyFactory.setKind("AccessData").newKey();
        FullEntity entity = FullEntity.newBuilder(key)
                .set("AcceptLanguage", accessData.getAcceptLanguage())
                .set("RemoteAddress", accessData.getRemoteAddress())
                .set("RequestUrl", accessData.getRequestUrl())
                .set("UserAgent", accessData.getUserAgent())
                .set("AccessDate", Timestamp.of(accessData.getAccessDate()))
                .build();
        return datastore.put(entity);
    }

    public List<AccessData> findAll() {
        List<AccessData> resultList = new ArrayList<>();
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("AccessData")
                //                .setFilter(PropertyFilter.eq("RemoteAddress", "x"))
                .build();
        QueryResults<Entity> results = datastore.run(query);
        while (results.hasNext()) {
            Entity currentEntity = results.next();
            resultList.add(new AccessData(
                    new Date(currentEntity.getTimestamp("AccessDate").getSeconds() * 100L),
                    currentEntity.getString("RemoteAddress"),
                    currentEntity.getString("UserAgent"),
                    currentEntity.getString("AcceptLanguage"),
                    currentEntity.getString("RequestUrl")));
        }
        return resultList;
    }
}
