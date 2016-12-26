package com.ifeng.pgc.services;

import com.ifeng.pgc.beans.EIPInfo;
import com.ifeng.pgc.core.Context;

import java.util.List;

/**
 * Created by zhanglr on 2016/9/2.
 */
public interface EIPService {
    void execute(Context context);

    EIPInfo getEIpInfo(String hostIp) throws Exception ;

    List<String> getAllHostIp() throws Exception ;
}
