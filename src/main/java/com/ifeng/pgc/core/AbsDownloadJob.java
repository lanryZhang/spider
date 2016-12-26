package com.ifeng.pgc.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifeng.pgc.beans.Analysis;
import com.ifeng.pgc.beans.SiteAnalysis;
import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.core.monitor.IMonitor;
import com.ifeng.pgc.core.monitor.spider.SpiderMonitor;
import com.ifeng.pgc.services.AnalysisService;
import com.ifeng.pgc.services.SiteAnalysisService;
import com.ifeng.pgc.utils.DateUtil;
import com.ifeng.pgc.utils.HttpResult;
import com.ifeng.pgc.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.*;

/**
 * Created by zhanglr on 2016/7/12.
 */
public abstract class AbsDownloadJob extends AbsJobDescriptor implements IMonitor<AbsDownloadJob> {
    private Logger log = Logger.getLogger(AbsDownloadJob.class);
//    private String savePath = "/mnt/source2/pmop/storage_spider2";
    private String savePath = "/mnt/source2/pmop/storage_raw";
    protected String urlFormat = "http://vpfeng.5233game.com/parse_pc.php?url=%s&format=%s";
    protected AnalysisService analysisService;
    protected Analysis analysis = new Analysis();
    protected SiteAnalysisService siteAnalysisService;
    protected SiteAnalysis siteAnalysis = new SiteAnalysis();

    public void execute() {
        analysisService = BeanLoader.getBean("analysisService");
        siteAnalysisService = BeanLoader.getBean("siteAnalysisService");
        SpiderMonitor.regist(this);
        List<VideoEntityInfo> list = getVideoList();
        if (list == null){
            return;
        }
        spiderStartTime = System.currentTimeMillis();
        Date date = DateUtil.parseDate(DateUtil.now());
        String year = DateUtil.format(date, "yyyy");
        String month = DateUtil.format(date, "MM");
        String day = DateUtil.format(date, "dd");
        String path;
        String impPath ;
        StringUtils.removeEnd(savePath, "/");
        path = savePath + "/" + year + "/" + month+day + "/";
        impPath = savePath +"/" + year + "/" + month +"/"+ day + "/";
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new java.io.File(impPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        for (int i = 0; i < list.size(); i++) {
            try {
                analysis.setPgcId(list.get(i).getPgcId());
                analysis.setPgcName(list.get(i).getPgcName());
                analysis.setSite(siteName);
                analysis.setLastDownloadDate(DateUtil.formatDateTime(new Date()));
                analysis.setDateTime(DateUtil.formatDateHour(new Date()));
                siteAnalysis.setSite(siteName);
                siteAnalysis.setLastDownloadDate(DateUtil.formatDateTime(new Date()));
                siteAnalysis.setDateTime(DateUtil.formatDateHour(new Date()));
                analysisService.AddDownloadCount(analysis);
                String gid = UUID.randomUUID().toString();
                Map<String,Object> result = getRealUrl(list.get(i).getUrl().trim());

                StringBuilder sb = new StringBuilder();
                List<String> realUrls = result.get("vUrls") == null ? null : (List<String>) result.get("vUrls");
                String s = result.getOrDefault("total_duration","0") == null? "0" :result.getOrDefault("total_duration","0").toString();
                double duration = Double.valueOf(s);
                list.get(i).setDuration(duration);
                boolean flag = true;
                if (realUrls != null && (list.get(i).getPriority() == 5 || duration < 600) ) {
                    for (int j = 0; j < realUrls.size(); j++) {
                        try {
                            log.info("开始下载："+realUrls.get(j));
                            HttpResult du = HttpUtils.download(realUrls.get(j), path + gid + "_" + j);
                            if (du.getStatusCode() == 403){
                                forbiddenErrorCount.incrementAndGet();
                            }
                            log.info("文件路径:"+du.getBody().toString());
                            sb.append(du.getBody().toString()).append("|");
                        } catch (Exception e) {
                            flag = false;
                            log.error(e);
                            break;
                        }
                    }

                    String thumb = list.get(i).getImgLocation() ;
                    String t = result.get("thumb") == null?null:result.get("thumb").toString();
                    thumb = (thumb == null || "".equals(thumb))? t : thumb;

                    if (thumb != null){
                        try {
                            // String fileType = thumb.substring(thumb.lastIndexOf("."),thumb.length());
                            log.info("开始下载图片：" + thumb);
                            HttpResult res = HttpUtils.download(thumb, impPath + gid);
                            thumb = res.getBody().toString();
                            log.info("文件路径:" + thumb);
                        }catch (Exception e){
                            thumb = null;
                        }
                    }
                    //
                    if (sb.length() >= 1 && flag) {
                        sb.deleteCharAt(sb.length() - 1);
                        list.get(i).setFileLocation(sb.toString());
                        log.info(list.get(i).toString());
                        list.get(i).setStatus(1);
                        list.get(i).setImgLocation(thumb);
                        analysisService.AddSucDownloadCount(analysis);
                        siteAnalysisService.AddSucDownloadCount(siteAnalysis);
                    } else {
                        list.get(i).setStatus(2);
                        analysisService.AddFailDownloadCount(analysis);
                        siteAnalysisService.AddFailDownloadCount(siteAnalysis);
                        log.info("下载失败：" + list.get(i).toString());
                    }

                }else if (duration >= 600){
                    list.get(i).setStatus(7);
                }else {
                    analysisService.AddFailDownloadCount(analysis);
                    siteAnalysisService.AddFailDownloadCount(siteAnalysis);
                    list.get(i).setStatus(2);
                }

            } catch (Exception e) {
                try {
                    analysisService.AddFailDownloadCount(analysis);
                    siteAnalysisService.AddFailDownloadCount(siteAnalysis);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                list.get(i).setStatus(2);
                e.printStackTrace();
                //log.error(e);
            }


            try {
                update(list.get(i));
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error(e);
            }
            remainUrlCount = list.size() - i;
        }
        totalSpiderUrlCount += list.size();
    }

    @Override
    public AbsDownloadJob report() {
        lastCollectMonitorInfoTime = System.currentTimeMillis();
        return this;
    }

    public abstract List<VideoEntityInfo> getVideoList();

    public Map<String,Object> getRealUrl(String url) throws Exception{
        Map<String,Object> res = new HashMap<>();
        System.out.println(url+"实际地址:"+String.format(urlFormat, URLEncoder.encode(url, "UTF-8"), "super"));

        try {

            HttpResult result = HttpUtils.httpGet(String.format(urlFormat, URLEncoder.encode(url, "UTF-8"), "super"));
            JSONObject object = JSON.parseObject(result.getBody().toString());
            res.put("thumb",object.getString("thumb"));
            res.put("total_duration",object.getString("total_duration"));

            JSONArray inner = object.getJSONArray("V");
            List<String> list = new ArrayList<>();
            if (inner != null) {
                for (int i = 0; i < inner.size(); i++) {
                    list.add(inner.getJSONObject(i).get("U").toString());
                }
            }
            res.put("vUrls",list);
            return res;
        }catch (Exception e){
            log.error(e);
        }
        return null;
    }

    public abstract void update(VideoEntityInfo en);

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
