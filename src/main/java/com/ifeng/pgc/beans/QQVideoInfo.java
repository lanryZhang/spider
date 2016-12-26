package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ExtractBy;

/**
 * Created by zhanglr on 2016/7/5.
 */
@ExtractBy(value = "//root/videolst",multi = true)
public class QQVideoInfo {
    private String category;
    @ExtractBy(value = "//title/text()", notNull=true)
    private String title;
    @ExtractBy(value = "//url/text()", notNull=true)
    private String url;

    private int listPageId;

    public int getListPageId() {
        return listPageId;
    }

    public void setListPageId(int listPageId) {
        this.listPageId = listPageId;
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
}
