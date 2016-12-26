package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

/**
 * Created by zhanglr on 2016/6/14.
 */

@TargetUrl(value = "http://v.qq.com/page/*/*/*/*.html", sourceRegion = "//div[@class='mod_player']")
//@ExtractBy(value = "//div[@class='D_video']")
public class QQVideoPageInfo_ {


    @ExtractBy(value = "//div[@class='intro_inner']/div[@class='intro_info']/ul[@class='info_list']/li/div[@class='list_cont']/div[@class='mod_tag']/ul/li/a/span/text()", multi = true)
    private List<String> keywords;
    @ExtractBy(value = "//div[@class='album_details cf']/div[@class='mod_video_user']/div[@class='video_user_detail']/div[@class='detail_hd']/a[1]/text()")
    private String author;
    @ExtractBy(value = "//div[@class='mod_action cf']/div[@class='action_wrap cf']/div[@class='action_count']/a/em/text()")
    private String comments;

    @ExtractBy(value = "//div[@class='mod_action cf']/div[@class='action_wrap cf']/div[@class='action_count']/div[@class='count_item count_played']/div[@class='count_title']/em/text()")
    private String playstat;

    @ExtractBy(value = "//div[@class='detail_bd']/ul/li[@class='item item_focus']/span/text()")
    private String favstat;
    private String upstat;
    private String downstat;
    private String title;
    @ExtractBy(value = "//div[@class='site_container']/div[@class='container_inner']/div[@class='breadcrumb']/a[1]/text()")
    private String category;


    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPlaystat() {
        return playstat;
    }

    public void setPlaystat(String playstat) {
        this.playstat = playstat;
    }

    public String getFavstat() {
        return favstat;
    }

    public void setFavstat(String favstat) {
        this.favstat = favstat;
    }

    public String getUpstat() {
        return upstat;
    }

    public void setUpstat(String upstat) {
        this.upstat = upstat;
    }

    public String getDownstat() {
        return downstat;
    }

    public void setDownstat(String downstat) {
        this.downstat = downstat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
