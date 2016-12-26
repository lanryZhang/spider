package com.ifeng.pgc.services.impl;

import com.ifeng.pgc.beans.*;
import com.ifeng.pgc.dao.YouKuDao;
import com.ifeng.pgc.services.Service;
import com.ifeng.pgc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhanglr on 2016/6/14.
 */
@org.springframework.stereotype.Service(value = "youKuServiceImpl")
public class YouKuServiceImpl implements Service {
    @Autowired
    private YouKuDao youKuDao;

    @Override
    public void flush(Object list,String setPgcName,String pgcId,int priority) throws Exception {
        List ll = (List) list;
        List<VideoEntityInfo> result = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < ll.size(); i++) {
                VideoEntityInfo v = new VideoEntityInfo();
                v.setId(UUID.randomUUID().toString());
                if (ll.get(i) instanceof IYouku_I_VideoInfo) {
                    v.setTitle(((IYouku_I_VideoInfo) ll.get(i)).getTitle());
                    v.setUrl(((IYouku_I_VideoInfo) ll.get(i)).getUrl().trim());
                    v.setCategory(((IYouku_I_VideoInfo) ll.get(i)).getCategory());
                } else if (ll.get(i) instanceof IYouku_U_VideoInfo) {
                    v.setTitle(((IYouku_U_VideoInfo) ll.get(i)).getTitle());
                    v.setUrl(((IYouku_U_VideoInfo) ll.get(i)).getUrl().trim());
                    v.setCategory(((IYouku_U_VideoInfo) ll.get(i)).getCategory());
                } else {
                    v.setTitle(((IYouku_Other_VideoInfo) ll.get(i)).getTitle());
                    v.setUrl(((IYouku_Other_VideoInfo) ll.get(i)).getUrl().trim());
                    v.setCategory(((IYouku_Other_VideoInfo) ll.get(i)).getCategory());
                }
                v.setCreateTime(DateUtil.now());
                v.setPriority(priority);
                v.setPgcName(setPgcName);
                v.setPgcId(pgcId);
                v.setStatus(0);
                v.setUpdateStatus(0);
                result.add(v);
            }

        }
        youKuDao.saveBatch(result);
    }

    @Override
    public Object flush(Object list,String setPgcName,String pgcId,int priority, boolean upset) throws Exception {
        List ll = (List) list;
        List<VideoEntityInfo> result = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < ll.size(); i++) {
                VideoEntityInfo v = new VideoEntityInfo();
                v.setId(UUID.randomUUID().toString());
                if (ll.get(i) instanceof IYouku_I_VideoInfo) {
                    v.setTitle(((IYouku_I_VideoInfo) ll.get(i)).getTitle());
                    v.setUrl(((IYouku_I_VideoInfo) ll.get(i)).getUrl().trim());
                    v.setCategory(((IYouku_I_VideoInfo) ll.get(i)).getCategory());
                } else if (ll.get(i) instanceof IYouku_U_VideoInfo) {
                    v.setTitle(((IYouku_U_VideoInfo) ll.get(i)).getTitle());
                    v.setUrl(((IYouku_U_VideoInfo) ll.get(i)).getUrl().trim());
                    v.setCategory(((IYouku_U_VideoInfo) ll.get(i)).getCategory());
                } else {
                    v.setTitle(((IYouku_Other_VideoInfo) ll.get(i)).getTitle());
                    v.setUrl(((IYouku_Other_VideoInfo) ll.get(i)).getUrl().trim());
                    v.setCategory(((IYouku_Other_VideoInfo) ll.get(i)).getCategory());
                }
                v.setCreateTime(DateUtil.now());
                v.setPriority(priority);
                v.setPgcName(setPgcName);
                v.setPgcId(pgcId);
                v.setStatus(0);
                v.setUpdateStatus(0);
                result.add(v);
            }

        }
        return youKuDao.saveBatch(result, upset);
    }

    @Override
    public Object get() throws Exception {
        return youKuDao.selectList();
    }

    @Override
    public int update(Object o) throws Exception {
        return youKuDao.updateOne((VideoEntityInfo) o);
    }

    @Override
    public int updateOneById(Object o) throws Exception {
        return youKuDao.updateOneById((VideoEntityInfo) o);
    }

    @Override
    public int updateOneByUrl(Object o) throws Exception {
        return youKuDao.updateOneByUrl((VideoEntityInfo) o);
    }

    @Override
    public List<VideoEntityInfo> selectUnSyncList() throws Exception {
        return youKuDao.selectUnSyncList();
    }

    @Override
    public List<String> buildUrls() throws Exception {
        List<String> list = new ArrayList<String>();
        List<VideoEntityInfo> temp = youKuDao.selectList();
        for (VideoEntityInfo url : temp) {
            list.add(url.getUrl().trim());
        }
        return list;
    }
}
