package com.ifeng.pgc.quartzJobs.downloadjobs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.AbsDownloadJob;
import com.ifeng.pgc.core.BeanLoader;
import com.ifeng.pgc.services.DownloadService;
import com.ifeng.pgc.services.Service;
import com.ifeng.pgc.utils.HttpResult;
import com.ifeng.pgc.utils.HttpUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhusy on 2016/9/7.
 */
public class IQiyiDownloadJob extends AbsDownloadJob {
    private Logger log = Logger.getLogger(IQiyiDownloadJob.class);
    private Service service = BeanLoader.getBean("iQiyiServiceImpl");

    @Override
    public List<VideoEntityInfo> getVideoList() {
        try{
            DownloadService service = BeanLoader.getBean("downloadServiceImpl");
            return service.getIQiyiList();
        }catch (Exception e){
            log.error(e);
        }
        return null;
    }

    @Override
    public Map<String, Object> getRealUrl(String url) throws Exception {
        Map<String,Object> res = new HashMap<>();
        try {
            HttpResult result = HttpUtils.httpGet(String.format(urlFormat, URLEncoder.encode(url, "UTF-8"), "super"));
            JSONObject object = JSON.parseObject(result.getBody().toString());
            res.put("thumb",object.getString("thumb"));
            res.put("total_duration",object.getString("total_duration"));
            JSONArray inner = object.getJSONArray("V");
            List<String> list = new ArrayList<>();
            if (inner != null) {
                for (int i = 0; i < inner.size(); i++) {
                    Object keyUrl = inner.getJSONObject(i).get("C");
                    if (keyUrl != null) {
                        String temp = keyUrl.toString();
                        result = HttpUtils.httpGet(temp);
                        JSONObject keyResult = JSON.parseObject(result.getBody().toString());
                        list.add(keyResult.getString("l"));
                    }
                }
            }
            res.put("vUrls",list);
            return res;
        }catch (Exception e){
            log.error(e);
        }
        return null;
    }

    @Override
    public void update(VideoEntityInfo en) {
        try {
           service.updateOneById(en);
        }catch (Exception e){
            log.error(e);
        }
    }
}
