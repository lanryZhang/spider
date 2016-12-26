package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * Created by zhanglr on 2016/6/16.
 */
@TargetUrl(value = "http://i.youku.com/i/*/videos\\?order=1&page=\\d+")
@ExtractBy(value = "//div[@class='v-link']",multi = true)
public class IYouku_I_VideoInfo {
    @ExtractBy(value = "//a[@class='username']/text()",source = ExtractBy.Source.RawHtml)
    private String category;
    @ExtractBy(value = "//a/@title")
    private String title;
    @ExtractBy(value = "//a/@href", notNull=true)
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
