package com.ifeng.pgc.services;

import com.ifeng.pgc.persistence.IFlush;

import java.util.List;

/**
 * Created by zhanglr on 2016/6/14.
 */
public interface Service<T> extends IFlush,IPageUrlBuilder {

    T get() throws Exception;

    int update(T t) throws Exception;

    int updateOneById(T t) throws Exception;
     int updateOneByUrl(T t) throws Exception;

    List<T> selectUnSyncList() throws Exception;

    List<String> buildUrls() throws Exception;
}
