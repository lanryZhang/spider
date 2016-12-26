package com.ifeng.pgc.dao.impl;

import com.ifeng.pgc.beans.UrlEntity;
import com.ifeng.pgc.core.mongo.*;
import com.ifeng.pgc.dao.VideoDao;
import com.ifeng.pgc.utils.DateUtil;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by zhanglr on 2016/7/30.
 */
@Repository
public class VideoDaoImpl implements VideoDao {
    private final static String COLLECTION_NAME = "LIST_URLS";
    private MongoCli client;
    public VideoDaoImpl(){
        client = MongoFactory.newInstance("spider");
    }
    @Override
    public List<UrlEntity> selectListUrls(String date, String site) throws Exception {
        client.getCollection(COLLECTION_NAME);
        MongoSelect select = new MongoSelect();
        select.where("spideDate", WhereType.LessThan,date)
                .where("site", site)
                .where("status",1)
                .orderBy("spideDate", OrderByDirection.ASC)
                .orderBy("priority", OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select,UrlEntity.class);
    }

    @Override
    public void updateSpideDateById(String id, String lastSpideStatus) throws Exception {
        client.getCollection(COLLECTION_NAME);
        Where where = new Where();
        where.and("id", id);
        Map<String,Object> fields = new HashMap<>();
        fields.put("spideDate",DateUtil.now());
        fields.put("lastSpideStatus",lastSpideStatus);
        if (lastSpideStatus.equals("success")) {
            fields.put("spideType", 0);
        }
        client.update(fields, where);
    }
}
