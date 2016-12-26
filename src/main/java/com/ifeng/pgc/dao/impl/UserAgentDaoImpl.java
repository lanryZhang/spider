package com.ifeng.pgc.dao.impl;

import com.ifeng.pgc.beans.UserAgentInfo;
import com.ifeng.pgc.core.mongo.MongoCli;
import com.ifeng.pgc.core.mongo.MongoFactory;
import com.ifeng.pgc.core.mongo.MongoSelect;
import com.ifeng.pgc.dao.UserAgentDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhanglr on 2016/8/25.
 */
@Repository
public class UserAgentDaoImpl implements UserAgentDao {
    private final static String COLLECTION_NAME = "USER_AGENTS";
    private MongoCli client;
    public UserAgentDaoImpl(){
        client = MongoFactory.newInstance("spider");
        client.getCollection(COLLECTION_NAME);
    }
    @Override
    public void insert(UserAgentInfo userAgentInfo) throws Exception {
        client.insert(userAgentInfo);
    }

    @Override
    public UserAgentInfo selectOne(int page) throws Exception {
        List<UserAgentInfo> list = client.selectList(new MongoSelect().page(page,1), UserAgentInfo.class);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public long count() throws Exception {
        return client.count();
    }
}
