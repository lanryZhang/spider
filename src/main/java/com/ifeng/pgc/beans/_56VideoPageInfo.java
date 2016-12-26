package com.ifeng.pgc.beans;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.List;

/**
 * Created by zhanglr on 2016/6/14.
 */

@TargetUrl(value = "http://www.56.com/*/*.html", sourceRegion = "//embed[@id='sohu_player']")
//@ExtractBy(value = "//div[@class='D_video']")
public class _56VideoPageInfo {


    private List<String> keywords;
    @ExtractBy(value = "//div[@class='play_action_main']/div[@class='user_info']/div[@class='user_box']/div[@class='user_info_extra']/h3/a[@class='user_name_txt']/text()")
    private String author;
    @ExtractBy(value = "//div[@class='lw_post_hd']/div[@class='lw_post_hd_extra']/div[@class='lw_meta_txt']/span[@class='post_meta']/strong/em/text()")
    private String comments;
    @ExtractBy(value = "//div[@class='play_info']/div[@class='video_title clearfix']/div[@class='video_title_extra']/span/em[1]/text()")
    private String playstat;
    @ExtractBy(value = "//div[@class='play_action_main']/div[@class='user_info']/div[@class='user_box']/div[@class='user_info_extra']/div[@class='user_rss']/span/text()")
    private String favstat;
    private String upstat;
    private String downstat;
    @ExtractBy(value = "//div[@class='video_title clearfix']/div[@class='video_title_main']/span/a[2]/text()")
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
