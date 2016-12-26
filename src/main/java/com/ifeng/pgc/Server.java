package com.ifeng.pgc;

import com.ifeng.pgc.core.BeanLoader;

/**
 * Created by zhanglr on 2016/6/14.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("beginning....");
        BeanLoader.getContext();
        System.out.println("initializing over");
        System.out.println("spide beginning");
//
//        Master master = new Master();
//        master.start();
//        Worker worker = new Worker();
//        worker.connect();
    }
}