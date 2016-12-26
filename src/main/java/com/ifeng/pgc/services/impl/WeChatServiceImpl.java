/*
* WeChatServiceImpl.java 
* Created on  202016/12/8 16:47 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.services.Service;

import java.util.List;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
@org.springframework.stereotype.Service(value = "weChatServiceImpl")
public class WeChatServiceImpl implements Service {
    @Override
    public void flush(Object o, String pgcName, String pgcId, int priority) throws Exception {

    }

    @Override
    public Object flush(Object o, String pgcName, String pgcId, int priority, boolean upset) throws Exception {
        return null;
    }

    @Override
    public Object get() throws Exception {
        return null;
    }

    @Override
    public int update(Object o) throws Exception {
        return 0;
    }

    @Override
    public int updateOneById(Object o) throws Exception {
        return 0;
    }

    @Override
    public int updateOneByUrl(Object o) throws Exception {
        return 0;
    }

    @Override
    public List selectUnSyncList() throws Exception {
        return null;
    }

    @Override
    public List<String> buildUrls() throws Exception {
        return null;
    }
}
