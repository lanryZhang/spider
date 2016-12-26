package com.ifeng.pgc.beans;

import com.ifeng.pgc.annotations.JsonProperties;
import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

/**
 * Created by zhanglr on 2016/6/13.
 */

//http://www.toutiao.com/pgc/ma/?media_id=5946910774&page_type=0&max_behot_time=0&count=10&version=2&platform=pc
//@TargetUrl("http://*toutiao.com/\\w+/*\\?page_type=0")
//@ExtractBy(value = "//div[@class='mediaFeed']/ul/li",multi = true)
@JsonProperties(Name = "list")
public class JinRiTouTiaoVideoInfo extends EntryCodec {
//    @ExtractBy(value = "//div[@class='rbox-inner']/a/text()", notNull=true)
    @JsonProperties(Name = "title")
    private String title;
//    @ExtractBy(value = "//div[@class='rbox-inner']/a/@href",notNull=true)
    @JsonProperties(Name = "source_url")
    private String url;
    private String createTime;
    private String statCount;
//    @ExtractBy(value = "//h1[@class='profile_name']/a/text() | //div[@class='info-inner']/p[@class='name']/text()",source = ExtractBy.Source.RawHtml)
    @JsonProperties(Name = "source")
    private String category;
    private int listPageId;
//    @ExtractBy("//div[@class='y-left lbox']/a[@class='img-wrap']/img/@src")
    @JsonProperties(Name = "pc_image_url")
    private String imgPath ;



    public String getImgPath() {
        return imgPath == null?null:imgPath.replace("list/126x82","large");
    }


    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getListPageId() {
        return listPageId;
    }

    public void setListPageId(int listPageId) {
        this.listPageId = listPageId;
    }
    @Override
    public String toString(){
        return this.title;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatCount() {
        return statCount;
    }

    public void setStatCount(String statCount) {
        this.statCount = statCount;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void decode(DataLoader loader) {
        this.title = loader.getString("title");
        this.url = loader.getString("source_url");
        this.category = loader.getString("source");
        this.imgPath = loader.getString("pc_image_url");
    }
}
