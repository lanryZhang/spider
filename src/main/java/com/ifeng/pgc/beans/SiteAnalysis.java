/**
 * SiteAnalysis.java
 * Created by zhusy on 2016/12/8 0008 9:32
 * Copyright © 2012 Phoenix New Media Limited All Rights Reserved
 */
package com.ifeng.pgc.beans;

import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

public class SiteAnalysis extends EntryCodec{
    private String site;
    private String lastSpiderDate;
    private String lastDownloadDate;
    private String lastSynDate;
    private String dateTime;
    private int spiderVideoCount;
    private int sucSpiderCount;
    private int failSpiderCount;
    private int sucDownloadCount;
    private int failDownloadCount;
    private int sucSynCount;
    private int failSynCount;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLastSpiderDate() {
        return lastSpiderDate;
    }

    public void setLastSpiderDate(String lastSpiderDate) {
        this.lastSpiderDate = lastSpiderDate;
    }

    public String getLastDownloadDate() {
        return lastDownloadDate;
    }

    public void setLastDownloadDate(String lastDownloadDate) {
        this.lastDownloadDate = lastDownloadDate;
    }

    public String getLastSynDate() {
        return lastSynDate;
    }

    public void setLastSynDate(String lastSynDate) {
        this.lastSynDate = lastSynDate;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getSpiderVideoCount() {
        return spiderVideoCount;
    }

    public void setSpiderVideoCount(int spiderVideoCount) {
        this.spiderVideoCount = spiderVideoCount;
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

    @Override
    public void decode(DataLoader loader) {
        this.site = loader.getString("site");
        this.lastSpiderDate = loader.getString("lastSpiderDate");
        this.lastDownloadDate = loader.getString("lastDownloadDate");
        this.lastSynDate = loader.getString("lastSynDate");
        this.dateTime = loader.getString("dateTime");
        this.spiderVideoCount = loader.getInt("spiderVideoCount");
        this.sucSpiderCount = loader.getInt("sucSpiderCount");
        this.failSpiderCount = loader.getInt("failSpiderCount");
        this.sucDownloadCount = loader.getInt("sucDownloadCount");
        this.failDownloadCount = loader.getInt("failDownloadCount");
        this.sucSynCount = loader.getInt("sucSynCount");
        this.failSynCount = loader.getInt("failSynCount");
    }
}
