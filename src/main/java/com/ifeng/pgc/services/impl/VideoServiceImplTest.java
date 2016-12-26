package com.ifeng.pgc.services.impl;/*
* VideoDaoImplTest.java 
* Created on  202016/12/8 17:15 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/

import com.ifeng.pgc.beans.UrlEntity;
import com.ifeng.pgc.dao.VideoDao;
import com.ifeng.pgc.services.IListUrlBuilder;
import com.ifeng.pgc.services.VideoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
@Service(value = "videoServiceImplTest")
public class VideoServiceImplTest implements IListUrlBuilder,VideoService {

    @Override
    public List<UrlEntity> selectListUrls(String date, String site) throws Exception {
        UrlEntity en = new UrlEntity();
        en.setUrl("http://mp.weixin.qq.com/profile?src=3&timestamp=1481181678&ver=1&signature=ASLoiBqtoL9PVGZRU8p*kgKDeSqVqc7oa*SP*3sR48vUmWtWoWfoG0g5CgtCIBzr-G7h9LydeQQFrwFqcub9JQ==");
        en.setPgcName("甘肃教师学苑");
        en.setPriority(5);
        en.setSpideType(0);
        List<UrlEntity> res = new ArrayList<>();
        res.add(en);
        return res;
    }

    @Override
    public void updateSpideDateById(String id, String lastSpideStatus) throws Exception {

    }
}
