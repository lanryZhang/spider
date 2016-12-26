package com.ifeng.pgc.dao.impl;

import com.ifeng.pgc.beans.EIPInfo;
import com.ifeng.pgc.core.mongo.MongoCli;
import com.ifeng.pgc.core.mongo.MongoFactory;
import com.ifeng.pgc.core.mongo.MongoSelect;
import com.ifeng.pgc.core.mongo.OrderByDirection;
import com.ifeng.pgc.dao.EIPDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhanglr on 2016/9/5.
 */
@Repository
public class EIPDaoImpl implements EIPDao {
    private final static String COLLECTION_NAME = "EIP";
    MongoCli cli;
    public EIPDaoImpl(){
        cli = MongoFactory.newInstance("spider");
    }
    @Override
    public EIPInfo getEIpInfo(String hostIp) throws Exception {
        cli.getCollection(COLLECTION_NAME);
        MongoSelect select = new MongoSelect();
        select.where("hostIp", hostIp)
                .orderBy("createTime", OrderByDirection.DESC);
        return cli.selectOne(select, EIPInfo.class);
    }

    @Override
    public void insert(EIPInfo en) throws Exception {
        cli.getCollection(COLLECTION_NAME);
        cli.insert(en);
    }

    @Override
    public List<String> getAllHostIp() throws Exception {
        cli.getCollection(COLLECTION_NAME);
        return cli.distinct(new MongoSelect().addField("hostIp"),String.class);
    }
}
