package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.beans.UrlEntity;
import com.ifeng.pgc.dao.VideoDao;
import com.ifeng.pgc.services.IListUrlBuilder;
import com.ifeng.pgc.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanglr on 2016/7/30.
 */
@Service(value = "videoServiceImpl")
public class VideoServiceImpl implements IListUrlBuilder,VideoService {
    @Autowired
    private VideoDao videoDao;
    @Override
    public List<UrlEntity> selectListUrls(String date, String site) throws Exception {
        return videoDao.selectListUrls(date,site);
    }

    @Override
    public void updateSpideDateById(String id,String lastSpideStatus) throws Exception {
        videoDao.updateSpideDateById(id,lastSpideStatus);
    }

}
