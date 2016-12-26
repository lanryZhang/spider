package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

/**
 * Created by zhanglr on 2016/6/14.
 */

@TargetUrl(value = "http://v.qq.com/page/*/*/*/*.html", sourceRegion = "//div[@class='mod_player']")
//@ExtractBy(value = "//div[@class='D_video']")
public class QQVideoPageInfo_3 {


    @ExtractBy(value = "//div[@class='mod_video_intro mod_video_intro_boke']/div[@class='video_info cf']/p/a/span/text()", multi = true)
    private List<String> keywords;
    @ExtractBy(value = "//div[@class='mod_video_user']/div[@class='video_user_detail']/ul/li[@class='item_name']/a/text()")
    private String author;
    @ExtractBy(value = "//div[@id='mainBody']/div[@class='top_reply']/h1/a/span/text()")
    private String comments;

    @ExtractBy(value = "//div[@class='mod_action cf']/div[@class='played_count']/em/strong/text()")
    private String playstat;

    @ExtractBy(value = "//div[@class='mod_video_user']/div[@class='video_user_detail']/ul/li[@class='item_followers']/em/text()")
    private String favstat;
    private String upstat;
    private String downstat;
    private String title;
    @ExtractBy(value = "//div[@class='mod_player_head cf']/div[@class='mod_crumbs_titles']/div[@class='mod_crumbs_wrap']/div[@class='mod_crumbs']/a[1]/text()")
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
       if (this.playstat != null && this.playstat.contains(".")) {
            this.playstat = this.playstat + "万";
        }
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
