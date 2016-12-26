package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ComboExtract;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

/**
 * Created by zhanglr on 2016/6/14.
 */

@TargetUrl(value = "http://v.qq.com/page/*/*/*/*.html", sourceRegion = "//div[@class='txp_player']")
//@ExtractBy(value = "//div[@class='D_video']")
public class QQVideoPageInfo {


    @ExtractBy(value = "//div[@class='mod_intro']/div[@class='video_info']/div[@class='video_brief']/span/a/text()", multi = true)
    private List<String> keywords;
    @ExtractBy(value = "//div[@class='mod_intro']/div[@class='video_user']/a/span/text()")
    private String author;
    @ComboExtract(value = {
            @ExtractBy(value = "//div[@id='mainBody']/div[@class='top_reply']/h1/a/span/text()"),
            @ExtractBy(value = "(\\d*)条评论", type = ExtractBy.Type.Regex)})
    private String comments;
    @ExtractBy(value = "//div[@class='mod_action cf']/div[@class='action_wrap cf']/div[@class='action_item action_count']/a/span/em/text()")
    private String playstat;
    @ExtractBy(value = "//div[@class='mod_intro']/div[@class='video_user']/a[@class='btn_book']/span/text()")
    private String favstat;
    private String upstat;
    private String downstat;
    private String title;
    @ExtractBy(value = "//div[@class='navigation_inner cf']/div[@class='nav_item current']/a/text()")
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
