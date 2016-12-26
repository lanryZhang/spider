package com.ifeng.pgc.dao;

import com.ifeng.pgc.beans.UrlEntity;

import java.util.List;

/**
 * Created by zhanglr on 2016/7/30.
 */
public interface VideoDao {
    List<UrlEntity> selectListUrls(String date,String site) throws Exception;

    void updateSpideDateById(String id, String lastSpideStatus) throws Exception;
}
