package com.ifeng.pgc.core.monitor.spider;

import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhanglr on 2016/9/7.
 */
public class SpiderJobDescriptor extends EntryCodec implements Serializable {
    private String jobName;
    private String startTime;
    private String currentStatus;
    private long totalSpiderUrlCount;
    private long remainUrlCount;

    private int forbiddenErrorCount;
    private String lastCollectMonitorInfoTime;
    private String siteName;

    protected String hostname;

    protected String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getForbiddenErrorCount() {
        return forbiddenErrorCount;
    }

    public void setForbiddenErrorCount(int forbiddenErrorCount) {
        this.forbiddenErrorCount = forbiddenErrorCount;
    }

    public String getLastCollectMonitorInfoTime() {
        return lastCollectMonitorInfoTime;
    }

    public void setLastCollectMonitorInfoTime(String lastCollectMonitorInfoTime) {
        this.lastCollectMonitorInfoTime = lastCollectMonitorInfoTime;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public long getTotalSpiderUrlCount() {
        return totalSpiderUrlCount;
    }

    public void setTotalSpiderUrlCount(long totalSpideUrlCount) {
        this.totalSpiderUrlCount = totalSpideUrlCount;
    }

    public long getRemainUrlCount() {
        return remainUrlCount;
    }

    public void setRemainUrlCount(long remainUrlCount) {
        this.remainUrlCount = remainUrlCount;
    }

    @Override
    public void decode(DataLoader loader) {
        this.currentStatus = loader.getString("");
        this.forbiddenErrorCount = loader.getInt("");
        this.hostname = loader.getString("");
        this.jobName = loader.getString("");
        this.remainUrlCount = loader.getInt("");
        this.siteName = loader.getString("");
        this.startTime = loader.getString("");
        this.totalSpiderUrlCount = loader.getInt("");
        this.lastCollectMonitorInfoTime = loader.getString("");
        this.createTime = loader.getString("createTime");
    }
}
