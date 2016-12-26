package com.ifeng.pgc.dao;

import com.ifeng.pgc.beans.EIPInfo;

import java.util.List;

/**
 * Created by zhanglr on 2016/9/5.
 */
public interface EIPDao {
    EIPInfo getEIpInfo(String hostIp) throws Exception;
    void insert(EIPInfo en) throws Exception;
    List<String> getAllHostIp() throws Exception ;
}
