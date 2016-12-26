package com.ifeng.pgc.quartzJobs.syncjob;

import com.ifeng.pgc.beans.Analysis;
import com.ifeng.pgc.beans.SiteAnalysis;
import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.BeanLoader;
import com.ifeng.pgc.services.AnalysisService;
import com.ifeng.pgc.services.Service;
import com.ifeng.pgc.services.SiteAnalysisService;
import com.ifeng.pgc.utils.DateUtil;
import com.ifeng.pgc.utils.HttpResult;
import com.ifeng.pgc.utils.HttpUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanglr on 2016/7/14.
 */
public class SyncPmopJob {
    private Logger log = Logger.getLogger(SyncPmopJob.class);
    private Service service;
    private Analysis analysis = new Analysis();
    private SiteAnalysis siteAnalysis = new SiteAnalysis();
    private AnalysisService analysisService;
    private SiteAnalysisService siteAnalysisService;
    private String syncUrl;
    private String syncUrl2;

    public void execute() throws Exception {
        String[] serviceNames = new String[]{"youKuServiceImpl", "jinRiTouTiaoServiceImpl",
                "miaoPaiServiceImpl", "qqServiceImpl",
                "sohuServiceImpl", "_56serviceImpl", "iQiyiServiceImpl", "LetvServiceImpl"};
        String[] sources = new String[]{"优酷", "今日头条", "秒拍", "腾讯视频", "搜狐", "56视频", "爱奇艺", "乐视"};
        analysisService = BeanLoader.getBean("analysisService");
        siteAnalysisService = BeanLoader.getBean("siteAnalysisService");
        for (int i = 0; i < serviceNames.length; i++) {
            doExecute(serviceNames[i], sources[i]);
        }
    }

    private void doExecute(String serviceName, String source) throws Exception {
        service = BeanLoader.getBean(serviceName);
        List<VideoEntityInfo> list = service.selectUnSyncList();

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    analysis.setPgcId(list.get(i).getPgcId());
                    analysis.setPgcName(list.get(i).getPgcName());
                    analysis.setSite(source);
                    analysis.setDateTime(DateUtil.formatDateHour(new Date()));
                    siteAnalysis.setSite(source);
                    siteAnalysis.setLastSynDate(DateUtil.formatDateTime(new Date()));
                    siteAnalysis.setDateTime(DateUtil.formatDateHour(new Date()));
                    String xml = buidXml(list.get(i), source);
                    HttpResult res = HttpUtils.httpPost(syncUrl, "xmlData=" + xml);
                    HttpResult res2 = HttpUtils.httpPost(syncUrl2, "xmlData=" + URLEncoder.encode(xml.replace("\n", "").replace("\r", ""), "UTF-8"));
                    log.info("同步xml数据：" + xml + "\r\n 返回值1：" + res.getBody() + "\r\n 返回值2：" + res2.getBody());
                    if (res.getBody().equals("0")) {
                        analysisService.AddFailSynCount(analysis);
                        siteAnalysisService.AddFailSynCount(siteAnalysis);
                        list.get(i).setStatus(4);
                    } else {
                        analysisService.AddSucSynCount(analysis);
                        siteAnalysisService.AddSucSynCount(siteAnalysis);
                        list.get(i).setStatus(3);
                    }
                } catch (Exception e) {
                    list.get(i).setStatus(4);
                }
                service.updateOneById(list.get(i));
            }
        }
    }

    private String buidXml(VideoEntityInfo en, String source) {
        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement("Item");

        rootElement.addElement("Id").setText("");
        rootElement.addElement("pgcId").setText(en.getPgcId());
        rootElement.addElement("title").addCDATA(en.getTitle().replace("&", "＆"));
        rootElement.addElement("createTime").setText(en.getCreateTime());
        rootElement.addElement("poster").setText(en.getImgLocation());
        rootElement.addElement("abstract").setText("");
        rootElement.addElement("description").setText("");
        rootElement.addElement("keywords").setText(en.getKeywords());
        rootElement.addElement("tags").addCDATA(en.getCategory().replace("&", "＆"));
        rootElement.addElement("categoryName").setText("");
        rootElement.addElement("cpName").setText(source);
        rootElement.addElement("columnName").setText(en.getPgcName());
        rootElement.addElement("episode").setText("");
        rootElement.addElement("version").setText("");
        rootElement.addElement("area").setText("");
        rootElement.addElement("guest").setText("");
        rootElement.addElement("host").setText("");
        rootElement.addElement("actor").setText("");
        rootElement.addElement("director").setText("");
        rootElement.addElement("pointStart").setText("");
        rootElement.addElement("pointEnd").setText("");
        rootElement.addElement("favorCount").setText(en.getFavstat());
        rootElement.addElement("upCount").setText(en.getUpstat());
        rootElement.addElement("downCount").setText(en.getDownstat());
        rootElement.addElement("playCount").setText(en.getPlaystat());
        rootElement.addElement("comments").setText(en.getComments());
        if (en.getFileLocation() != null && !en.getFileLocation().equals("")) {
            String[] files = en.getFileLocation().split("\\|");
            for (int i = 0; i < files.length; i++) {
                rootElement.addElement("url").addAttribute("part", String.valueOf(i)).setText(files[i]);
            }
        }
        rootElement.addElement("creator").setText(en.getPgcName());
        rootElement.addElement("source").setText("抓站");
        rootElement.addElement("duration").setText(String.valueOf(en.getDuration()));
        rootElement.addElement("mediaName").setText(source);
        rootElement.addElement("priority").setText(String.valueOf(en.getPriority()));
        return document.asXML();
    }

    public void setSyncUrl2(String syncUrl2) {
        this.syncUrl2 = syncUrl2;
    }

    public void setSyncUrl(String syncUrl) {
        this.syncUrl = syncUrl;
    }
}
