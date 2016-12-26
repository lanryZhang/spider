/**
 * AnalysisServiceImpl.java
 * Created by zhusy on 2016/12/6 0006 15:14
 * Copyright © 2012 Phoenix New Media Limited All Rights Reserved
 */
package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.beans.Analysis;
import com.ifeng.pgc.core.mongo.MongoSelect;
import com.ifeng.pgc.core.mongo.Where;
import com.ifeng.pgc.core.mongo.WhereType;
import com.ifeng.pgc.dao.AnalysisDao;
import com.ifeng.pgc.services.AnalysisService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "analysisService")
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    private AnalysisDao analysisDao;

    @Override
    public void insertOne(Analysis analysis) throws Exception {
        analysisDao.insertOne(analysis);
    }

    @Override
    public void insertList(List<Analysis> analysisList) throws Exception {
        analysisDao.insertList(analysisList);
    }

    @Override
    public Analysis selectOne(MongoSelect select) throws Exception {
        return analysisDao.selectOne(select);
    }

    @Override
    public Analysis selectOne(String pgcName, String site, String dateTime) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, site);
        where.and("pgcName", WhereType.Equal, pgcName);
        where.and("dateTime", WhereType.Equal, dateTime);
        return selectOne(new MongoSelect(where));
    }

    @Override
    public List<Analysis> selectList(MongoSelect select) throws Exception {
        return analysisDao.selectList(select);
    }

    @Override
    public UpdateResult update(Map<String, Object> map, Where where, boolean upsert) throws Exception {
        return analysisDao.update(map, where, upsert);
    }

    @Override
    public UpdateResult update(Analysis analysis, boolean upsert) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pgcId", analysis.getPgcId());
        map.put("site", analysis.getSite());
        map.put("pgcName", analysis.getPgcName());
        map.put("lastSpiderDate", analysis.getLastSpiderDate());
        map.put("dateTime", analysis.getDateTime());
        map.put("spiderCount", analysis.getSpiderCount());
        map.put("sucSpiderCount", analysis.getSucSpiderCount());
        map.put("failSpiderCount", analysis.getFailSpiderCount());
        map.put("downloadCount", analysis.getDownloadCount());
        map.put("sucDownloadCount", analysis.getSucDownloadCount());
        map.put("failDownloadCount", analysis.getFailDownloadCount());
        map.put("sucSynCount", analysis.getSucSynCount());
        map.put("failSynCount", analysis.getFailSynCount());
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("pgcName", WhereType.Equal, analysis.getPgcName());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        return analysisDao.update(map, where, true);
    }

    @Override
    public DeleteResult delete(Where where) throws Exception {
        return analysisDao.delete(where);
    }

    @Override
    public DeleteResult delete(Analysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("pgcName", WhereType.Equal, analysis.getPgcName());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        return analysisDao.delete(where);
    }

    @Override
    public UpdateResult AddSpiderCount(Analysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("pgcName", WhereType.Equal, analysis.getPgcName());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("pgcId",analysis.getPgcId());
        fields.put("site",analysis.getSite());
        fields.put("pgcName",analysis.getPgcName());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastSpiderDate",analysis.getLastSpiderDate());
        Map<String, Number> map = new HashMap<>();
        map.put("spiderCount",1);
        return analysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddSpiderVideoCount(Analysis analysis, int count) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("pgcName", WhereType.Equal, analysis.getPgcName());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("pgcId",analysis.getPgcId());
        fields.put("site",analysis.getSite());
        fields.put("pgcName",analysis.getPgcName());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastSpiderDate",analysis.getLastSpiderDate());
        Map<String, Number> map = new HashMap<>();
        map.put("spiderVideoCount",count);
        return analysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddSucSpiderCount(Analysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("pgcName", WhereType.Equal, analysis.getPgcName());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("pgcId",analysis.getPgcId());
        fields.put("site",analysis.getSite());
        fields.put("pgcName",analysis.getPgcName());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastSpiderDate",analysis.getLastSpiderDate());
        Map<String, Number> map = new HashMap<>();
        map.put("sucSpiderCount",1);
        return analysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddFailSpiderCount(Analysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("pgcName", WhereType.Equal, analysis.getPgcName());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("pgcId",analysis.getPgcId());
        fields.put("site",analysis.getSite());
        fields.put("pgcName",analysis.getPgcName());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastSpiderDate",analysis.getLastSpiderDate());
        Map<String, Number> map = new HashMap<>();
        map.put("failSpiderCount",1);
        return analysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddDownloadCount(Analysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("pgcName", WhereType.Equal, analysis.getPgcName());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("pgcId",analysis.getPgcId());
        fields.put("site",analysis.getSite());
        fields.put("pgcName",analysis.getPgcName());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastDownloadDate",analysis.getLastDownloadDate());
        Map<String, Number> map = new HashMap<>();
        map.put("downloadCount",1);
        return analysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddSucDownloadCount(Analysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("pgcName", WhereType.Equal, analysis.getPgcName());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("pgcId",analysis.getPgcId());
        fields.put("site",analysis.getSite());
        fields.put("pgcName",analysis.getPgcName());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastDownloadDate",analysis.getLastDownloadDate());
        Map<String, Number> map = new HashMap<>();
        map.put("sucDownloadCount",1);
        return analysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddFailDownloadCount(Analysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("pgcName", WhereType.Equal, analysis.getPgcName());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("pgcId",analysis.getPgcId());
        fields.put("site",analysis.getSite());
        fields.put("pgcName",analysis.getPgcName());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastDownloadDate",analysis.getLastDownloadDate());
        Map<String, Number> map = new HashMap<>();
        map.put("failDownloadCount",1);
        return analysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddSucSynCount(Analysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("pgcName", WhereType.Equal, analysis.getPgcName());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("pgcId",analysis.getPgcId());
        fields.put("site",analysis.getSite());
        fields.put("pgcName",analysis.getPgcName());
        fields.put("dateTime",analysis.getDateTime());
        Map<String, Number> map = new HashMap<>();
        map.put("sucSynCount",1);
        return analysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddFailSynCount(Analysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("pgcName", WhereType.Equal, analysis.getPgcName());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("pgcId",analysis.getPgcId());
        fields.put("site",analysis.getSite());
        fields.put("pgcName",analysis.getPgcName());
        fields.put("dateTime",analysis.getDateTime());
        Map<String, Number> map = new HashMap<>();
        map.put("failSynCount",1);
        return analysisDao.inc(fields,map,where,true);
    }
}
