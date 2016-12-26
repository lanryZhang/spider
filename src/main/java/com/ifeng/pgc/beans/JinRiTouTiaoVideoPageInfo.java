package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ComboExtract;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

/**
 * Created by zhanglr on 2016/6/14.
 */

@TargetUrl(value = "http://toutiao.com/*/*", sourceRegion = "//div[@class='detail-main player']")
//@ExtractBy(value = "//div[@class='D_video']")
public class JinRiTouTiaoVideoPageInfo {


    private List<String> keywords;
    @ExtractBy(value = "//div[@id='pagelet-ndetailbar']/div[@class='image-actions']/div[@class='author clearfix']/a[@class='name']/text()")
    private String author;
    @ExtractBy(value = "//div[@class='cheader clearfix']/div[@class='ctotal']/span[@class='ctotalnum']/text()")
    private String comments;
    @ComboExtract(value = {
            @ExtractBy(value = "//div[@id='pagelet-player-detail']/div[@class='detail-main']/p/span[2]/text()"),
            @ExtractBy(value = "(\\d+)次", type = ExtractBy.Type.Regex)
    })
    private String playstat;
    private String favstat;
    @ExtractBy(value = "//div[@id='pagelet-ndetailbar']/div[@class='image-actions']/a[@class='abtn abtn-digg']/text()")
    private String upstat;
    @ExtractBy(value = "//div[@id='pagelet-ndetailbar']/div[@class='image-actions']/a[@class='abtn abtn-bury']/text()")
    private String downstat;
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
