package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * Created by zhanglr on 2016/6/16.
 */

/**
 * 女神有药
 */
@TargetUrl(value = "http://www.youku.com/show_episode/id_\\w+.html\\?dt=json&divid=reload_\\d+")
@ExtractBy(value = "//ul[@class='item']",multi = true)
public class IYouku_Other_VideoInfo {
    @ExtractBy(value = "//span[@class='name']/text()",source = ExtractBy.Source.RawHtml)
    private String category;
    @ExtractBy(value = "//li[@class='ititle_w' or @class='ititle']/a/text()")
    private String title;
    @ExtractBy(value = "//li[@class='ititle_w' or @class='ititle']/a/@href", notNull=true)
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
