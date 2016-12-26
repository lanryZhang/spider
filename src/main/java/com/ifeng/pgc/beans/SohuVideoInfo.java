package com.ifeng.pgc.beans;

import com.ifeng.pgc.annotations.JsonProperties;
import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

/**
 * Created by zhanglr on 2016/6/30.
 */
@JsonProperties(Name = "data.list")
public class SohuVideoInfo extends EntryCodec {
    @JsonProperties(Name = "title")
    private String title;
    @JsonProperties(Name = "url")
    private String url;

    private String category;

    private int listPageId;

    public int getListPageId() {
        return listPageId;
    }

    public void setListPageId(int listPageId) {
        this.listPageId = listPageId;
    }

    @Override
    public String toString(){
        return this.title;
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
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void decode(DataLoader loader) {
        this.title = loader.getString("title");
        this.url = loader.getString("url");
    }
}