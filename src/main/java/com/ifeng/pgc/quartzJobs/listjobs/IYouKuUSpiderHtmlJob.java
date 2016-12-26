package com.ifeng.pgc.quartzJobs.listjobs;

import com.ifeng.pgc.beans.IYouku_U_VideoInfo;
import com.ifeng.pgc.core.AbsHtmlJob;
import com.ifeng.pgc.core.BeanLoader;
import com.ifeng.pgc.core.Context;
import com.ifeng.pgc.persistence.IFlush;
import com.ifeng.pgc.services.VideoService;
import com.ifeng.pgc.utils.DateUtil;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanglr on 2016/6/12.
 */
public class IYouKuUSpiderHtmlJob extends AbsHtmlJob {
    private IFlush<List<IYouku_U_VideoInfo>> flusher;
    private List<IYouku_U_VideoInfo> list = new ArrayList<>();
    private VideoService videoService;
    public IYouKuUSpiderHtmlJob() {
        super(IYouku_U_VideoInfo.class);
    }
    private Logger log = Logger.getLogger(IYouKuUSpiderHtmlJob.class);
    @Override
    public void doExecute(Context context) {
        ResultItems item = (ResultItems) context.get("resultItems");
        try {
            flusher = BeanLoader.getBean("youKuServiceImpl");
            videoService = BeanLoader.getBean("videoServiceImpl");
            List<IYouku_U_VideoInfo> temp = item.get(IYouku_U_VideoInfo.class.getName());
            String listPageId = item.getRequest().getExtra("id") == null ? "0":item.getRequest().getExtra("id").toString();
            String pgcName = item.getRequest().getExtra("pgcName") == null ? "0":item.getRequest().getExtra("pgcName").toString();
            String pgcId = item.getRequest().getExtra("pgcId") == null ? "0":item.getRequest().getExtra("pgcId").toString();
            analysis.setPgcId(pgcId);
            analysis.setPgcName(pgcName);
            analysis.setSite("优酷");
            analysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            analysis.setDateTime(DateUtil.formatDateHour(new Date()));
            siteAnalysis.setSite("优酷");
            siteAnalysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            siteAnalysis.setDateTime(DateUtil.formatDateHour(new Date()));
            int priority = item.getRequest().getExtra("priority")== null ? 0:Integer.valueOf(item.getRequest().getExtra("priority").toString());
            if (temp != null && temp.size() >= 1) {
                int count = (Integer) flusher.flush(temp, pgcName, pgcId, priority, true);
                analysisService.AddSucSpiderCount(analysis);
                analysisService.AddSpiderVideoCount(analysis, count);
                siteAnalysisService.AddSucSpiderCount(siteAnalysis);
                siteAnalysisService.AddSpiderVideoCount(siteAnalysis, count);
            } else{
                analysisService.AddFailSpiderCount(analysis);
                siteAnalysisService.AddFailSpiderCount(siteAnalysis);
            }
            videoService.updateSpideDateById(listPageId,"success");
        } catch (Exception e) {
            log.error("List download error:" + item.getRequest().getUrl());
            log.error(e);
        }
    }
    @Override
    public  void doError(Context context){
        try {
            Request request = (Request) context.get("request");
            String pgcName = request.getExtra("pgcName") == null ? "0" : request.getExtra("pgcName").toString();
            String pgcId = request.getExtra("pgcId") == null ? "" : request.getExtra("pgcId").toString();
            analysis.setPgcId(pgcId);
            analysis.setPgcName(pgcName);
            analysis.setSite("优酷");
            analysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            analysis.setDateTime(DateUtil.formatDateHour(new Date()));
            siteAnalysis.setSite("优酷");
            siteAnalysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            siteAnalysis.setDateTime(DateUtil.formatDateHour(new Date()));
            siteAnalysisService.AddFailSpiderCount(siteAnalysis);
            analysisService.AddFailSpiderCount(analysis);
            videoService = BeanLoader.getBean("videoServiceImpl");
            videoService.updateSpideDateById(request.getExtra("id").toString(), "faild");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
