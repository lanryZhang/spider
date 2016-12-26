package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ComboExtract;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

/**
 * Created by zhanglr on 2016/6/14.
 */

@TargetUrl(value = "http://my.tv.sohu.com/us/*/*.shtml", sourceRegion = "//embed[@id='player']")
//@ExtractBy(value = "//div[@class='D_video']")
public class SohuPageInfo {


    @ExtractBy(value = "//div[@class='left']/div[@class='jieshao cfix']/div[@class='colR']/div[@class='jtag cfix']/p[@class='sr']/a/text()", multi = true)
    private List<String> keywords;
    @ExtractBy(value = "//div[@class='left']/div[@class='jieshao cfix']/div[@class='colR']/div[@class='rowA cfix']/p/a/text()")
    private String author;
    @ComboExtract(value = {
            @ExtractBy(value = "//div[@class='remark']/div[@id='commList']/div[@class='rTit c-black']/span/text()"),
            @ExtractBy(value = "(\\d+)条评论", type = ExtractBy.Type.Regex)
    })
    private String comments;
    @ExtractBy(value = "//div[@class='left']/div[@class='videoBox cfix']/div[@class='vBox vBox-play']/span/em/i/text()")
    private String playstat;
    @ExtractBy(value = "//div[@class='left']/div[@class='jieshao cfix']/div[@class='colR']/div[@class='rowA cfix']/div[@class='rel l readtxt']/span/text()")
    private String favstat;
    @ExtractBy(value = "//div[@class='left']/div[@class='videoBox cfix']/div[@class='vBox vBox-ding']/a/em/i/text()")
    private String upstat;
    @ExtractBy(value = "//div[@class='left']/div[@class='videoBox cfix']/div[@class='vBox vBox-cai']/a/em/i/text()")
    private String downstat;
    @ExtractBy(value = "//div[@class='left']/div[@class='crumbs']/a[1]/text()")
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
