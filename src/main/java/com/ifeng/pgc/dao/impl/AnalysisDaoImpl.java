/**
 * AnalysisDaoImpl.java
 * Created by zhusy on 2016/12/6 0006 14:37
 * Copyright © 2012 Phoenix New Media Limited All Rights Reserved
 */
package com.ifeng.pgc.dao.impl;

import com.ifeng.pgc.beans.Analysis;
import com.ifeng.pgc.core.mongo.MongoCli;
import com.ifeng.pgc.core.mongo.MongoFactory;
import com.ifeng.pgc.core.mongo.MongoSelect;
import com.ifeng.pgc.core.mongo.Where;
import com.ifeng.pgc.dao.AnalysisDao;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AnalysisDaoImpl implements AnalysisDao {
    private final static String COLLECTION_NAME = "Analysis";
    private MongoCli client;

    public AnalysisDaoImpl() {
        this.client = MongoFactory.newInstance("spider");
    }

    @Override
    public void insertOne(Analysis analysis) throws Exception {
        client.getCollection(COLLECTION_NAME);
        client.insert(analysis);
    }

    @Override
    public void insertList(List<Analysis> analysisList) throws Exception {
        client.getCollection(COLLECTION_NAME);
        client.insert(analysisList);
    }

    @Override
    public Analysis selectOne(MongoSelect select) throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.selectOne(select, Analysis.class);
    }

    @Override
    public List<Analysis> selectList(MongoSelect select) throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.selectList(select, Analysis.class);
    }

    @Override
    public UpdateResult update(Map<String, Object> map, Where where, boolean upsert) throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.update(map, where, upsert);
    }

    @Override
    public DeleteResult delete(Where where) throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.remove(where);
    }

    @Override
    public UpdateResult inc(Map<String, Number> fields, Where where) throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.inc(fields,where);
    }

    @Override
    public UpdateResult inc(Map<String, Object> fields, Map<String, Number> map, Where where, boolean upsert) throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.inc(fields,map,where,upsert);
    }

    @Override
    public UpdateResult inc(Analysis analysis, Map<String, Number> incField, Where where, Boolean upsert) throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.inc(analysis,incField,where,upsert);
    }
}
