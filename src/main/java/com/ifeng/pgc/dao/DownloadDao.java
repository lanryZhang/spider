package com.ifeng.pgc.dao;

import com.ifeng.pgc.beans.VideoEntityInfo;

import java.util.List;

/**
 * Created by zhanglr on 2016/7/12.
 */
public interface DownloadDao {
    List<VideoEntityInfo> getYouKuList() throws Exception;
    List<VideoEntityInfo> getMiaoPaiList() throws Exception;
    List<VideoEntityInfo> getJinRiTouTiaoList() throws Exception;
    List<VideoEntityInfo> getSohuList() throws Exception;
    List<VideoEntityInfo> getQQList() throws Exception;
    List<VideoEntityInfo> get56List() throws Exception;
    List<VideoEntityInfo> getIQiyiList() throws Exception;
    List<VideoEntityInfo> getLetvList() throws Exception;

}
