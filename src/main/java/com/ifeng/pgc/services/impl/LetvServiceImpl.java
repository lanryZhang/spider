package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.beans.LetvVideoInfo;
import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.IPageProcessor;
import com.ifeng.pgc.core.distribute.handlers.AuthReqHandler;
import com.ifeng.pgc.dao.LetvDao;
import com.ifeng.pgc.services.Service;
import com.ifeng.pgc.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhusy on 2016/9/2.
 */
@org.springframework.stereotype.Service(value = "LetvServiceImpl")
public class LetvServiceImpl implements Service ,IPageProcessor{
    private Logger log = Logger.getLogger(AuthReqHandler.class);
    @Autowired
    private LetvDao letvDao;

    @Override
    public String process(String rawText) {
        String json = "";
        if(rawText.startsWith("jQuery") && rawText.endsWith(")")){
            if(rawText.indexOf("(") <= rawText.length()-1){
                json = rawText.substring(rawText.indexOf("(")+1,rawText.length()-1);
            }
        } else if (rawText.startsWith("(") && rawText.endsWith(")")){
            json =  rawText.substring(1,rawText.length()-1);
        }
        return json;
    }

    @Override
    public void flush(Object o,String pgcName,String pgcId,int priority) throws Exception {
        List<LetvVideoInfo> list = (List<LetvVideoInfo>) o;
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
                v.setImgLocation(list.get(i).getImgPath());
                result.add(v);
            }
        }
        letvDao.saveBatch(result);
    }

    @Override
    public Object flush(Object o, String pgcName,String pgcId,int priority,boolean upset) throws Exception {
        List<LetvVideoInfo> list = (List<LetvVideoInfo>) o;
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
                v.setImgLocation(list.get(i).getImgPath());
                result.add(v);
            }
        }
        return letvDao.saveBatch(result, upset);
    }

    @Override
    public Object get() throws Exception {
        return letvDao.selectList();
    }

    @Override
    public int update(Object o) throws Exception {
        return letvDao.updateOne((VideoEntityInfo) o);
    }

    @Override
    public int updateOneById(Object o) throws Exception {
        return letvDao.updateOneById((VideoEntityInfo) o);
    }

    @Override
    public int updateOneByUrl(Object o) throws Exception {
        return letvDao.updateOneByUrl((VideoEntityInfo) o);
    }

    @Override
    public List<VideoEntityInfo> selectUnSyncList() throws Exception {
        return letvDao.selectUnSyncList();
    }

    @Override
    public List<String> buildUrls() throws Exception {
        List<String> list;
        list = new ArrayList<>();
        for (VideoEntityInfo url : letvDao.selectList()) {
            list.add(url.getUrl().trim());
        }
        return list;
    }

}
