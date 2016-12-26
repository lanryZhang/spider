package com.ifeng.pgc.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifeng.pgc.beans.JinRiTouTiaoVideoInfo;
import com.ifeng.pgc.beans.UrlEntity;
import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.IPageDealer;
import com.ifeng.pgc.dao.JinRiTouTiaoDao;
import com.ifeng.pgc.services.Service;
import com.ifeng.pgc.utils.DateUtil;
import com.ifeng.pgc.utils.NativeAsciiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhanglr on 2016/6/14.
 */
@org.springframework.stereotype.Service(value = "jinRiTouTiaoServiceImpl")
public class JinRiTouTiaoServiceImpl implements Service, IPageDealer {

    @Autowired
    private JinRiTouTiaoDao jinRiTouTiaoDao;

    @Override
    public void flush(Object o, String pgcName, String pgcId, int priority) throws Exception {
        List<JinRiTouTiaoVideoInfo> list = (List<JinRiTouTiaoVideoInfo>) o;
        List<VideoEntityInfo> result = new ArrayList<>();
        VideoEntityInfo v;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
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
        jinRiTouTiaoDao.saveBatch(result);
    }

    @Override
    public Object flush(Object o, String pgcName, String pgcId, int priority, boolean upset) throws Exception {
        List<JinRiTouTiaoVideoInfo> list = (List<JinRiTouTiaoVideoInfo>) o;
        List<VideoEntityInfo> result = new ArrayList<>();
        VideoEntityInfo v;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
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
        return jinRiTouTiaoDao.saveBatch(result, true);
    }

    @Override
    public Object get() throws Exception {
        return jinRiTouTiaoDao.selectList();
    }

    @Override
    public int update(Object o) throws Exception {
        return jinRiTouTiaoDao.updateOne((VideoEntityInfo) o);
    }

    @Override
    public int updateOneById(Object o) throws Exception {
        return jinRiTouTiaoDao.updateOneById((VideoEntityInfo) o);
    }

    @Override
    public int updateOneByUrl(Object o) throws Exception {
        return jinRiTouTiaoDao.updateOneByUrl((VideoEntityInfo) o);
    }

    @Override
    public List<VideoEntityInfo> selectUnSyncList() throws Exception {
        return jinRiTouTiaoDao.selectUnSyncList();
    }

    @Override
    public List<String> buildUrls() throws Exception {
        List<String> list = null;
        list = new ArrayList<String>();
        for (VideoEntityInfo url : jinRiTouTiaoDao.selectList()) {
            list.add(url.getUrl().trim());
        }
        return list;
    }

    @Override
    public void deal(Page page, Spider spider) {
        String rawText = page.getRawText();
        rawText = NativeAsciiUtils.ascii2Native(rawText);
        JSONObject jsonObject = JSON.parseObject(rawText);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        String max_behot_time = "0";
        try {
            max_behot_time = jsonObject.getJSONObject("next").getString("max_behot_time");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json = new JSONObject();
        json.put("list", jsonArray);
        page.setRawText(json.toString());
        if ("1".equals(page.getRequest().getExtra("spideType").toString())) {
            if ("1".equals(max_behot_time)) {
                String urlFormat = "http://www.toutiao.com/pgc/ma/?media_id=%s&page_type=0&max_behot_time=%s&as=479BB4B7254C150";
                String curUrl = page.getRequest().getUrl();
                String id = curUrl.substring(curUrl.indexOf("id=") + 3, curUrl.indexOf("&m"));
                String url = String.format(urlFormat, id, max_behot_time);
                Request request = new Request();
                request.putExtra("id", page.getRequest().getExtra("id"));
                request.putExtra("pgcName", page.getRequest().getExtra("pgcName"));
                request.putExtra("priority", page.getRequest().getExtra("priority"));
                request.putExtra("spideType", 1);
                request.setUrl(url);
                spider.addRequest(request);
            }
        }
    }

    @Override
    public void reBuildUrl(List<UrlEntity> urlList) {
        String urlFormat = "http://www.toutiao.com/pgc/ma/?media_id=%s&page_type=0&max_behot_time=0&as=479BB4B7254C150";
        for (int i = 0; i < urlList.size(); i++) {
            String urlTemp = urlList.get(i).getUrl().trim();
            if (urlTemp.contains("/m") && urlTemp.contains("/?")) {
                String id = urlTemp.substring(urlTemp.indexOf("/m") + 2, urlTemp.indexOf("/?"));
                urlList.get(i).setUrl(String.format(urlFormat, id));
            }
        }
    }
}
