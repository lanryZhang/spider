package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.beans.SohuVideoInfo;
import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.dao.SohuDao;
import com.ifeng.pgc.services.Service;
import com.ifeng.pgc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhanglr on 2016/6/27.
 */
@org.springframework.stereotype.Service(value = "sohuServiceImpl")
public class SohuServiceImpl implements Service{
    @Autowired
    private SohuDao sohuDao;
    @Override
    public void flush(Object o,String pgcName,String pgcId, int priority) throws Exception {
        List<SohuVideoInfo> list = (List<SohuVideoInfo>) o;
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
        sohuDao.saveBatch(result);
    }

    @Override
    public Object flush(Object o,String pgcName,String pgcId,int priority, boolean upset) throws Exception {
        List<SohuVideoInfo> list = (List<SohuVideoInfo>) o;
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
        return sohuDao.saveBatch(result,upset);
    }

    @Override
    public Object get() throws Exception {
        return sohuDao.selectList();
    }

    @Override
    public int update(Object o) throws Exception {
        return sohuDao.updateOne((VideoEntityInfo) o);
    }

    @Override
    public int updateOneById(Object o) throws Exception {
        return sohuDao.updateOneById((VideoEntityInfo) o);
    }

    @Override
    public int updateOneByUrl(Object o) throws Exception {
        return sohuDao.updateOneByUrl((VideoEntityInfo) o);
    }

    @Override
    public List<VideoEntityInfo> selectUnSyncList() throws Exception {
        return sohuDao.selectUnSyncList();
    }

    @Override
    public List<String> buildUrls() throws Exception {
        List<String> list = null;
        list = new ArrayList<String>();
        for (VideoEntityInfo url : sohuDao.selectList()) {
            list.add(url.getUrl().trim());
        }
        return list;
    }
}
