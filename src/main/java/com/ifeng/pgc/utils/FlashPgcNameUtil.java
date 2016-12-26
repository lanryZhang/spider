package com.ifeng.pgc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ifeng.pgc.beans.UrlEntity;
import com.ifeng.pgc.core.mongo.MongoCli;
import com.ifeng.pgc.core.mongo.MongoSelect;
import com.ifeng.pgc.core.mongo.Where;
import com.ifeng.pgc.core.mongo.WhereType;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import us.codecraft.webmagic.selector.Html;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhusy on 2016/9/7.
 */
public class FlashPgcNameUtil {

    private static final String URL = "http://10.90.9.21/support/findWeMediaId";

    public static MongoCli getMongoCli() {
        List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
        ServerAddress s = new ServerAddress("36.110.204.91", 27017);
        serverAddresses.add(s);
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        MongoCredential mongoCredential = MongoCredential.createCredential("spider", "spider", "aT4QTEThwkfDZWAEJb4B".toCharArray());
        credentials.add(mongoCredential);
        return new MongoCli(serverAddresses, credentials);
    }

    public static void FreshPgcId() {
        MongoCli mongoCli = getMongoCli();
        mongoCli.changeDb("spider");
        mongoCli.getCollection("LIST_URLS");
        List<UrlEntity> urlList = null;
        try {

//            urlList = mongoCli.selectList(new MongoSelect().where("pgcId",WhereType.Equal,null), UrlEntity.class);
            urlList = mongoCli.selectList(new MongoSelect(), UrlEntity.class);
//
//            if (urlList != null) {
//                for (UrlEntity urlEntity : urlList){
//                    System.out.println(urlEntity.getPgcName());
//                }
//            }
            if (urlList != null) {
                int total = urlList.size();
                int count = 0;
                for (int i = 0; i < urlList.size(); i++) {
                    HttpResult result = HttpUtils.httpPost(URL, "pgcName=" + URLEncoder.encode(urlList.get(i).getPgcName(), "UTF-8"));
                    if (result != null && result.getBody() != null && !"".equals(result.getBody().toString())) {
                        JSONObject jsonObject = JSON.parseObject(result.getBody().toString());
                        if (jsonObject.getBoolean("success")) {
                            Where where = new Where();
                            HashMap<String, Object> fields = new HashMap<>();
                            fields.put("pgcId", jsonObject.getString("eAccountId"));
                            where.and("pgcName", WhereType.Equal, urlList.get(i).getPgcName());
                            mongoCli.update(fields, where);
                            System.out.println("Succeed to update the " + i + "th of " + total + "---[id:"+fields.get("pgcId")+",pgcName:"+urlList.get(i).getPgcName()+"]");
                            count++;
                        } else {
                            System.out.println("There isn't any PGC called " + urlList.get(i).getPgcName());
                        }
                    } else {
                        System.out.println("Failed to connect to the Server of the " + i + "th of " + total);
                    }
                }
                System.out.println("OK! Succeed to update the " + count + " of " + total);
            } else {
                System.out.println("Failed to get urlList from Mongo !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void FreshPgcName() {
        String site = "优酷_U";//"腾讯视频";//"秒拍";//"搜狐";//"优酷_I";//"今日头条";
        MongoCli mongoCli = getMongoCli();
        mongoCli.changeDb("spider");
        mongoCli.getCollection("LIST_URLS");
        MongoSelect select = new MongoSelect();
        select.where("site", WhereType.Equal, site)
                .where("pgcName", WhereType.Equal, null);
        List<UrlEntity> urlList = null;
        try {
            urlList = mongoCli.selectList(select, UrlEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (urlList != null) {
                for (UrlEntity urlEntity : urlList) {
                    String url = urlEntity.getUrl();
                    String title = getYoukuIPgcName(url);
                    Map<String, Object> fields = new HashMap<>();
                    fields.put("pgcName", title);
                    Where where = new Where();
                    where.and("id", WhereType.Equal, urlEntity.getId());
                    mongoCli.update(fields, where);
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getYoukuIPgcName(String url) throws Exception {
        HttpResult result = HttpUtils.httpGet(url);
        if (!"".equals(result.toString())) {
            Html temp = Html.create(result.getBody().toString());
            String title = temp.xpath("//title/text()").get();
            if (!title.contains("的自频道")) {
                return "";
            }
            title = title.substring(0, title.indexOf("的自频道"));
            return title;
        } else {
            return "";
        }
    }

    private static String getSohuPgcName(String url) throws Exception {
        String format = "http://my.tv.sohu.com/user/media/video.do?uid=%s";
        String uid = url.substring(url.indexOf("uid=") + 4, url.indexOf("&pg"));
        HttpResult result = HttpUtils.httpGet(String.format(format, uid));
        if (!"".equals(result.toString())) {
            Html temp = Html.create(result.getBody().toString());
            String title = temp.xpath("//title/text()").get();
            if (!title.contains("的视频空间")) {
                return "";
            }
            title = title.substring(0, title.indexOf("的视频空间"));
            return title;
        } else {
            return "";
        }
    }

    private static String getToutiaoPgcName(String url) throws Exception {
        HttpResult result = HttpUtils.httpGet(url);
        if (!"".equals(result.toString())) {
            Html temp = Html.create(result.getBody().toString());
            String title = temp.xpath("//meta[@name='keywords']/@content").get();
            return title;
        } else {
            return "";
        }
    }

    private static String getMiaopaiPgcName(String url) throws Exception {
        HttpResult result = HttpUtils.httpGet(url);
        if (!"".equals(result.toString())) {
            JSONObject json = JSON.parseObject(result.getBody().toString());
            String html;
            html = json.getString("msg");
            html = NativeAsciiUtils.ascii2Native(html);
            html.replace("\\\"", "");
            html.replace("\\", "");
            Html temp = Html.create(html);
            String title = temp.xpath("//div[@class='D_video']/div[@class='introduction']/div[@class='D_head_name']/h1/a/text()").get();
            List<String> urls = temp.xpath("//div[@class='D_video']/div[@class='list clearfix']/ol/li[3]/a/@href").all();
            return title;
        } else {
            return "";
        }
    }

    private static String getQQPgcName(String url) throws Exception {
        HttpResult result = HttpUtils.httpGet(url);
        if (!"".equals(result.toString())) {
            Html temp = Html.create(result.getBody().toString());
            List<String> urls = temp.xpath("//root/videolst/url/text()").all();
            if (urls.size() > 0) {
                String url2 = urls.get(0);
                String[] sprits = url2.split("/");
                String uid = sprits[sprits.length - 1];
                String format = "http://v.qq.com/x/page/%s";
                HttpResult result2 = HttpUtils.httpGet(String.format(format, uid));
                Html temp2 = Html.create(result2.getBody().toString());
                String title = temp2.xpath("//div[@class='video_user']/a[@class='user_info']/@title").get();
                return title;
            }
            return "";
        } else {
            return "";
        }
    }
}
