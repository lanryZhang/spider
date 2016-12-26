package com.ifeng.pgc.quartzJobs.pagejobs;

import com.ifeng.pgc.beans.QQVideoPageInfo_2;
import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.AbsHtmlJob;
import com.ifeng.pgc.core.BeanLoader;
import com.ifeng.pgc.core.Context;
import com.ifeng.pgc.services.Service;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglr on 2016/6/12.
 */
public class QQPageJob_ extends AbsHtmlJob {

    private Service<VideoEntityInfo> service;
    private Logger log = Logger.getLogger(QQPageJob_.class);
    public QQPageJob_() {
        super(QQVideoPageInfo_2.class);
    }

    @Override
    public void doError(Context context) {
        Request request = (Request) context.get("request");
        if (service == null) {
            service = BeanLoader.getBean(serviceName);
        }
        VideoEntityInfo mp = new VideoEntityInfo();
        mp.setUpdateStatus(3);
        mp.setUrl(request.getUrl());
        try {
            service.updateOneByUrl(mp);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void doExecute(Context context) {
        ResultItems item = (ResultItems) context.get("resultItems");
        try {
            service = BeanLoader.getBean(serviceName);
            QQVideoPageInfo_2 temp = item.get(QQVideoPageInfo_2.class.getName());
            if (temp != null) {
                VideoEntityInfo mp = new VideoEntityInfo();
                String keywords = "";
                if (temp.getKeywords().size() > 0) {
                    for (String str : temp.getKeywords()) {
                        keywords = keywords + str + ",";
                    }
                    mp.setKeywords(keywords.substring(0, keywords.length() - 1));
                }
                mp.setUrl(item.getRequest().getUrl());
                mp.setAuthor(temp.getAuthor());
                mp.setCategory(temp.getCategory());
                mp.setComments(temp.getComments());
                mp.setPlaystat(temp.getPlaystat());
                mp.setFavstat(temp.getFavstat());
                mp.setDownstat(temp.getDownstat());
                mp.setUpstat(temp.getUpstat());
                mp.setUpdateStatus(1);
               // if (mp.getPlaystat() != null)
                    service.update(mp);
            }
        } catch (Exception e) {
            log.error("Page download error:" + item.getRequest().getUrl());
            log.error(e);
        }
    }

    @Override
    protected List<Request> doBuildUrls() throws Exception {
        service = BeanLoader.getBean(serviceName);
        List<String> list = service.buildUrls();
        List<Request> result = new ArrayList<>();
        Request request;
        for (int i = 0 ;i < list.size();i++){
            request = new Request(list.get(i));
            result.add(request);
        }
        return result;
    }
}
