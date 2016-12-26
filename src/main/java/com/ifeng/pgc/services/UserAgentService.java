package com.ifeng.pgc.services;

import com.ifeng.pgc.beans.UserAgentInfo;

/**
 * Created by zhanglr on 2016/8/25.
 */
public interface UserAgentService {
    void insert(UserAgentInfo userAgentInfo) throws Exception;
    UserAgentInfo selectOne(int page) throws Exception;
    long count()  throws Exception;
}
