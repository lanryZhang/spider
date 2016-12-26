package com.ifeng.pgc.dao.impl;

import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.mongo.*;
import com.ifeng.pgc.dao.JinRiTouTiaoDao;
import com.ifeng.pgc.utils.DateUtil;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by zhanglr on 2016/6/14.
 */
@Repository
public class JinRiTouTiaoDaoImpl implements JinRiTouTiaoDao {
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//    private final static String SAVE_BATCH_JINRITOUTIAO = "save_batch_jinritoutiao";
//    private final static String UPDATE_ONE_TOUTIAO_BY_ID = "update_one_toutiao_by_id";
//    private final static String UPDATE_ONE_TOUTIAO_BY_URL = "update_one_toutiao_by_url";
//    private final static String SELECT_ONE_TOUTIAO = "select_one_toutiao";
//    private final static String SELECT_LIST_TOUTIAO = "select_list_toutiao";
//    private final static String UPDATE_ONE_TOUTIAO = "update_one_toutiao";
//    private final static String SELECT_ONE_TOUTIAO_BY_URL = "select_one_toutiao_by_url";
//    private final static String INSERT_ONE_TOUTIAO_VIDEO = "insert_one_toutiao_video";
//    private final static String SELECT_UNSYNC_TOUTIAO_LIST = "select_unsync_toutiao_list";

    private final static String COLLECTION_NAME = "JINRITOUTIAO";
    private MongoCli client;

    public JinRiTouTiaoDaoImpl() {
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
        client.insert(result, new Date());
        //sqlSessionTemplate.insert(SAVE_BATCH_JINRITOUTIAO, list);
    }

    @Override
    public Object saveBatch(List<VideoEntityInfo> list, boolean upset) throws Exception {
        int count = 0;
        if (list != null) {
            client.getCollection(COLLECTION_NAME);
            if (upset) {
                for (int i = 0; i < list.size(); i++) {
                    MongoSelect select = new MongoSelect();
                    select.where("url", list.get(i).getUrl());
                    select.where("title", list.get(i).getTitle());
                    VideoEntityInfo v = client.selectOne(select, VideoEntityInfo.class);
                    if (v == null) {
                        count++;
                        client.insert(list.get(i), new Date());
                    }
                }
            } else {
                saveBatch(list);
                count = list.size();
            }
        }
        return count;
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
        //return sqlSessionTemplate.selectOne(SELECT_ONE_TOUTIAO);
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

        // return sqlSessionTemplate.selectList(SELECT_LIST_TOUTIAO);
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
        fields.put("duration", en.getDuration());
        fields.put("upstat", en.getUpstat());
        fields.put("downstat", en.getDownstat());
        fields.put("updateStatus", en.getUpdateStatus());
        client.update(fields, where);
        return 1;
        // return sqlSessionTemplate.update(UPDATE_ONE_TOUTIAO, en);
    }

    @Override
    public int updateOneById(VideoEntityInfo en) throws Exception {
        client.getCollection(COLLECTION_NAME);
        Where where = new Where();
        where.and("id", en.getId());
        Map<String, Object> fields = new HashMap<>();
        fields.put("fileLocation", en.getFileLocation());
        fields.put("duration", en.getDuration());
        fields.put("status", en.getStatus());
        fields.put("imgLocation", en.getImgLocation());
        client.update(fields, where);
        return 1;
        //return sqlSessionTemplate.update(UPDATE_ONE_TOUTIAO_BY_ID, en);
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
        // return sqlSessionTemplate.selectList(SELECT_UNSYNC_TOUTIAO_LIST);
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
        //return sqlSessionTemplate.update(UPDATE_ONE_TOUTIAO_BY_URL, en);
    }

//    public SqlSessionTemplate getSqlSessionTemplate() {
//        return sqlSessionTemplate;
//    }
//
//    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
//        this.sqlSessionTemplate = sqlSessionTemplate;
//    }
}
