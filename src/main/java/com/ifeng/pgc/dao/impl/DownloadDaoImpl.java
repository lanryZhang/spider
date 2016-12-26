package com.ifeng.pgc.dao.impl;

import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.mongo.*;
import com.ifeng.pgc.dao.DownloadDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhanglr on 2016/7/12.
 */
@Repository
public class DownloadDaoImpl implements DownloadDao {
    private MongoCli client;
    public DownloadDaoImpl(){
        client = MongoFactory.newInstance("spider");
    }
    @Override
    public List<VideoEntityInfo> getYouKuList() throws Exception {
        client.getCollection("YOUKU");
        MongoSelect select = new MongoSelect();
        select.where("status", 0)
//                .where("priority", WhereType.Equal,5)
                .orderBy("createTime",OrderByDirection.DESC)
                .orderBy("priority",OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select,VideoEntityInfo.class);
        //return sqlSessionTemplate.selectList(GET_YOUKU_VIDEO_LIST);
    }

    @Override
    public List<VideoEntityInfo> getMiaoPaiList() throws Exception {
        client.getCollection("MIAOPAI");
        MongoSelect select = new MongoSelect();
        select.where("status", 0)
//                .where("priority", WhereType.Equal,5)
                .orderBy("priority", OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select,VideoEntityInfo.class);
        //return sqlSessionTemplate.selectList(GET_MIAOPAI_VIDEO_LIST);
    }

    @Override
    public List<VideoEntityInfo> getJinRiTouTiaoList() throws Exception {
        client.getCollection("JINRITOUTIAO");
        MongoSelect select = new MongoSelect();
        select.where("status", 0)
                .where("priority", WhereType.Equal,5)
                .orderBy("createTime",OrderByDirection.DESC)
                .orderBy("priority", OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select,VideoEntityInfo.class);
        //return sqlSessionTemplate.selectList(GET_TOUTIAO_VIDEO_LIST);
    }

    @Override
    public List<VideoEntityInfo> getSohuList() throws Exception {
        client.getCollection("SOHU");
        MongoSelect select = new MongoSelect();
        select.where("status", 0)
//                .where("priority", WhereType.Equal,5)
                .orderBy("priority", OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select,VideoEntityInfo.class);
        //return sqlSessionTemplate.selectList(GET_SOHU_VIDEO_LIST);
    }

    @Override
    public List<VideoEntityInfo> getQQList() throws Exception {
        client.getCollection("QQ");
        MongoSelect select = new MongoSelect();
        select.where("status", 0)
//                .where("priority", WhereType.Equal,5)
                .orderBy("priority", OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select,VideoEntityInfo.class);
        //return sqlSessionTemplate.selectList(GET_QQ_VIDEO_LIST);
    }

    @Override
    public List<VideoEntityInfo> get56List() throws Exception {
        client.getCollection("VIDEO56");
        MongoSelect select = new MongoSelect();
        select.where("status", 0)
//                .where("priority", WhereType.Equal,5)
                .orderBy("priority", OrderByDirection.DESC)
                .page(1, 100);
        return client.selectList(select,VideoEntityInfo.class);
        //return sqlSessionTemplate.selectList(GET_56_VIDEO_LIST);
    }

    @Override
    public List<VideoEntityInfo> getIQiyiList() throws Exception {
        client.getCollection("IQIYI");
        MongoSelect select = new MongoSelect();
        select.where("status",0)
//                .where("priority", WhereType.Equal,5)
                .orderBy("priority", OrderByDirection.DESC)
                .page(1,100);
        return client.selectList(select,VideoEntityInfo.class);
    }

    @Override
    public List<VideoEntityInfo> getLetvList() throws Exception {
        client.getCollection("LETV");
        MongoSelect select = new MongoSelect();
        select.where("status",0)
//                .where("priority", WhereType.Equal,5)
                .orderBy("priority", OrderByDirection.DESC)
                .page(1,100);
        return client.selectList(select,VideoEntityInfo.class);
    }
}
