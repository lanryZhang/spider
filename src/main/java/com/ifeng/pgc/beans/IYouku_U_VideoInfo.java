package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhanglr on 2016/6/16.
 */
@TargetUrl(value = "http://i.youku.com/u/*/videos/fun_ajaxload/\\?__rt=1&__ro=&v_page=1&page_num=\\d+&page_order=1&q=")
@ExtractBy(value = "//div[@class='v-link']",multi = true)
public class IYouku_U_VideoInfo {
    @ExtractBy(value = "//a/@title")
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
        Pattern pattern = Pattern.compile( "^(<|《).*(>|》)");
        Matcher m = pattern.matcher(this.category);
        if(m.find()) {
            category = m.group(0);
        }
        return  category;
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
