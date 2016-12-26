package com.ifeng.pgc.beans;

import com.ifeng.pgc.annotations.JsonProperties;
import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

/**
 * Created by zhusy on 2016/8/31.
 */

@JsonProperties(Name = "list")
public class IQiyiVideoInfo extends EntryCodec {
    @JsonProperties(Name = "title")
    private String title;
    @JsonProperties(Name = "url")
    private String url;
    private String createTime;
    private String statCount;
    @JsonProperties(Name = "category")
    private String category;
    private int listPageId;
//    @ExtractBy("//div[@class='site-piclist_pic']/a/img/@src")
    private String imgPath ;

    public String getImgPath() {
        return imgPath == null?null:imgPath;
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
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("title = \""+getTitle());
        sb.append("\"url = \""+getUrl());
        sb.append("\"category = \""+getCategory());
        sb.append("\"imgPath = \""+getImgPath());
        sb.append("\"}");
        return sb.toString();
    }
    @Override
    public void decode(DataLoader loader) {
        this.title = loader.getString("title");
        this.url = loader.getString("url");
        this.category = loader.getString("category");
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatCount() {
        return statCount;
    }

    public void setStatCount(String statCount) {
        this.statCount = statCount;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
