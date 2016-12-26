package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.dao.MiaoPaiDao;
import com.ifeng.pgc.services.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglr on 2016/6/27.
 */
@org.springframework.stereotype.Service(value = "miaoPaiServiceImpl")
public class MiaoPaiServiceImpl implements Service {
    @Autowired
    private MiaoPaiDao miaoPaiDao;

    @Override
    public void flush(Object o,String pgcName,String pgcId,int priority) throws Exception {
        List<VideoEntityInfo> result = (List<VideoEntityInfo>) o;
        miaoPaiDao.saveBatch(result);
    }

    @Override
    public Object flush(Object o,String pgcName,String pgcId,int priority, boolean upset) throws Exception {
        List<VideoEntityInfo> result = (List<VideoEntityInfo>) o;
        return miaoPaiDao.saveBatch(result,upset);
    }

    @Override
    public Object get() throws Exception {
        return miaoPaiDao.selectList();
    }

    @Override
    public int update(Object o) throws Exception {
        return miaoPaiDao.updateOne((VideoEntityInfo) o);
    }

    @Override
    public int updateOneById(Object o) throws Exception {
        return miaoPaiDao.updateOneById((VideoEntityInfo) o);
    }

    @Override
    public int updateOneByUrl(Object o) throws Exception {
        return miaoPaiDao.updateOneByUrl((VideoEntityInfo) o);
    }

    @Override
    public List<VideoEntityInfo> selectUnSyncList() throws Exception {
        return miaoPaiDao.selectUnSyncList();
    }

    @Override
    public List<String> buildUrls() throws Exception {
        List<String> list = null;
        list = new ArrayList<String>();
        for (VideoEntityInfo url : miaoPaiDao.selectList()) {
            list.add(url.getUrl().trim());
        }
        return list;
    }
}
