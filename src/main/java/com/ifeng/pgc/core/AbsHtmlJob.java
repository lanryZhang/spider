package com.ifeng.pgc.core;

import com.ifeng.pgc.core.monitor.IMonitor;
import com.ifeng.pgc.core.monitor.spider.SpiderMonitor;
import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglr on 2016/6/14.
 */
public abstract class AbsHtmlJob extends AbsJobBase implements IMonitor<AbsHtmlJob> {
    public AbsHtmlJob(Class<?> clazz) {
        this.clazz = clazz;
    }
    private Logger log = Logger.getLogger(AbsHtmlJob.class);
    @Override
    public int initPages(Context context) {
        return 0;
    }


    private Spider createSpide() throws Exception{
        if (clazz == null && className != null) {
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                //log.error(e);
            }
        }
        Site site = Site.me();
        OOSpider<Object> spider = OOSpider.create(site,
                clazz);
        spider.getSite().setUserAgent(getUserAgent());
        SpiderListener listener;
        listener = new SpiderListener() {
            @Override
            public void onSuccess(Request request) {
                try {
                    String ua = getUserAgent();
                    spider.getSite().setUserAgent(ua);
                    spider.getSite().addHeader("User-Agent",ua);
                }catch (Exception e) {}
            }
            @Override
            public void onError(Request request) {
                try{
                    Context context = new Context();
                    context.put("request", request);
                    doError(context);
                    Object statusCode = request.getExtra(Request.STATUS_CODE);
                    if (statusCode != null && "403".equals(statusCode.toString())){
                        forbiddenErrorCount.incrementAndGet();
                    }
                        String ua = getUserAgent();
                        spider.getSite().setUserAgent(ua);
                        spider.getSite().addHeader("User-Agent", ua);
                }catch (Exception e){
                    log.error(e);
                }

            }
        };
        List<SpiderListener> listeners = new ArrayList<>();
        listeners.add(listener);
        spider.setSpiderListeners(listeners);
        spider.setUUID(spiderName);
        spider.setExitWhenComplete(false);
        if (port != 0 && hostname != null && !hostname.equals("")) {
            spider.getSite().setHttpProxy(new HttpHost(hostname, port));
        }
        if (sleepTime > 0) {
            spider.getSite().setSleepTime(sleepTime);
        }
        if (retrySleepTime > 0) {
            spider.setEmptySleepTime(retrySleepTime);
        }
        if (timeout > 0) {
            spider.getSite().setTimeOut(timeout);
        }
        spider.thread(3);
        spider.startRequest(buildRequest())
//        spider.addUrl("http://www.toutiao.com/m5859394671/?page_type=0")
                .addPipeline((resultItems, task) -> {
                    try {
                        Context context = new Context();
                        context.put("resultItems", resultItems);
                        log.info(resultItems);
                        context.put("task", task);
                        doExecute(context);
                        remainUrlCount = ((QueueScheduler) spider.getScheduler()).getLeftRequestsCount(task);
                    } catch (Exception e) {
                        log.error(e);
                    }
                });
        if (downloader != null) {
            spider.setDownloader(downloader);
        }
//        if (downloader == null) {
//            spider.setDownloader(new PhantomJsDownloader());
//        }
        return spider;
    }
    public void execute() throws Exception {
        Spider spider;

        if (SpideFactory.contains(spiderName)){
            spider = SpideFactory.get(spiderName);
            List<Request> requests = buildRequest();
            if (requests.size() > 0) {
                Request[] temp = requests.toArray(new Request[requests.size()]);
                spider.addRequest(temp);

                log.info("新增抓取URL：" + temp.length);
            }
        }else{
            spider = createSpide();
            if (spider != null)
                SpideFactory.put(spiderName, spider);
        }
        if (spider.getStatus() == Spider.Status.Stopped || spider.getStatus() == Spider.Status.Init) {
            spider.start();
            spiderStartTime = System.currentTimeMillis();
            SpiderMonitor.regist(this);
        }
    }

    @Override
    public AbsHtmlJob report() {
        lastCollectMonitorInfoTime = System.currentTimeMillis();
        return this;
    }
    public abstract void doExecute(Context context) throws Exception;

    public  void doError(Context context) throws Exception{}

    public Downloader getDownloader() {
        return downloader;
    }

    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }
}
