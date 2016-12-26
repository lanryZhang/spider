package com.ifeng.pgc.quartzJobs.listjobs;

import com.ifeng.pgc.beans.MiaoPaiVideoInfo;
import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.AbsJsonJob;
import com.ifeng.pgc.core.BeanLoader;
import com.ifeng.pgc.core.Context;
import com.ifeng.pgc.persistence.IFlush;
import com.ifeng.pgc.services.VideoService;
import com.ifeng.pgc.utils.DateUtil;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhanglr on 2016/6/14.
 */
public class MiaoPaiSpiderJsonJob extends AbsJsonJob {
    private IFlush<List<VideoEntityInfo>> flusher;
    private VideoService videoService;
    private Logger log = Logger.getLogger(MiaoPaiSpiderJsonJob.class);

    public MiaoPaiSpiderJsonJob() {
        super(MiaoPaiVideoInfo.class);
    }

    @Override
    public void doExecute(Context context) throws Exception{
        ResultItems item = (ResultItems) context.get("resultItems");
        try {
            flusher = BeanLoader.getBean(serviceName);
            videoService = BeanLoader.getBean("videoServiceImpl");
            Html html = Html.create(item.get("msg").toString());
            List<String> urls = html.xpath("//div[@class='D_video']/div[@class='list clearfix']/ol/li[3]/a/@href").all();
            String category = html.xpath("//div[@class='D_video']/div[@class='introduction']/div[@class='D_head_name']/h1/a/text()").get();
            List<String> title = html.xpath("//div[@class='D_video']/div[@class='introduction']/p[1]/text()").all();
            String listPageId = item.getRequest().getExtra("id") == null ? "0":item.getRequest().getExtra("id").toString();
            String pgcName = item.getRequest().getExtra("pgcName") == null ? "0":item.getRequest().getExtra("pgcName").toString();
            String pgcId = item.getRequest().getExtra("pgcId") == null ? "":item.getRequest().getExtra("pgcId").toString();
            analysis.setPgcId(pgcId);
            analysis.setPgcName(pgcName);
            analysis.setSite("秒拍");
            analysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            analysis.setDateTime(DateUtil.formatDateHour(new Date()));
            siteAnalysis.setSite("秒拍");
            siteAnalysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            siteAnalysis.setDateTime(DateUtil.formatDateHour(new Date()));
            int priority = item.getRequest().getExtra("priority")== null ? 0:Integer.valueOf(item.getRequest().getExtra("priority").toString());
            if (urls != null && urls.size() >= 1) {
                List<VideoEntityInfo> list = new ArrayList<>();
                for (int i = 0; i < urls.size();i++){
                    VideoEntityInfo mp = new VideoEntityInfo();
                    mp.setId(UUID.randomUUID().toString());
                    mp.setCategory(category);
                    mp.setUrl(urls.get(i));
                    mp.setTitle(title.get(i));
                    mp.setStatus(0);
                    mp.setUpdateStatus(0);
                    mp.setPriority(priority);
                    mp.setPgcName(pgcName);
                    mp.setPgcId(pgcId);
                    mp.setCreateTime(DateUtil.now());
                    mp.setPriority(priority);

                    list.add(mp);
                }
                int count = (Integer) flusher.flush(list, pgcName, pgcId, priority, true);
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
            analysisService.AddFailSpiderCount(analysis);
            siteAnalysisService.AddFailSpiderCount(siteAnalysis);
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
            analysis.setSite("秒拍");
            analysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            analysis.setDateTime(DateUtil.formatDateHour(new Date()));
            siteAnalysis.setSite("秒拍");
            siteAnalysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            siteAnalysis.setDateTime(DateUtil.formatDateHour(new Date()));
            videoService = BeanLoader.getBean("videoServiceImpl");
            videoService.updateSpideDateById(request.getExtra("id").toString(), "faild");
            analysisService.AddFailSpiderCount(analysis);
            siteAnalysisService.AddFailSpiderCount(siteAnalysis);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
