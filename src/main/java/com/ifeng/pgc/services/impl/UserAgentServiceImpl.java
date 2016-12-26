package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.beans.UserAgentInfo;
import com.ifeng.pgc.dao.UserAgentDao;
import com.ifeng.pgc.services.UserAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanglr on 2016/8/25.
 */
@Service(value = "userAgentServiceImpl")
public class UserAgentServiceImpl implements UserAgentService {
    @Autowired
    private UserAgentDao userAgentDao;
    @Override
    public void insert(UserAgentInfo userAgentInfo) throws Exception {
        userAgentDao.insert(userAgentInfo);
    }

    @Override
    public UserAgentInfo selectOne(int page) throws Exception {
        return userAgentDao.selectOne(page);
    }

    @Override
    public long count() throws Exception {
        return userAgentDao.count();
    }
}
