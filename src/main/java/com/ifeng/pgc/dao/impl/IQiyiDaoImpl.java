package com.ifeng.pgc.dao.impl;

import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.mongo.*;
import com.ifeng.pgc.dao.IQiyiDao;
import com.ifeng.pgc.utils.DateUtil;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by zhusy on 2016/9/1.
 */
@Repository
public class IQiyiDaoImpl implements IQiyiDao {


    private final static String COLLECTION_NAME = "IQIYI";
    private MongoCli client;

    public IQiyiDaoImpl() {
        client = MongoFactory.newInstance("spider");
    }

    @Override
    public void saveBatch(List<VideoEntityInfo> list) throws Exception {
        List<VideoEntityInfo> result = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                VideoEntityInfo v = new VideoEntityInfo();
                v.setId(UUID.randomUUID().toString());
                v.setTitle(list.get(i).getTitle());
                v.setUrl(list.get(i).getUrl());
                v.setCreateTime(DateUtil.now());
                v.setCategory(list.get(i).getCategory());
                v.setStatus(0);
                v.setUpdateStatus(0);
                v.setPriority(list.get(i).getPriority());
                v.setPgcName(list.get(i).getPgcName());
                result.add(v);
            }
        }
        client.getCollection(COLLECTION_NAME);
        client.insert(result,new Date());
    }

    @Override
    public Object saveBatch(List<VideoEntityInfo> list, boolean upset) throws Exception {
        if (list != null) {
            client.getCollection(COLLECTION_NAME);
            if (upset) {
                int count = 0;
                for (int i = 0; i < list.size(); i++) {
                    MongoSelect select = new MongoSelect();
                    select.where("url", list.get(i).getUrl());
                    select.where("title",list.get(i).getTitle());
                    VideoEntityInfo v = client.selectOne(select, VideoEntityInfo.class);
                    if (v == null) {
                        count++;
                        client.insert(list.get(i),new Date());
                    }
                }
                return count;
            }else{
                saveBatch(list);
                return list.size();
            }
        }
        return 0;
    }

    public VideoEntityInfo selectOneByUrl(String url) throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.selectOne(new MongoSelect().where("url", url), VideoEntityInfo.class);
        //return sqlSessionTemplate.selectOne(SELECT_ONE_TOUTIAO_BY_URL, url);
    }

    @Override
    public VideoEntityInfo selectOne() throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.selectOne(new MongoSelect().where("comments", WhereType.NotEqual, ""), VideoEntityInfo.class);
    }

    @Override
    public List<VideoEntityInfo> selectList() throws Exception {
        client.getCollection(COLLECTION_NAME);
        MongoSelect select = new MongoSelect();
        select.where("updateStatus", WhereType.NotEqual, 3)
                .where("playstat", "")
                .orderBy("priority", OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select, VideoEntityInfo.class);

    }

    @Override
    public int updateOne(VideoEntityInfo en) throws Exception {
        client.getCollection(COLLECTION_NAME);
        Where where = new Where();
        where.and("url", en.getUrl());
        Map<String, Object> fields = new HashMap<>();
        fields.put("keywords", en.getKeywords());
        fields.put("author", en.getAuthor());
        fields.put("category", en.getCategory());
        fields.put("comments", en.getComments());
        fields.put("playstat", en.getPlaystat());
        fields.put("favstat", en.getFavstat());
        fields.put("upstat", en.getUpstat());
        fields.put("duration", en.getDuration());
        fields.put("downstat", en.getDownstat());
        fields.put("updateStatus", en.getUpdateStatus());
        client.update(fields, where);
        return 1;
    }

    @Override
    public int updateOneById(VideoEntityInfo en) throws Exception {
        client.getCollection(COLLECTION_NAME);
        Where where = new Where();
        where.and("id", en.getId());
        Map<String, Object> fields = new HashMap<>();
        fields.put("duration",en.getDuration());
        fields.put("fileLocation", en.getFileLocation());
        fields.put("status", en.getStatus());
        fields.put("imgLocation", en.getImgLocation());
        client.update(fields, where);
        return 1;
    }

    @Override
    public int updateOneByUrl(VideoEntityInfo en) throws Exception {
        client.getCollection(COLLECTION_NAME);
        Where where = new Where();
        where.and("url", en.getUrl());
        Map<String, Object> fields = new HashMap<>();
        fields.put("updateStatus", en.getUpdateStatus());
        client.update(fields, where);
        return 1;
    }

    @Override
    public List<VideoEntityInfo> selectUnSyncList() throws Exception {
        client.getCollection(COLLECTION_NAME);
        MongoSelect select = new MongoSelect();
        select.where("status", 1)
                //.where("updateStatus", 1)
                .orderBy("priority", OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select, VideoEntityInfo.class);
    }
}
