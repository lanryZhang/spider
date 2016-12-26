package com.ifeng.pgc.persistence;

/**
 * Created by zhanglr on 2016/6/14.
 */
public interface IFlush<T> {
    void flush(T t,String pgcName,String pgcId,int priority) throws Exception;

    Object flush(T t, String pgcName,String pgcId,int priority,boolean upset) throws Exception;

}