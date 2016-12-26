package com.ifeng.pgc.beans;

import com.ifeng.pgc.annotations.JsonProperties;
import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

/**
 * Created by zhusy on 2016/9/2.
 */
@JsonProperties(Name = "data.list")
public class LetvVideoInfo extends EntryCodec {
    private String category;
    @JsonProperties(Name = "title")
    private String title;
    private String url;
    @JsonProperties(Name = "vid")
    private int vid;
    private int listPageId;
    @JsonProperties(Name = "videoPic")
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getListPageId() {
        return listPageId;
    }

    public void setListPageId(int listPageId) {
        this.listPageId = listPageId;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid= vid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return "http://www.le.com/ptv/vplay/" + this.vid + ".html";
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("title = \""+ getTitle());
        sb.append("\"url = \""+getUrl());
        sb.append("\"vid = \""+getVid());
        sb.append("\"imgPath = \""+getImgPath());
        sb.append("\"}");
        return sb.toString();
    }

    @Override
    public void decode(DataLoader loader) {
        this.title = loader.getString("title");
        this.vid = loader.getInt("vid");
        this.imgPath = loader.getString("videoPic");
        this.url = "http://www.le.com/ptv/vplay/" + (this.vid) + ".html";
    }
}
