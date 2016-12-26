package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.beans.VideoEntityInfo;
import com.ifeng.pgc.dao.DownloadDao;
import com.ifeng.pgc.services.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanglr on 2016/7/12.
 */
@Service("downloadServiceImpl")
public class DownloadServiceImpl implements DownloadService {

    @Autowired
    private DownloadDao downloadDao;
    @Override
    public List<VideoEntityInfo> getYouKuList() throws Exception {
        return downloadDao.getYouKuList();
    }

    @Override
    public List<VideoEntityInfo> getMiaoPaiList() throws Exception {
        return downloadDao.getMiaoPaiList();
    }

    @Override
    public List<VideoEntityInfo> getJinRiTouTiaoList()throws Exception  {
        return downloadDao.getJinRiTouTiaoList();
    }

    @Override
    public List<VideoEntityInfo> getSohuList()throws Exception  {
        return downloadDao.getSohuList();
    }

    @Override
    public List<VideoEntityInfo> getQQList()throws Exception  {
        return downloadDao.getQQList();
    }

    @Override
    public List<VideoEntityInfo> get56List()throws Exception  {
        return downloadDao.get56List();
    }

    @Override
    public List<VideoEntityInfo> getIQiyiList() throws Exception {
        return downloadDao.getIQiyiList();
    }

    @Override
    public List<VideoEntityInfo> getLetvList() throws Exception {
        return downloadDao.getLetvList();
    }
}
