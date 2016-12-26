/**
 * AnalysisService.java
 * Created by zhusy on 2016/12/6 0006 14:43
 * Copyright © 2012 Phoenix New Media Limited All Rights Reserved
 */
package com.ifeng.pgc.services;

import com.ifeng.pgc.beans.Analysis;
import com.ifeng.pgc.core.mongo.MongoSelect;
import com.ifeng.pgc.core.mongo.Where;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;
import java.util.Map;

public interface AnalysisService {

    void insertOne(Analysis analysis) throws Exception;

    void insertList(List<Analysis> analysisList) throws Exception;

    Analysis selectOne(MongoSelect select) throws Exception;

    Analysis selectOne(String pgcName, String site, String dateTime) throws Exception;

    List<Analysis> selectList(MongoSelect select) throws Exception;

    UpdateResult update(Map<String, Object> map, Where where, boolean upsert) throws Exception;

    UpdateResult update(Analysis analysis, boolean upsert) throws Exception;

    DeleteResult delete(Where where) throws Exception;

    DeleteResult delete(Analysis analysis) throws Exception;

    UpdateResult AddSpiderCount(Analysis analysis) throws Exception;

    UpdateResult AddSpiderVideoCount(Analysis analysis, int count) throws Exception;

    UpdateResult AddSucSpiderCount(Analysis analysis) throws Exception;

    UpdateResult AddFailSpiderCount(Analysis analysis) throws Exception;

    UpdateResult AddDownloadCount(Analysis analysis) throws Exception;

    UpdateResult AddSucDownloadCount(Analysis analysis) throws Exception;

    UpdateResult AddFailDownloadCount(Analysis analysis) throws Exception;

    UpdateResult AddSucSynCount(Analysis analysis) throws Exception;

    UpdateResult AddFailSynCount(Analysis analysis) throws Exception;
}
