package com.ifeng.pgc.beans;

import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;
import us.codecraft.webmagic.model.annotation.ComboExtract;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * Created by zhanglr on 2016/9/2.
 */
@TargetUrl(value = "http://weixin.sogou.com/weixin\\?query=*page=\\d+*")
@ExtractBy(value = "//div[@class='wx-rb wx-rb3']",multi = true)
public class SogouSearchInfo  extends EntryCodec {
    @ComboExtract(value = {@ExtractBy(value = "//div[@class='txt-box']/p/text()" ),
            @ExtractBy(value = "\\w*伽罗\\w*",type = ExtractBy.Type.Regex)})
    private String contents;
    @ExtractBy(value = "//div[@class='txt-box']/h4/a/text()")
    private String title;
    @ComboExtract(value = {
            @ExtractBy(value = "//div[@class='txt-box']/div[@class='s-p']/a[@class='wx-name']/text()"),
            @ExtractBy(value = "凤凰网博报",type = ExtractBy.Type.Regex)
    })
    private String category;

    @ExtractBy(value = "//div[@class='txt-box']/h4/a/@href")
    private String url;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void decode(DataLoader loader) {

    }
}
