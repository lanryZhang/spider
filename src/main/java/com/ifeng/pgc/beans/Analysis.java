/**
 * Analysis.java
 * Created by zhusy on 2016/12/6 0006 14:09
 * Copyright © 2012 Phoenix New Media Limited All Rights Reserved
 */
package com.ifeng.pgc.beans;

import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

public class Analysis extends EntryCodec {
    private String pgcId;
    private String site;
    private String pgcName;
    private String lastSpiderDate;
    private String lastDownloadDate;
    private String dateTime;
    private int spiderCount;
    private int spiderVideoCount;
    private int sucSpiderCount;
    private int failSpiderCount;
    private int downloadCount;
    private int sucDownloadCount;
    private int failDownloadCount;
    private int sucSynCount;
    private int failSynCount;

    public String getPgcId() {
        return pgcId;
    }

    public void setPgcId(String pgcId) {
        this.pgcId = pgcId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPgcName() {
        return pgcName;
    }

    public void setPgcName(String pgcName) {
        this.pgcName = pgcName;
    }

    public String getLastSpiderDate() {
        return lastSpiderDate;
    }

    public void setLastSpiderDate(String lastSpiderDate) {
        this.lastSpiderDate = lastSpiderDate;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getSpiderCount() {
        return spiderCount;
    }

    public void setSpiderCount(int spiderCount) {
        this.spiderCount = spiderCount;
    }

    public int getSucSpiderCount() {
        return sucSpiderCount;
    }

    public void setSucSpiderCount(int sucSpiderCount) {
        this.sucSpiderCount = sucSpiderCount;
    }

    public int getFailSpiderCount() {
        return failSpiderCount;
    }

    public void setFailSpiderCount(int failSpiderCount) {
        this.failSpiderCount = failSpiderCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public int getSucDownloadCount() {
        return sucDownloadCount;
    }

    public void setSucDownloadCount(int sucDownloadCount) {
        this.sucDownloadCount = sucDownloadCount;
    }

    public int getFailDownloadCount() {
        return failDownloadCount;
    }

    public void setFailDownloadCount(int failDownloadCount) {
        this.failDownloadCount = failDownloadCount;
    }

    public int getSucSynCount() {
        return sucSynCount;
    }

    public void setSucSynCount(int sucSynCount) {
        this.sucSynCount = sucSynCount;
    }

    public int getFailSynCount() {
        return failSynCount;
    }

    public void setFailSynCount(int failSynCount) {
        this.failSynCount = failSynCount;
    }

    public int getSpiderVideoCount() {
        return spiderVideoCount;
    }

    public void setSpiderVideoCount(int spiderVideoCount) {
        this.spiderVideoCount = spiderVideoCount;
    }

    public String getLastDownloadDate() {
        return lastDownloadDate;
    }

    public void setLastDownloadDate(String lastDownloadDate) {
        this.lastDownloadDate = lastDownloadDate;
    }

    @Override
    public void decode(DataLoader loader) {
        this.pgcId = loader.getString("pgcId");
        this.site = loader.getString("site");
        this.pgcName = loader.getString("pgcName");
        this.lastSpiderDate = loader.getString("lastSpiderDate");
        this.lastDownloadDate = loader.getString("lastDownloadDate");
        this.dateTime = loader.getString("dateTime");
        this.spiderCount = loader.getInt("spiderCount");
        this.spiderVideoCount = loader.getInt("spiderVideoCount");
        this.sucSpiderCount = loader.getInt("sucSpiderCount");
        this.failSpiderCount = loader.getInt("failSpiderCount");
        this.downloadCount = loader.getInt("downloadCount");
        this.sucDownloadCount = loader.getInt("sucDownloadCount");
        this.failDownloadCount = loader.getInt("failDownloadCount");
        this.sucSynCount = loader.getInt("sucSynCount");
        this.failSynCount = loader.getInt("failSynCount");
    }
}
