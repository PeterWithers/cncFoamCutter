/**
 * Copyright (C) 2017 Peter Withers
 */
package com.bambooradical.winggcodegenerator.dao;

import com.bambooradical.winggcodegenerator.model.AerofoilData;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.cloud.datastore.FullEntity.Builder;
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
public class AerofoilService {

    @Autowired
    Datastore datastore;

    private KeyFactory aerofoilKeyFactory;

    @PostConstruct
    public void initializeKeyFactories() {
        aerofoilKeyFactory = datastore.newKeyFactory().setKind("AerofoilData");
    }

    private void setPoints(final Builder builder, double[][] points) {
        for (int index = 0; index < points.length; index++) {
            double[] point = points[index];
            builder.set("p" + index, point[0], point[1]);
        }
    }

    private double[][] getPoints(Entity entity) {
        final ArrayList<double[]> dataPoints = new ArrayList<>();
        int index = 0;
        while (entity.contains("p" + index)) {
            final List<Value<Double>> list = entity.getList("p" + index);
            dataPoints.add(new double[]{list.get(0).get(), list.get(1).get()});
            index++;
        }
        return dataPoints.toArray(new double[][]{});
    }
//    private void setPoints(final Builder builder, double[][] points) {
//        ArrayList<Double> pointsList = new ArrayList<Double>();
//        for (double[] point : points) {
//            pointsList.add(point[0]);
//            pointsList.add(point[1]);
//        }
//        builder.set("points", pointsList);
//    }
//
//    private double[][] getPoints(final ArrayList<Double> dataPoints) {
//        double[][] points = new double[dataPoints.size() / 2][2];
//        for (int index = 0; index < dataPoints.size() / 2; index++) {
//            points[1][0] = dataPoints.get(index);
//            points[1][1] = dataPoints.get(index + 1);
//        }
//        return points;
//    }

    public Entity save(AerofoilData aerofoilData) {
//        Key key = aerofoilKeyFactory.newKey(aerofoilData.getName());
//        Entity entity = Entity.newBuilder(key)
        IncompleteKey key = aerofoilKeyFactory.setKind("AerofoilData").newKey();
//        final FullEntity pointsEntities = getPointsEntities(aerofoilData.getPoints());
        final Builder builder = FullEntity.newBuilder(key);
        builder.set("Name", aerofoilData.getName())
                .set("ParentId", (aerofoilData.getParentId() != null) ? aerofoilData.getParentId() : -1)
                .set("RemoteAddress", aerofoilData.getRemoteAddress())
                .set("isBezier", aerofoilData.isBezier())
                .set("isEditable", aerofoilData.isEditable())
                .set("isHidden", aerofoilData.isHidden())
                .set("AccessDate", Timestamp.of(aerofoilData.getAccessDate()));
        setPoints(builder, aerofoilData.getPoints());
        FullEntity entity = builder.build();
        return datastore.put(entity);
    }

    public boolean exists(Long id) {
        Key key = aerofoilKeyFactory.newKey(id);
        return datastore.get(key) != null;
    }

    public AerofoilData findOne(Long id) {
        Key key = aerofoilKeyFactory.newKey(id);
        Entity entity = datastore.get(key);
        return extractAerofoilData(entity);
    }

    private AerofoilData extractAerofoilData(Entity entity) {
//        @SuppressWarnings("unchecked")
//        ArrayList<Double> pointsList = (ArrayList<Double>) entity.getList("points");
//        AerofoilData aerofoilData = new AerofoilData(entity.getString("Name"), getPoints(pointsList));
        AerofoilData aerofoilData = new AerofoilData(entity.getString("Name"), getPoints(entity));
        aerofoilData.setAccessDate(new Date(entity.getTimestamp("AccessDate").getSeconds() * 100L));
        aerofoilData.setId(entity.getKey().getId());
        aerofoilData.setBezier(entity.getBoolean("isBezier"));
        aerofoilData.setEditable(entity.getBoolean("isEditable"));
        aerofoilData.setHidden(entity.getBoolean("isHidden"));
        aerofoilData.setBezier(entity.getBoolean("isBezier"));
        aerofoilData.setParentId(entity.getLong("ParentId"));
        aerofoilData.setRemoteAddress(entity.getString("RemoteAddress"));
        return aerofoilData;
    }

//    public long count() {
//        Query<Entity> query = Query.newEntityQueryBuilder().setKind("AerofoilData").build();
//        QueryResults<Entity> results = datastore.run(query);
//        return results.
//    }
    public List<AerofoilData> findAll() {
        List<AerofoilData> resultList = new ArrayList<>();
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("AerofoilData")
                //                .setFilter(PropertyFilter.eq("RemoteAddress", "x"))
                .build();
        QueryResults<Entity> results = datastore.run(query);
        while (results.hasNext()) {
            Entity currentEntity = results.next();
            resultList.add(extractAerofoilData(currentEntity));
        }
        return resultList;
    }

    public void delete(AerofoilData aerofoilData) {
        Key key = aerofoilKeyFactory.newKey(aerofoilData.getId());
        datastore.delete(key);
    }
}
