package com.ifeng.pgc.beans;

import com.ifeng.pgc.core.mongo.DataLoader;
import com.ifeng.pgc.core.mongo.EntryCodec;

/**
 * Created by zhanglr on 2016/8/25.
 */
public class UserAgentInfo extends EntryCodec {
    private int id;
    private String userAgent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public void decode(DataLoader loader) {
        this.id = loader.getInt("id");
        this.userAgent = loader.getString("userAgent");
    }
}
