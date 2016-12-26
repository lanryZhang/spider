package com.ifeng.pgc.beans;

import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

/**
 * Created by zhanglr on 2016/7/12.
 */
public class VideoEntityInfo extends EntryCodec {
    private String id;
    private String category = "";
    private String title = "";
    private String url = "";
    private String createTime = "";
    private String keywords = "";
    private String author = "";
    private String comments = "";
    private String playstat = "";
    private String favstat = "";
    private String upstat = "";
    private String downstat = "";
    /**
     * 0 未下载，
     * 1下载成功
     * 2下载失败,
     * 3 同步完成,
     * 4同步失败
     * 5上传成功
     * 6上传失败
     * 7 总时长超过10分钟，不下载
     */
    private int status = 0;
    private String fileLocation = "";
    private String imgLocation = "";
    private int updateStatus = 0;
    private String pgcName = "";
    private String pgcId = "";
    private int priority = 0 ;
    private String duration = "";

    public String getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = String.valueOf(duration);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPgcName() {
        return pgcName;
    }

    public void setPgcName(String pgcName) {
        this.pgcName = pgcName;
    }

    public String getImgLocation() {
        return imgLocation;
    }

    public void setImgLocation(String imgLocation) {
        this.imgLocation = imgLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category == null?"":category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getKeywords() {
        return keywords == null ? "" : keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return author == null ? "" : author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComments() {
        return comments == null ? "0" : comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPlaystat() {
        return playstat == null ? "0" : playstat;
    }

    public void setPlaystat(String playstat) {
        this.playstat = playstat;
    }

    public String getFavstat() {
        return favstat == null ? "0" : favstat;
    }

    public void setFavstat(String favstat) {
        this.favstat = favstat;
    }

    public String getUpstat() {
        return upstat == null ? "0" : upstat;
    }

    public void setUpstat(String upstat) {
        this.upstat = upstat;
    }

    public String getDownstat() {
        return downstat == null ? "0" : downstat;
    }

    public void setDownstat(String downstat) {
        this.downstat = downstat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFileLocation() {
        return fileLocation == null ? "" : fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getPgcId() {
        return pgcId;
    }

    public void setPgcId(String pgcId) {
        this.pgcId = pgcId;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString(){
        return this.title + "$"+this.url;
    }

    @Override
    public void decode(DataLoader loader) {
        this.id = loader.getString("id");
        this.category = loader.getString("category");
        this.title = loader.getString("title");
        this.url = loader.getString("url");
        this.createTime = loader.getString("createTime");
        this.keywords = loader.getString("keywords");
        this.author = loader.getString("author");
        this.comments = loader.getString("comments");
        this.playstat = loader.getString("playstat");
        this.favstat = loader.getString("favstat");
        this.upstat = loader.getString("upstat");
        this.downstat = loader.getString("downstat");
        this.status = loader.getInt("status");
        this.fileLocation = loader.getString("fileLocation");
        this.imgLocation = loader.getString("imgLocation");
        this.updateStatus = loader.getInt("updateStatus");
        this.pgcName = loader.getString("pgcName");
        this.pgcId = loader.getString("pgcId");
        this.priority = loader.getInt("priority");
        this.duration = loader.getString("duration");
    }
}
