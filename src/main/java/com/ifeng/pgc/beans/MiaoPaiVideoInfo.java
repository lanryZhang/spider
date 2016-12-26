package com.ifeng.pgc.beans;

import com.ifeng.pgc.annotations.JsonProperties;

/**
 * Created by zhanglr on 2016/6/14.
 */

public class MiaoPaiVideoInfo {
    @JsonProperties(Name = "msg")
    private String msg;
    private String category;
    private String url;
    private String title;
    private int listPageId;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
