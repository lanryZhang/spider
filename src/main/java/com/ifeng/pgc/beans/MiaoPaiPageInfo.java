package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ComboExtract;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * Created by zhanglr on 2016/6/14.
 */

@TargetUrl(value = "http://www.miaopai.com/*/*.htm",sourceRegion ="//div[@class='D_left']" )
@ExtractBy(value = "//div[@class='D_video']")
public class MiaoPaiPageInfo {

    private String keywords;
    @ExtractBy(value = "//a/@title")
    private String author;
    @ComboExtract(value = {
            @ExtractBy(value = "//div[@class='talk']/div[@class='talk_action clearfix']/a[2]/text()"),
            @ExtractBy(value = "评论 (\\d+)", type = ExtractBy.Type.Regex)
    })
    private String comments;

    @ComboExtract(value = {
            @ExtractBy(value = "//h2/b/text()"),
            @ExtractBy(value = "(\\d*[,]*[.]*\\d*[\\u4E00-\\u9FA5]?) 观看", type = ExtractBy.Type.Regex)
    })

    private String playstat;
    private String favstat;
    @ExtractBy(value = "//div[@class='talk']/div[@class='talk_action clearfix']/a[@class='a liked']/em/text()")
    private String upstat;
    private String downstat;


    @ExtractBy(value = "//div[@class='introduction']/p/text()")
    private String title;


    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
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
}
