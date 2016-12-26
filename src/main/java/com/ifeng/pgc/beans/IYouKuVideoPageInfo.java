package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ComboExtract;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

/**
 * Created by zhanglr on 2016/6/14.
 */

@TargetUrl(value = "http://v.youku.com/v_show/*.html*", sourceRegion = "//div[@class='playArea']")
//@ExtractBy(value = "//div[@class='D_video']")
public class IYouKuVideoPageInfo {


    private List<String> keywords;
    @ExtractBy(value = "//div[@id='vpactionv5']/div[@class='yk-interact scroll-paction']/div[@class='yk-uploadinfo']/div[@class='yk-userinfo']/div[@class='user-name']/a/text()")
    private String author;
    @ComboExtract(value = {
            @ExtractBy(value = "//div[@class='fn-wrap']/div[@class='fn-comment']/div[@class='fn']/a/span[@class='label']/text()"),
            @ExtractBy(value = "(\\d+)评论", type = ExtractBy.Type.Regex)
    })
    private String comments;
    @ExtractBy(value = "//div[@class='fn-stat']/div[@class='fn']/a/div[@class='playstat']/span[@class='stat']/em/text()")
    private String playstat;
    @ExtractBy(value = "//div[@class='yk-uploadinfo']/div[@class='yk-userinfo']/div[@class='user-action']/div[@class='sub-state']/span/text()")
    private String favstat;
    @ExtractBy(value = "//div[@class='fns']/div[@class='fn-updown']/div[@class='fn']/div[@class='fn-up']/a/span[@class='num']/text()")
    private String upstat;
    @ExtractBy(value = "//div[@class='fns']/div[@class='fn-updown']/div[@class='fn']/div[@class='fn-down']/a/span[@class='num']/text()")
    private String downstat;
     @ExtractBy(value = "//div[@id='vpofficialtitlev5']/div[@class='base']/div[@class='base_info']/div[@class='guide']/div[@class='crumbs']/a[1]/text()")
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
