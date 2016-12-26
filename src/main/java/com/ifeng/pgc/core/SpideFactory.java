package com.ifeng.pgc.core;

import us.codecraft.webmagic.Spider;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhanglr on 2016/8/2.
 */
public class SpideFactory {
    public final static ConcurrentHashMap<String,Spider> SPIDER_CONTAINER = new ConcurrentHashMap<>();

    public static Spider get(String spideName){
        return SPIDER_CONTAINER.get(spideName);
    }

    public static void put(String spideName,Spider spider){
        SPIDER_CONTAINER.put(spideName,spider);
    }

    public static Boolean contains(String spideName){
        return SPIDER_CONTAINER.containsKey(spideName);
    }
}
