package com.ifeng.pgc.quartzJobs;

import com.ifeng.pgc.core.AbsHtmlJob;
import com.ifeng.pgc.core.BeanLoader;
import com.ifeng.pgc.core.Context;
import com.ifeng.pgc.persistence.IFlush;
import com.ifeng.pgc.services.VideoService;
import com.ifeng.pgc.utils.DateUtil;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;

import java.util.Date;
import java.util.List;

/**
 * Created by zhanglr on 2016/6/12.
 */
public class NormalSpiderHtmlJob extends AbsHtmlJob {
    private IFlush flusher;
    private VideoService videoService;

    public NormalSpiderHtmlJob() {
        super(null);
    }

    @Override
    public void doExecute(Context context) throws Exception {
        try {
            ResultItems item = (ResultItems) context.get("resultItems");
            flusher = BeanLoader.getBean(serviceName);
            videoService = BeanLoader.getBean("videoServiceImpl");
            List temp = item.get(className);
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
            if (temp != null && temp.size() >= 1) {
                int count = (Integer) flusher.flush(temp, pgcName, pgcId, priority, true);
                analysisService.AddSucSpiderCount(analysis);
                analysisService.AddSpiderVideoCount(analysis, count);
                siteAnalysisService.AddSucSpiderCount(siteAnalysis);
                siteAnalysisService.AddSpiderVideoCount(siteAnalysis, count);
            } else {
                analysisService.AddFailSpiderCount(analysis);
                siteAnalysisService.AddFailSpiderCount(siteAnalysis);
            }
            videoService.updateSpideDateById(listPageId, "success");
        } catch (Exception e) {
            analysisService.AddFailSpiderCount(analysis);
            siteAnalysisService.AddFailSpiderCount(siteAnalysis);
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
            siteAnalysisService.AddFailSpiderCount(siteAnalysis);
            analysisService.AddFailSpiderCount(analysis);
            videoService = BeanLoader.getBean("videoServiceImpl");
            videoService.updateSpideDateById(request.getExtra("id").toString(), "faild");
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    protected List<Request> doBuildUrls() throws Exception {
        List<Request> list = super.doBuildUrls();
        switch (siteName) {
            case "今日头条":
                list.forEach((final Request request) -> {
                    request.putExtra("referer", request.getUrl().replace("?page_type=0", ""));
                });
                break;
        }
        return list;
    }
}
