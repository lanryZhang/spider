/**
 * AnalysisServiceImpl.java
 * Created by zhusy on 2016/12/6 0006 15:14
 * Copyright © 2012 Phoenix New Media Limited All Rights Reserved
 */
package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.beans.SiteAnalysis;
import com.ifeng.pgc.core.mongo.MongoSelect;
import com.ifeng.pgc.core.mongo.Where;
import com.ifeng.pgc.core.mongo.WhereType;
import com.ifeng.pgc.dao.SiteAnalysisDao;
import com.ifeng.pgc.services.SiteAnalysisService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "siteAnalysisService")
public class SiteAnalysisServiceImpl implements SiteAnalysisService {

    @Autowired
    private SiteAnalysisDao siteAnalysisDao;

    @Override
    public void insertOne(SiteAnalysis analysis) throws Exception {
        siteAnalysisDao.insertOne(analysis);
    }

    @Override
    public void insertList(List<SiteAnalysis> analysisList) throws Exception {
        siteAnalysisDao.insertList(analysisList);
    }

    @Override
    public SiteAnalysis selectOne(MongoSelect select) throws Exception {
        return siteAnalysisDao.selectOne(select);
    }

    @Override
    public SiteAnalysis selectOne(String site, String dateTime) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, site);
        where.and("dateTime", WhereType.Equal, dateTime);
        return selectOne(new MongoSelect(where));
    }

    @Override
    public List<SiteAnalysis> selectList(MongoSelect select) throws Exception {
        return siteAnalysisDao.selectList(select);
    }

    @Override
    public UpdateResult update(Map<String, Object> map, Where where, boolean upsert) throws Exception {
        return siteAnalysisDao.update(map, where, upsert);
    }

    @Override
    public UpdateResult update(SiteAnalysis analysis, boolean upsert) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("site", analysis.getSite());
        map.put("lastSpiderDate", analysis.getLastSpiderDate());
        map.put("lastDownloadDate", analysis.getLastDownloadDate());
        map.put("lastSynDate", analysis.getLastSynDate());
        map.put("dateTime", analysis.getDateTime());
        map.put("SpiderVideoCount", analysis.getSpiderVideoCount());
        map.put("sucSpiderCount", analysis.getSucSpiderCount());
        map.put("failSpiderCount", analysis.getFailSpiderCount());
        map.put("sucDownloadCount", analysis.getSucDownloadCount());
        map.put("failDownloadCount", analysis.getFailDownloadCount());
        map.put("sucSynCount", analysis.getSucSynCount());
        map.put("failSynCount", analysis.getFailSynCount());
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        return siteAnalysisDao.update(map, where, true);
    }

    @Override
    public DeleteResult delete(Where where) throws Exception {
        return siteAnalysisDao.delete(where);
    }

    @Override
    public DeleteResult delete(SiteAnalysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        return siteAnalysisDao.delete(where);
    }


    @Override
    public UpdateResult AddSpiderVideoCount(SiteAnalysis analysis, int count) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("site",analysis.getSite());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastSpiderDate",analysis.getLastSpiderDate());
        Map<String, Number> map = new HashMap<>();
        map.put("spiderVideoCount",count);
        return siteAnalysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddSucSpiderCount(SiteAnalysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("site",analysis.getSite());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastSpiderDate",analysis.getLastSpiderDate());
        Map<String, Number> map = new HashMap<>();
        map.put("sucSpiderCount",1);
        return siteAnalysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddFailSpiderCount(SiteAnalysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("site",analysis.getSite());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastSpiderDate",analysis.getLastSpiderDate());
        Map<String, Number> map = new HashMap<>();
        map.put("failSpiderCount",1);
        return siteAnalysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddSucDownloadCount(SiteAnalysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("site",analysis.getSite());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastDownloadDate",analysis.getLastDownloadDate());
        Map<String, Number> map = new HashMap<>();
        map.put("sucDownloadCount",1);
        return siteAnalysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddFailDownloadCount(SiteAnalysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("site",analysis.getSite());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastDownloadDate",analysis.getLastDownloadDate());
        Map<String, Number> map = new HashMap<>();
        map.put("failDownloadCount",1);
        return siteAnalysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddSucSynCount(SiteAnalysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("site",analysis.getSite());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastSynDate",analysis.getLastSynDate());
        Map<String, Number> map = new HashMap<>();
        map.put("sucSynCount",1);
        return siteAnalysisDao.inc(fields,map,where,true);
    }

    @Override
    public UpdateResult AddFailSynCount(SiteAnalysis analysis) throws Exception {
        Where where = new Where();
        where.and("site", WhereType.Equal, analysis.getSite());
        where.and("dateTime", WhereType.Equal, analysis.getDateTime());
        Map<String, Object> fields = new HashMap<>();
        fields.put("site",analysis.getSite());
        fields.put("dateTime",analysis.getDateTime());
        fields.put("lastSynCount",analysis.getLastSynDate());
        Map<String, Number> map = new HashMap<>();
        map.put("failSynCount",1);
        return siteAnalysisDao.inc(fields,map,where,true);
    }
}
