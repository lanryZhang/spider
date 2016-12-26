package com.ifeng.pgc.quartzJobs.downloadjobs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.BeanLoader;
import com.ifeng.pgc.core.AbsDownloadJob;
import com.ifeng.pgc.services.DownloadService;
import com.ifeng.pgc.services.Service;
import com.ifeng.pgc.utils.HttpUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanglr on 2016/7/12.
 */
public class JinRiTouTiaoDownloadJob extends AbsDownloadJob {
    private Logger log = Logger.getLogger(JinRiTouTiaoDownloadJob.class);
    private Service service = BeanLoader.getBean("jinRiTouTiaoServiceImpl");
    @Override
    public List<VideoEntityInfo> getVideoList() {
        try{
            DownloadService service = BeanLoader.getBean("downloadServiceImpl");
            return service.getJinRiTouTiaoList();
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
