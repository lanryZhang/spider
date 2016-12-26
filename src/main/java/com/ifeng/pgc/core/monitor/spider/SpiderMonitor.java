package com.ifeng.pgc.core.monitor.spider;

import com.google.common.collect.Sets;
import com.ifeng.pgc.core.AbsJobDescriptor;
import com.ifeng.pgc.core.mongo.MongoCli;
import com.ifeng.pgc.core.mongo.MongoFactory;
import com.ifeng.pgc.core.monitor.IMonitor;
import com.ifeng.pgc.utils.DateUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhanglr on 2016/7/30.
 */
public class SpiderMonitor {

    private static Logger log = Logger.getLogger(SpiderMonitor.class);
    private static Set<IMonitor> instances = Sets.newSetFromMap(new ConcurrentHashMap<IMonitor,Boolean>());
    private static MongoCli cli = MongoFactory.newInstance("spider");

    public static void regist(IMonitor en){
        instances.add(en);
    }

    public static List<SpiderJobDescriptor> analysis(){
        List<SpiderJobDescriptor> res = new ArrayList<>();
        Map<String, Object> fieldMap = new HashMap<>();
        cli.getCollection("MONITOR");
        try {
            instances.forEach(r -> {
                AbsJobDescriptor descriptor = (AbsJobDescriptor) r.report();
                SpiderJobDescriptor spiderJobDescriptor = new SpiderJobDescriptor();

                Field[] fields = descriptor.getClass().getFields();
                for (Field field : fields) {
                    try {
                        fieldMap.put(field.getName(), field.get(descriptor));
                    } catch (IllegalAccessException e) {
                        log.error(e);
                    }
                }

                spiderJobDescriptor.setCurrentStatus("ok");
                int forbiddenErrorCount = ((AtomicInteger) fieldMap.get("forbiddenErrorCount")).get();
                ((AtomicInteger) fieldMap.get("forbiddenErrorCount")).set(0);
                spiderJobDescriptor.setForbiddenErrorCount(forbiddenErrorCount);
                String hostName = fieldMap.get("hostName") == null ? "":fieldMap.get("hostName").toString();
                spiderJobDescriptor.setHostname(hostName);
                String spiderName = fieldMap.get("spiderName") == null ? "":fieldMap.get("spiderName").toString();
                spiderJobDescriptor.setJobName(spiderName);
                long lastCollectMonitorInfoTime = fieldMap.get("lastCollectMonitorInfoTime") == null ? 0 : (Long) fieldMap.get("lastCollectMonitorInfoTime");
                spiderJobDescriptor.setLastCollectMonitorInfoTime(DateUtil.formatDateTime(new Date(lastCollectMonitorInfoTime)));
                int remainUrlCount = fieldMap.get("remainUrlCount") == null ? 0 : (int)fieldMap.get("remainUrlCount");
                spiderJobDescriptor.setRemainUrlCount(remainUrlCount);
                int totalSpiderUrlCount = fieldMap.get("totalSpiderUrlCount") == null ? 0 : (int)fieldMap.get("totalSpiderUrlCount");
                spiderJobDescriptor.setTotalSpiderUrlCount(totalSpiderUrlCount);
                String siteName = fieldMap.get("siteName") == null ? "":fieldMap.get("siteName").toString();
                spiderJobDescriptor.setSiteName(siteName);
                long spiderStartTime = fieldMap.get("spiderStartTime") == null ? 0 : (Long) fieldMap.get("spiderStartTime");
                spiderJobDescriptor.setStartTime(DateUtil.formatDateTime(new Date(spiderStartTime)));
                spiderJobDescriptor.setCreateTime(DateUtil.now());
                res.add(spiderJobDescriptor);
            });
            if (res.size() >0)
                cli.insert(res);
        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }
}
