package com.ifeng.pgc.quartzJobs;

import com.ifeng.pgc.core.AbsJsonJob;
import com.ifeng.pgc.core.BeanLoader;
import com.ifeng.pgc.core.Context;
import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;
import com.ifeng.pgc.persistence.IFlush;
import com.ifeng.pgc.services.VideoService;
import com.ifeng.pgc.utils.DateUtil;
import net.minidev.json.JSONObject;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanglr on 2016/6/12.
 */
public class NormalPageSpiderJsonJob extends AbsJsonJob {
    private IFlush flusher;
    private VideoService videoService;
    public NormalPageSpiderJsonJob() {
        super(null);
    }

    @Override
    public void doExecute(Context context) throws Exception {
        try {
            ResultItems item = (ResultItems) context.get("resultItems");
            flusher = BeanLoader.getBean(serviceName);
            List list = new ArrayList<>();
            videoService = BeanLoader.getBean("videoServiceImpl");
            String listPageId = item.getRequest().getExtra("id") == null ? "0" : item.getRequest().getExtra("id").toString();
            String pgcName = item.getRequest().getExtra("pgcName") == null ? "0" : item.getRequest().getExtra("pgcName").toString();
            String pgcId = item.getRequest().getExtra("pgcId") == null ? "" : item.getRequest().getExtra("pgcId").toString();
            String siteName = item.getRequest().getExtra("siteName") == null ? "" : item.getRequest().getExtra("siteName").toString();
            int priority = item.getRequest().getExtra("priority") == null ? 0 : Integer.valueOf(item.getRequest().getExtra("priority").toString());
            analysis.setPgcId(pgcId);
            analysis.setPgcName(pgcName);
            analysis.setSite(siteName);
            analysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            analysis.setDateTime(DateUtil.formatDateHour(new Date()));
            siteAnalysis.setSite(siteName);
            siteAnalysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            siteAnalysis.setDateTime(DateUtil.formatDateHour(new Date()));
            if (jsonProperties != null) {
                List temp = item.get(jsonProperties.Name());
                try {
                    for (int i = 0; i < temp.size(); i++) {
                        EntryCodec entyCodec = clazz.newInstance();
                        DataLoader dl = new DataLoader((JSONObject) temp.get(i));
                        entyCodec.decode(dl);

                        list.add(entyCodec);
                    }
                } catch (InstantiationException e) {
                    //log.error(e);
                } catch (IllegalAccessException e) {
                    //log.error(e);
                }

            } else {
                list.addAll(item.get(Class.forName(className).getName()));
            }
            try {
                int count = (Integer) flusher.flush(list, pgcName, pgcId, priority, true);
                analysisService.AddSucSpiderCount(analysis);
                analysisService.AddSpiderVideoCount(analysis, count);
                siteAnalysisService.AddSucSpiderCount(siteAnalysis);
                siteAnalysisService.AddSpiderVideoCount(siteAnalysis, count);
                videoService.updateSpideDateById(listPageId, "success");
            } catch (Exception e) {
                analysisService.AddFailSpiderCount(analysis);
                siteAnalysisService.AddFailSpiderCount(siteAnalysis);
                throw e;
            }
        } catch (ClassNotFoundException e) {
            throw e;
        }
    }

    @Override
    public void doError(Context context) throws Exception {
        try {
            Request request = (Request) context.get("request");
            String pgcName = request.getExtra("pgcName") == null ? "0" : request.getExtra("pgcName").toString();
            String pgcId = request.getExtra("pgcId") == null ? "" : request.getExtra("pgcId").toString();
            String siteName = request.getExtra("siteName") == null ? "" : request.getExtra("siteName").toString();
            analysis.setPgcId(pgcId);
            analysis.setPgcName(pgcName);
            analysis.setSite(siteName);
            analysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            analysis.setDateTime(DateUtil.formatDateHour(new Date()));
            siteAnalysis.setSite(siteName);
            siteAnalysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            siteAnalysis.setDateTime(DateUtil.formatDateHour(new Date()));
            analysisService.AddFailSpiderCount(analysis);
            siteAnalysisService.AddFailSpiderCount(siteAnalysis);
            videoService = BeanLoader.getBean("videoServiceImpl");
            videoService.updateSpideDateById(request.getExtra("id").toString(), "faild");
        } catch (Exception e) {
            throw e;
        }
    }

}
