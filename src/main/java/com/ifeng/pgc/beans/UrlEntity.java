package com.ifeng.pgc.beans;

import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

/**
 * Created by zhanglr on 2016/7/31.
 */
public class UrlEntity extends EntryCodec {
    private String id;
    private String url;
    private String spideDate;
    private String site;
    private String lastSpideStatus;
    private String creater;
    private String pgcName;
    private String pgcId;
    private String createTime;
    /**
     * 0增量抓取 1全量抓取
     */
    private int spideType;
    private int priority;


    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getPgcName() {
        return pgcName;
    }

    public void setPgcName(String pgcName) {
        this.pgcName = pgcName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSpideType() {
        return spideType;
    }

    public void setSpideType(int spideType) {
        this.spideType = spideType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSpideDate() {
        return spideDate;
    }

    public void setSpideDate(String spideDate) {
        this.spideDate = spideDate;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLastSpideStatus() {
        return lastSpideStatus;
    }

    public void setLastSpideStatus(String lastSpideStatus) {
        this.lastSpideStatus = lastSpideStatus;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPgcId() {
        return pgcId;
    }

    public void setPgcId(String pgcId) {
        this.pgcId = pgcId;
    }

    @Override
    public void decode(DataLoader loader) {
        this.id = loader.getString("id");
        this.url = loader.getString("url");
        this.spideDate = loader.getString("spideDate");
        this.site = loader.getString("site");
        this.lastSpideStatus = loader.getString("lastSpideStatus");
        this.spideType = loader.getInt("spideType");
        this.priority  = loader.getInt("priority");
        this.creater = loader.getString("creater");
        this.pgcName = loader.getString("pgcName");
        this.pgcId = loader.getString("pgcId");
        this.createTime = loader.getString("createTime");
    }
}
