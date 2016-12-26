package com.ifeng.pgc.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanglr on 2016/6/27.
 */
public class Context {
    private Map<String,Object> context;

    public Context(){
        context = new HashMap<>();
    }
    public void put(String key,Object value){
         context.put(key,value);
    }
    public Object get(String key){
        return context.get(key);
    }
}
