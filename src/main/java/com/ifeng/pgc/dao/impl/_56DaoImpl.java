package com.ifeng.pgc.dao.impl;

import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.mongo.*;
import com.ifeng.pgc.dao._56Dao;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanglr on 2016/6/27.
 */
@Repository
public class _56DaoImpl implements _56Dao {

    public _56DaoImpl(){
        client = MongoFactory.newInstance("spider");
    }
    private final static String COLLECTION_NAME = "VIDEO56";
    private MongoCli client;

    @Override
    public void saveBatch(List<VideoEntityInfo> list) throws Exception {
        client.getCollection(COLLECTION_NAME);
        client.insert(list,new Date());
//        sqlSessionTemplate.insert(SAVE_BATCH_56VIDEO,list);
    }

    @Override
    public Object saveBatch(List<VideoEntityInfo> list, boolean upset) throws Exception {
        int count = 0;
        client.getCollection(COLLECTION_NAME);
        if (list !=null){
            if (upset) {
                for (int i = 0; i < list.size(); i++) {
                    MongoSelect select = new MongoSelect();
                    select.where("url", list.get(i).getUrl());
                    select.where("title",list.get(i).getTitle());
                    VideoEntityInfo v = client.selectOne(select, VideoEntityInfo.class);
                    if (v == null) {
                        count ++;
                        client.insert(list.get(i),new Date());
                    }
                }
            }else {
                saveBatch(list);
                count = list.size();
            }
        }
        return count;
    }
    public VideoEntityInfo selectOneByUrl(String url) throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.selectOne(new MongoSelect().where("url",url),VideoEntityInfo.class);
        //return sqlSessionTemplate.selectOne(SELECT_ONE_56_BY_URL, url);
    }

    @Override
    public VideoEntityInfo selectOne() throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.selectOne(new MongoSelect().where("comments", WhereType.NotEqual,""),VideoEntityInfo.class);
        //return sqlSessionTemplate.selectOne(SELECT_ONE_56VIDEO);
    }

    @Override
    public int updateOne(VideoEntityInfo en) throws Exception {
        client.getCollection(COLLECTION_NAME);
        Where where = new Where();
        where.and("url", en.getUrl());
        Map<String,Object> fields = new HashMap<>();
        fields.put("keywords",en.getKeywords());
        fields.put("author",en.getAuthor());
        fields.put("category",en.getCategory());
        fields.put("comments",en.getComments());
        fields.put("playstat",en.getPlaystat());
        fields.put("favstat",en.getFavstat());
        fields.put("upstat",en.getUpstat());
        fields.put("duration", en.getDuration());
        fields.put("downstat",en.getDownstat());
        fields.put("updateStatus",en.getUpdateStatus());
        client.update(fields, where);
        return 1;
    }

    @Override
    public int updateOneById(VideoEntityInfo en) throws Exception {
        client.getCollection(COLLECTION_NAME);
        Where where = new Where();
        where.and("id", en.getId());
        Map<String,Object> fields = new HashMap<>();
        fields.put("fileLocation",en.getFileLocation());
        fields.put("status",en.getStatus());
        fields.put("imgLocation",en.getImgLocation());
        fields.put("duration",en.getDuration());
        client.update(fields, where);
        return 1;
        //return sqlSessionTemplate.update(UPDATE_ONE_56VIDEO_BY_ID,en);
    }

    @Override
    public int updateOneByUrl(VideoEntityInfo en) throws Exception {
        client.getCollection(COLLECTION_NAME);
        Where where = new Where();
        where.and("url", en.getUrl());
        Map<String,Object> fields = new HashMap<>();
        fields.put("updateStatus",en.getUpdateStatus());
        client.update(fields, where);
        return 1;
    }

    @Override
    public List<VideoEntityInfo> selectList() throws Exception {
        client.getCollection(COLLECTION_NAME);
        MongoSelect select = new MongoSelect();
        select.where("updateStatus",WhereType.NotEqual,3)
                .where("playstat", "")
                .orderBy("priority", OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select,VideoEntityInfo.class);
    }

    @Override
    public List<VideoEntityInfo> selectUnSyncList() throws Exception {
        client.getCollection(COLLECTION_NAME);
        MongoSelect select = new MongoSelect();
        select.where("status",1)
                //.where("updateStatus", 1)
                .orderBy("priority", OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select,VideoEntityInfo.class);
    }

}
