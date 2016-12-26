package com.ifeng.pgc.services;

import com.ifeng.pgc.beans.UrlEntity;

import java.util.List;

/**
 * Created by zhanglr on 2016/7/30.
 */
public interface IListUrlBuilder {
    List<UrlEntity> selectListUrls(String date, String site) throws Exception;
}
