package com.ifeng.pgc.core;

import com.ifeng.pgc.annotations.JsonProperties;
import com.ifeng.pgc.core.mongo.EntryCodec;
import com.ifeng.pgc.core.monitor.IMonitor;
import com.ifeng.pgc.core.monitor.spider.SpiderMonitor;
import com.jayway.jsonpath.JsonPath;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanglr on 2016/6/14.
 */
public abstract class AbsJsonJob extends AbsJobBase implements PageProcessor, IMonitor<AbsJsonJob> {
    private Map<String, String> fieldsMap = new HashMap<>();
    protected JsonProperties jsonProperties;
    protected Class<? extends EntryCodec> clazz;
    private Logger log = Logger.getLogger(AbsHtmlJob.class);

    @Override
    public int initPages(Context context) {
        return 0;
    }

    @Override
    public void process(Page page) {
        if (clazz == null && className != null) {
            try {
                clazz = (Class<? extends EntryCodec>) Class.forName(className);
            } catch (ClassNotFoundException e) {
                //log.error(e);
            }
        }

        jsonProperties = clazz.getAnnotation(JsonProperties.class);
        if (iPageProcessor != null) {
            page.setRawText(iPageProcessor.process(page.getRawText()));
        }
        if(iPageDealer != null) {
            iPageDealer.deal(page,SpideFactory.get(spiderName));
        }

        //获取单个实例
        if (jsonProperties == null) {

            for (Map.Entry<String, String> en : fieldsMap.entrySet()) {
                page.putField(en.getKey(), page.getJson().jsonPath("$." + en.getValue()));
            }

        } else {//获取Json数组
            List list = JsonPath.read(page.getJson().toString(), "$." + jsonProperties.Name());
            //if(list == null || list.size() == 0) page.setSkip(true);
            page.getResultItems().put(jsonProperties.Name(), list);
        }
    }

    private void initJsonFields() {
        if (clazz != null) {
            Field[] fs = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fs, true);

            for (Field f : fs) {
                JsonProperties jp = f.getDeclaredAnnotation(JsonProperties.class);
                if (jp != null) {
                    fieldsMap.put(f.getName(), jp.Name());
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return Site.me();
    }

    public AbsJsonJob(Class clazz) {
        this.clazz = clazz;
        initJsonFields();
    }

    private Spider createSpide() throws Exception {
        Spider spider = Spider.create(this).startRequest(buildRequest());
        spider.getSite().setUserAgent(getUserAgent());
        if (sleepTime > 0) {
            spider.getSite().setSleepTime(sleepTime);
        }
        if (retrySleepTime > 0) {
            spider.setEmptySleepTime(retrySleepTime);
        }
        if (timeout > 0) {
            spider.getSite().setTimeOut(timeout);
        }

        SpiderListener listener = new SpiderListener() {
            @Override
            public void onSuccess(Request request) {
                try {
                    String ua = getUserAgent();
                    spider.getSite().setUserAgent(ua);
                    spider.getSite().addHeader("User-Agent", ua);
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Request request) {
                Context context = new Context();
                context.put("request", request);
                try {
                    doError(context);
                    String ua = getUserAgent();
                    spider.getSite().setUserAgent(ua);
                    spider.getSite().addHeader("User-Agent", ua);
                } catch (Exception e) {
                }
            }
        };
        List<SpiderListener> listeners = new ArrayList<>();
        listeners.add(listener);
        spider.setSpiderListeners(listeners);
        spider.setUUID(spiderName);
        spider.setExitWhenComplete(false);
        spider.thread(1);
        spider.addPipeline((ResultItems resultItems, Task task) -> {
            try {
                Context context = new Context();
                context.put("resultItems", resultItems);
                context.put("task", task);
                doExecute(context);
                remainUrlCount = ((QueueScheduler) spider.getScheduler()).getLeftRequestsCount(task);
            } catch (Exception e) {
                log.equals(e);
            }
        });
        return spider;
    }

    public void execute() {
        try {
            Spider spider;
            if (SpideFactory.contains(spiderName)) {

                spider = SpideFactory.get(spiderName);
                List<Request> requests = buildRequest();
                if (requests.size() > 0) {
                    Request[] temp = requests.toArray(new Request[requests.size()]);
                    spider.addRequest(temp);
                    log.info("新增抓取URL：" + temp.length);
                }
            } else {
                spider = createSpide();
                if (spider != null)
                    SpideFactory.put(spiderName, spider);
            }
            if (spider.getStatus() == Spider.Status.Stopped || spider.getStatus() == Spider.Status.Init) {
                spider.start();
                spiderStartTime = System.currentTimeMillis();
                SpiderMonitor.regist(this);
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public AbsJsonJob report() {
        lastCollectMonitorInfoTime = System.currentTimeMillis();
        return this;
    }

    public abstract void doExecute(Context context) throws Exception;

    public void doError(Context context) throws Exception {
    }
}
