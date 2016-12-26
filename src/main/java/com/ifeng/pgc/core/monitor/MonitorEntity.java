package com.ifeng.pgc.core.monitor;

import com.ifeng.pgc.core.monitor.spider.SpiderJobDescriptor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanglr on 2016/9/6.
 */
public class MonitorEntity implements Serializable {
    private String entryName;
    private String hostIp;
    private String port;

    private List<SpiderJobDescriptor> spideJobDescriptors;

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<SpiderJobDescriptor> getSpideJobDescriptors() {
        return spideJobDescriptors;
    }

    public void setSpideJobDescriptors(List<SpiderJobDescriptor> spideJobDescriptors) {
        this.spideJobDescriptors = spideJobDescriptors;
    }
}
