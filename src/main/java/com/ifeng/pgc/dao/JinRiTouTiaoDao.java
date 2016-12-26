package com.ifeng.pgc.dao;

import com.ifeng.pgc.beans.VideoEntityInfo;

import java.util.List;

/**
 * Created by zhanglr on 2016/6/14.
 */
public interface JinRiTouTiaoDao {
    void saveBatch(List<VideoEntityInfo> list) throws Exception;

    Object saveBatch(List<VideoEntityInfo> o, boolean upset) throws Exception;

    VideoEntityInfo selectOne() throws Exception;

    List<VideoEntityInfo> selectList() throws Exception;

    int updateOne(VideoEntityInfo en) throws Exception;

    int updateOneById(VideoEntityInfo en) throws Exception;

    int updateOneByUrl(VideoEntityInfo en) throws Exception;

    List<VideoEntityInfo> selectUnSyncList() throws Exception;
}
