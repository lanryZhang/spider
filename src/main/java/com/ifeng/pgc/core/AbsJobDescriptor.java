package com.ifeng.pgc.core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhanglr on 2016/9/19.
 */
public abstract class AbsJobDescriptor {
    public String hostname;
    public int port;
    public long spiderStartTime;
    public AtomicInteger forbiddenErrorCount = new AtomicInteger(0);
    public long lastCollectMonitorInfoTime;
    public int totalSpiderUrlCount;
    public int remainUrlCount;
    public String spiderName;
    public String siteName;



    public void setSpiderName(String spiderName) {
        this.spiderName = spiderName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
