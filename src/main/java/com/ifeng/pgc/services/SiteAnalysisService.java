/**
 * AnalysisService.java
 * Created by zhusy on 2016/12/6 0006 14:43
 * Copyright © 2012 Phoenix New Media Limited All Rights Reserved
 */
package com.ifeng.pgc.services;

import com.ifeng.pgc.beans.SiteAnalysis;
import com.ifeng.pgc.core.mongo.MongoSelect;
import com.ifeng.pgc.core.mongo.Where;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;
import java.util.Map;

public interface SiteAnalysisService {

    void insertOne(SiteAnalysis siteAnalysis) throws Exception;

    void insertList(List<SiteAnalysis> siteAnalysisList) throws Exception;

    SiteAnalysis selectOne(MongoSelect select) throws Exception;

    SiteAnalysis selectOne(String site, String dateTime) throws Exception;

    List<SiteAnalysis> selectList(MongoSelect select) throws Exception;

    UpdateResult update(Map<String, Object> map, Where where, boolean upsert) throws Exception;

    UpdateResult update(SiteAnalysis siteAnalysis, boolean upsert) throws Exception;

    DeleteResult delete(Where where) throws Exception;

    DeleteResult delete(SiteAnalysis siteAnalysis) throws Exception;

    UpdateResult AddSpiderVideoCount(SiteAnalysis siteAnalysis, int count) throws Exception;

    UpdateResult AddSucSpiderCount(SiteAnalysis siteAnalysis) throws Exception;

    UpdateResult AddFailSpiderCount(SiteAnalysis siteAnalysis) throws Exception;

    UpdateResult AddSucDownloadCount(SiteAnalysis siteAnalysis) throws Exception;

    UpdateResult AddFailDownloadCount(SiteAnalysis siteAnalysis) throws Exception;

    UpdateResult AddSucSynCount(SiteAnalysis siteAnalysis) throws Exception;

    UpdateResult AddFailSynCount(SiteAnalysis siteAnalysis) throws Exception;
}
