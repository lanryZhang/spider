package com.ifeng.pgc.services.impl;

import com.alibaba.fastjson.JSON;
import com.ifeng.pgc.beans.IQiyiVideoInfo;
import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.IPageProcessor;
import com.ifeng.pgc.core.distribute.handlers.AuthReqHandler;
import com.ifeng.pgc.dao.IQiyiDao;
import com.ifeng.pgc.services.Service;
import com.ifeng.pgc.utils.DateUtil;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhusy on 2016/9/1.
 */
@org.springframework.stereotype.Service(value = "iQiyiServiceImpl")
public class IQiyiServiceImpl implements Service, IPageProcessor {
    private Logger log = Logger.getLogger(AuthReqHandler.class);
    @Autowired
    private IQiyiDao iQiyiDao;

    @Override
    public String process(String rawText) {
        String htmlStr = "";
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (rawText != "") {
            htmlStr = JSON.parseObject(rawText).getString("data");
        }
//        htmlStr = NativeAsciiUtils.ascii2Native(htmlStr);
//        htmlStr = htmlStr.replace("\\r","");
//        htmlStr = htmlStr.replace("\\n","");
//        htmlStr = htmlStr.replace("\\","");
//        System.out.println(htmlStr);
        Html html = Html.create(htmlStr);
        List<String> list = html.xpath("//li[@j-delegate='colitem']").all();
        String category = html.xpath("//div[@class='pf_username']/span[@class='username']/text()").get();
        if(category == null){
            category = "";
        }
        for (int i = 0; i < list.size(); i++) {
            Html temp = Html.create(list.get(i));
            String title = temp.xpath("//div[@class='site-piclist_pic']/a/@data-title").get();
            String url = temp.xpath("//div[@class='site-piclist_pic']/a/@href").get();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("title",title);
                jsonObject.put("url",url);
                jsonObject.put("category",category);
            } catch (JSONException e) {
                log.error(e);
            }
            jsonArray.put(jsonObject);
        }
        try {
            json.put("list",jsonArray);
        } catch (JSONException e) {
            log.error(e);
        }
        return json.toString();
    }

    @Override
    public void flush(Object o, String pgcName,String pgcId, int priority) throws Exception {
        List<IQiyiVideoInfo> list = (List<IQiyiVideoInfo>) o;
        List<VideoEntityInfo> result = new ArrayList<>();
        VideoEntityInfo v;
        if (list != null && list.size() > 0) {
            for (IQiyiVideoInfo i : list) {
                v = new VideoEntityInfo();
                v.setId(UUID.randomUUID().toString());
                v.setUrl(i.getUrl());
                v.setCategory(i.getCategory());
                v.setTitle(i.getTitle());
                v.setCreateTime(DateUtil.now());
                v.setPgcName(pgcName);
                v.setPgcId(pgcId);
                v.setStatus(0);
                v.setUpdateStatus(0);
                v.setPriority(priority);
                v.setImgLocation(i.getImgPath());
                result.add(v);
            }
        }
        iQiyiDao.saveBatch(result);
    }

    @Override
    public Object flush(Object o, String pgcName,String pgcId, int priority, boolean upset) throws Exception {
        List<IQiyiVideoInfo> list = (List<IQiyiVideoInfo>) o;
        List<VideoEntityInfo> result = new ArrayList<>();
        VideoEntityInfo v;
        if (list != null && list.size() > 0) {
            for (IQiyiVideoInfo i : list) {
                v = new VideoEntityInfo();
                v.setId(UUID.randomUUID().toString());
                v.setUrl(i.getUrl().trim());
                v.setCategory(i.getCategory());
                v.setTitle(i.getTitle());
                v.setCreateTime(DateUtil.now());
                v.setPgcName(pgcName);
                v.setPgcId(pgcId);
                v.setStatus(0);
                v.setUpdateStatus(0);
                v.setPriority(priority);
                v.setImgLocation(i.getImgPath());
                result.add(v);
            }
        }
        return iQiyiDao.saveBatch(result, true);
    }

    @Override
    public Object get() throws Exception {
        return iQiyiDao.selectList();
    }

    @Override
    public int update(Object o) throws Exception {
        return iQiyiDao.updateOne((VideoEntityInfo) o);
    }

    @Override
    public int updateOneById(Object o) throws Exception {
        return iQiyiDao.updateOneById((VideoEntityInfo) o);
    }

    @Override
    public int updateOneByUrl(Object o) throws Exception {
        return iQiyiDao.updateOneByUrl((VideoEntityInfo) o);
    }

    @Override
    public List<VideoEntityInfo> selectUnSyncList() throws Exception {
        return iQiyiDao.selectUnSyncList();
    }

    @Override
    public List<String> buildUrls() throws Exception {
        List<String> list;
        list = new ArrayList<>();
        for (VideoEntityInfo url : iQiyiDao.selectList()) {
            list.add(url.getUrl().trim());
        }
        return list;
    }

}
