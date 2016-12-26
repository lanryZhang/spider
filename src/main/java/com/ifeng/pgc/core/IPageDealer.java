/*
* IPageDealer.java
* Created on  202016/11/8 0008 9:38
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved
*/
package com.ifeng.pgc.core;

import com.ifeng.pgc.beans.UrlEntity;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * Created by zhusy on 2016/11/8 0008.
 */
public interface IPageDealer {
    void deal(Page page, Spider spider);
    void reBuildUrl(List<UrlEntity> urlList);
}
