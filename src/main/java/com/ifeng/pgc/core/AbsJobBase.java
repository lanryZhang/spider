package com.ifeng.pgc.core;

import com.ifeng.pgc.beans.Analysis;
import com.ifeng.pgc.beans.SiteAnalysis;
import com.ifeng.pgc.beans.UrlEntity;
import com.ifeng.pgc.beans.UserAgentInfo;
import com.ifeng.pgc.services.AnalysisService;
import com.ifeng.pgc.services.IListUrlBuilder;
import com.ifeng.pgc.services.SiteAnalysisService;
import com.ifeng.pgc.services.UserAgentService;
import com.ifeng.pgc.utils.DateUtil;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.downloader.Downloader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanglr on 2016/6/16.
 */
public abstract class AbsJobBase extends AbsJobDescriptor implements IPageHandler {
    protected Class<?> clazz;
    protected int pageStartIndex;
    protected int pageEndIndex;
    protected String className;
    protected String serviceName;
    protected int sleepTime;
    protected int retrySleepTime;
    protected Downloader downloader;
    protected int timeout = 30000;
    protected AnalysisService analysisService;
    protected Analysis analysis = new Analysis();
    protected SiteAnalysisService siteAnalysisService;
    protected SiteAnalysis siteAnalysis = new SiteAnalysis();
    protected IListUrlBuilder listUrlBuilder;

    public void setiPageProcessor(IPageProcessor iPageProcessor) {
        this.iPageProcessor = iPageProcessor;
    }

    protected IPageProcessor iPageProcessor;
    protected IPageDealer iPageDealer;

    public void setiPageDealer(IPageDealer iPageDealer) {
        this.iPageDealer = iPageDealer;
    }

    protected List<Request> buildRequest() throws Exception {
        analysisService = BeanLoader.getBean("analysisService");
        siteAnalysisService = BeanLoader.getBean("siteAnalysisService");
        List<Request> requests = doBuildUrls();
        List<Request> result = new ArrayList<>();
        Request request;
        if (pageStartIndex > pageEndIndex) {
            throw new IndexOutOfBoundsException("分页起始页码不能大于结束页码");
        } else if (pageStartIndex > 0 && requests != null) {


            for (int k = 0; k < requests.size(); k++) {
                int pe = 1;
                if ((Integer)requests.get(k).getExtra("spideType") == 1){
                    pe = pageEndIndex;
                }
                for (int i = pageStartIndex; i <= pe; i++) {
                    String t = String.format(requests.get(k).getUrl(), i);
                    request = new Request();
                    request.putExtra("id", requests.get(k).getExtra("id"));
                    request.putExtra("pgcName", requests.get(k).getExtra("pgcName"));
                    request.putExtra("pgcId", requests.get(k).getExtra("pgcId"));
                    request.putExtra("spideType", requests.get(k).getExtra("spideType"));
                    request.putExtra("priority",requests.get(k).getExtra("priority"));
                    request.putExtra("siteName",requests.get(k).getExtra("siteName"));
                    analysis.setPgcId(requests.get(k).getExtra("pgcId").toString());
                    analysis.setPgcName(requests.get(k).getExtra("pgcName").toString());
                    analysis.setSite(requests.get(k).getExtra("siteName").toString().contains("优酷")?"优酷":requests.get(k).getExtra("siteName").toString());
                    analysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
                    analysis.setDateTime(DateUtil.formatDateHour(new Date()));
                    siteAnalysis.setSite(requests.get(k).getExtra("siteName").toString().contains("优酷")?"优酷":requests.get(k).getExtra("siteName").toString());
                    siteAnalysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
                    siteAnalysis.setDateTime(DateUtil.formatDateHour(new Date()));
                    analysisService.AddSpiderCount(analysis);
                    request.setUrl(t);
                    result.add(request);
                }
            }
        }
        totalSpiderUrlCount += requests.size() + result.size();
        return result.size() == 0 ? requests : result;
    }
    protected List<Request> doBuildUrls() throws Exception {
        List<UrlEntity> list = listUrlBuilder.selectListUrls(DateUtil.now(), siteName);
        if(iPageDealer != null){
            iPageDealer.reBuildUrl(list);
        }
        List<Request> result = new ArrayList<>();
        Request request;
        for (int i = 0 ;i < list.size();i++){
            System.out.println(list.get(i).getPgcName()+"\t"+list.get(i).getUrl());
            request = new Request(list.get(i).getUrl());
            request.putExtra("id",list.get(i).getId());
            request.putExtra("pgcName",list.get(i).getPgcName());
            request.putExtra("pgcId",list.get(i).getPgcId());
            request.putExtra("spideType",list.get(i).getSpideType());
            request.putExtra("priority",list.get(i).getPriority());
            request.putExtra("siteName",siteName);
            analysis.setPgcId(list.get(i).getPgcId());
            analysis.setPgcName(list.get(i).getPgcName());
            analysis.setSite(siteName.contains("优酷")?"优酷":siteName);
            analysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            analysis.setDateTime(DateUtil.formatDateHour(new Date()));
            siteAnalysis.setSite(siteName.contains("优酷")?"优酷":siteName);
            siteAnalysis.setLastSpiderDate(DateUtil.formatDateTime(new Date()));
            siteAnalysis.setDateTime(DateUtil.formatDateHour(new Date()));
            analysisService.AddSpiderCount(analysis);
            result.add(request);
        }
        return result;
    }

    public String getUserAgent() throws Exception {
        UserAgentService service = BeanLoader.getBean("userAgentService");
        long count = service.count();
        int id = (int) Math.floor(Math.random() * count + 1);
        UserAgentInfo ua = service.selectOne(id);
        return ua == null?"":ua.getUserAgent();
    }

    public void setListUrlBuilder(IListUrlBuilder listUrlBuilder) {
        this.listUrlBuilder = listUrlBuilder;
    }


    public void setRetrySleepTime(int retrySleepTime) {
        this.retrySleepTime = retrySleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }


    public void setPageEndIndex(int pageEndIndex) {
        this.pageEndIndex = pageEndIndex;
    }

    public void setPageStartIndex(int pageStartIndex) {
        this.pageStartIndex = pageStartIndex;
    }


    public void setClassName(String className) {
        this.className = className;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
