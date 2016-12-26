package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.beans.QQVideoInfo;
import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.dao.QQDao;
import com.ifeng.pgc.services.Service;
import com.ifeng.pgc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhanglr on 2016/6/27.
 */
@org.springframework.stereotype.Service(value = "qqServiceImpl")
public class QQServiceImpl implements Service{
    @Autowired
    private QQDao qqDao;
    @Override
    public void flush(Object o,String pgcName,String pgcId,int priority) throws Exception {
        List<QQVideoInfo> list = (List<QQVideoInfo>) o;
        List<VideoEntityInfo> result = new ArrayList<>();
        VideoEntityInfo v;
        if (list != null && list.size() > 0){
            for(int i =0 ;i < list.size();i++){
                v = new VideoEntityInfo();
                v.setId(UUID.randomUUID().toString());
                v.setUrl(list.get(i).getUrl().trim());
                v.setCategory(list.get(i).getCategory());
                v.setTitle(list.get(i).getTitle());
                v.setCreateTime(DateUtil.now());
                v.setPgcName(pgcName);
                v.setPgcId(pgcId);
                v.setStatus(0);
                v.setUpdateStatus(0);
                v.setPriority(priority);
                result.add(v);
            }
        }
        qqDao.saveBatch(result);
    }

    @Override
    public Object flush(Object o,String pgcName,String pgcId,int priority, boolean upset) throws Exception {
        List<QQVideoInfo> list = (List<QQVideoInfo>) o;
        List<VideoEntityInfo> result = new ArrayList<>();
        VideoEntityInfo v;
        if (list != null && list.size() > 0){
            for(int i =0 ;i < list.size();i++){
                v = new VideoEntityInfo();
                v.setId(UUID.randomUUID().toString());
                v.setUrl(list.get(i).getUrl());
                v.setCategory(list.get(i).getCategory());
                v.setTitle(list.get(i).getTitle());
                v.setCreateTime(DateUtil.now());
                v.setPgcName(pgcName);
                v.setPgcId(pgcId);
                v.setStatus(0);
                v.setUpdateStatus(0);
                v.setPriority(priority);
                result.add(v);
            }
        }
        return qqDao.saveBatch(result,upset);
    }

    @Override
    public Object get() throws Exception {
        return qqDao.selectList();
    }

    @Override
    public int update(Object o) throws Exception {
        return qqDao.updateOne((VideoEntityInfo) o);
    }

    @Override
    public int updateOneById(Object o) throws Exception {
        return qqDao.updateOneById((VideoEntityInfo) o);
    }

    @Override
    public int updateOneByUrl(Object o) throws Exception {
        return qqDao.updateOneByUrl((VideoEntityInfo) o);
    }

    @Override
    public List<VideoEntityInfo> selectUnSyncList() throws Exception {
        return qqDao.selectUnSyncList();
    }

    @Override
    public List<String> buildUrls() throws Exception {
        List<String> list = null;
        list = new ArrayList<String>();
        for (VideoEntityInfo url : qqDao.selectList()) {
            list.add(url.getUrl().trim());
        }
        return list;
    }
}
