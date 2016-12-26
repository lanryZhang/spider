/**
 * AnalysisDao.java
 * Created by zhusy on 2016/12/6 0006 14:18
 * Copyright © 2012 Phoenix New Media Limited All Rights Reserved
 */
package com.ifeng.pgc.dao;

import com.ifeng.pgc.beans.SiteAnalysis;
import com.ifeng.pgc.core.mongo.MongoSelect;
import com.ifeng.pgc.core.mongo.Where;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;
import java.util.Map;

public interface SiteAnalysisDao {

    void insertOne(SiteAnalysis siteAnalysis) throws Exception;

    void insertList(List<SiteAnalysis> siteAnalysises) throws Exception;

    SiteAnalysis selectOne(MongoSelect select) throws Exception;

    List<SiteAnalysis> selectList(MongoSelect select) throws Exception;

    UpdateResult update(Map<String, Object> map, Where where, boolean upsert) throws Exception;

    DeleteResult delete(Where where) throws Exception;

    UpdateResult inc(Map<String, Number> fields, Where where) throws  Exception;

    UpdateResult inc(Map<String, Object> fields, Map<String, Number> map, Where where, boolean upsert) throws Exception;

    UpdateResult inc(SiteAnalysis analysis, Map<String, Number> incField, Where where, Boolean upsert) throws  Exception;
}
