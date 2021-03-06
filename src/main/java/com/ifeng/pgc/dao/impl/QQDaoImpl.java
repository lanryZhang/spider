package com.ifeng.pgc.dao.impl;

import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.mongo.*;
import com.ifeng.pgc.dao.QQDao;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanglr on 2016/6/27.
 */
@Repository
public class QQDaoImpl implements QQDao {


//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//    private final static String SAVE_BATCH_QQ = "save_batch_qq";
//    private final static String SELECT_ONE_QQ = "select_one_qq";
//    private final static String SELECT_LIST_QQ = "select_list_qq";
//    private final static String INSERT_ONE_QQ_VIDEO = "insert_one_qq_video";
//    private final static String UPDATE_ONE_QQ = "update_one_qq";
//    private final static String UPDATE_ONE_QQ_BY_ID = "update_one_qq_by_id";
//    private final static String UPDATE_ONE_QQ_BY_URL = "update_one_qq_by_url";
//    private final static String SELECT_UNSYNC_QQ_LIST = "select_unsync_qq_list";
//    private final static String SELECT_ONE_QQ_BY_URL = "select_one_qq_by_url";

    private final static String COLLECTION_NAME = "QQ";
    private MongoCli client;

    public QQDaoImpl() {
        client = MongoFactory.newInstance("spider");
    }

    @Override
    public void saveBatch(List<VideoEntityInfo> list) throws Exception {
        client.getCollection(COLLECTION_NAME);
        client.insert(list,new Date());
        //sqlSessionTemplate.insert(SAVE_BATCH_QQ, list);
    }

    @Override
    public Object saveBatch(List<VideoEntityInfo> list, boolean upset) throws Exception {
        int count =0;
        if (list != null) {
            client.getCollection(COLLECTION_NAME);
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
            }else{
                saveBatch(list);
                count = list.size();
            }
        }
        return count;
    }


    public VideoEntityInfo selectOneByUrl(String url) throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.selectOne(new MongoSelect().where("url", url), VideoEntityInfo.class);
        //return sqlSessionTemplate.selectOne(SELECT_ONE_QQ_BY_URL, url);
    }

    @Override
    public VideoEntityInfo selectOne() throws Exception {
        client.getCollection(COLLECTION_NAME);
        return client.selectOne(new MongoSelect().where("comments", WhereType.NotEqual, ""), VideoEntityInfo.class);
        //return sqlSessionTemplate.selectOne(SELECT_ONE_QQ);
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
        fields.put("duration", en.getDuration());
        fields.put("favstat", en.getFavstat());
        fields.put("upstat", en.getUpstat());
        fields.put("downstat", en.getDownstat());
        fields.put("updateStatus", en.getUpdateStatus());
        client.update(fields, where);
        return 1;
        //return sqlSessionTemplate.update(UPDATE_ONE_QQ, en);
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
        //return sqlSessionTemplate.update(UPDATE_ONE_QQ_BY_ID, en);
    }

    @Override
    public List<VideoEntityInfo> selectList() throws Exception {
        client.getCollection(COLLECTION_NAME);
        MongoSelect select = new MongoSelect();
        Where where  = new Where();
        select.where("updateStatus", WhereType.NotEqual, 3)
                .where("playstat", "")
                .orderBy("priority", OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select, VideoEntityInfo.class);
        //return sqlSessionTemplate.selectList(SELECT_LIST_QQ);
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
        //return sqlSessionTemplate.update(UPDATE_ONE_QQ_BY_URL,en);
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
        //return sqlSessionTemplate.selectList(SELECT_UNSYNC_QQ_LIST);
    }

//    public SqlSessionTemplate getSqlSessionTemplate() {
//        return sqlSessionTemplate;
//    }
//
//    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
//        this.sqlSessionTemplate = sqlSessionTemplate;
//    }
}
