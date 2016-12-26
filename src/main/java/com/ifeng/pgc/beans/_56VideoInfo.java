package com.ifeng.pgc.beans;

import com.ifeng.pgc.annotations.JsonProperties;
import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

/**
 * Created by zhanglr on 2016/6/17.
 */
//@TargetUrl(value = "http://my.tv.sohu.com/user/wm/56ta/v.do\\?uid=209472497&sortType=1&pg=\\d+&size=40")
//@ExtractBy(value = "//div[@class='v-link']",multi = true)
@JsonProperties(Name = "data.list")
public class _56VideoInfo extends EntryCodec {
    private String category;
    @JsonProperties(Name = "title")
    private String title;
    //@JsonProperties(Name = "vid56Encode")
    private String url;
    //@JsonProperties(Name = "vid56")
    private int vid56;
    private int listPageId;

    public int getListPageId() {
        return listPageId;
    }

    public void setListPageId(int listPageId) {
        this.listPageId = listPageId;
    }

    public int getVid56() {
        return vid56;
    }

    public void setVid56(int vid56) {
        this.vid56 = vid56;
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
        this.vid56 = loader.getInt("vid56");
        this.url = "http://www.56.com/u" + (this.vid56  % 88 + 11) + "/v_" + loader.getString("vid56Encode") + ".html";
    }
}

