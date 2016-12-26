package com.ifeng.pgc.core.mongo;


import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoFactory {

	private static MongoCli instance;

	/**
	 * 默认数据库
	 * @return
	 */
	public static MongoCli getInstance()  {
		if (instance == null){
			synchronized (MongoFactory.class) {
				if (instance == null){
					ServerAddress addr20 = new ServerAddress("36.110.204.91", 27017);
					List<ServerAddress> list = new ArrayList<>();
					list.add(addr20);
					MongoCredential credential = MongoCredential.createCredential("spider", "spider", "aT4QTEThwkfDZWAEJb4B".toCharArray());

					instance = new MongoCli(list, Arrays.asList(credential));
				}
			}
		}
		return instance;
	}

	public static MongoCli newInstance(){
		ServerAddress addr20 = new ServerAddress("36.110.204.91", 27017);
		List<ServerAddress> list = new ArrayList<>();
		MongoCredential credential = MongoCredential.createCredential("spider", "spider", "aT4QTEThwkfDZWAEJb4B".toCharArray());
		list.add(addr20);
		instance = new MongoCli(list, Arrays.asList(credential));
		
		return instance;
	}

	public static MongoCli newInstance(String db){
		ServerAddress addr20 = new ServerAddress("36.110.204.91", 27017);
		List<ServerAddress> list = new ArrayList<>();
		MongoCredential credential = MongoCredential.createCredential("spider", "spider", "aT4QTEThwkfDZWAEJb4B".toCharArray());
		list.add(addr20);
		MongoCli client = new MongoCli(list, Arrays.asList(credential));

		client.changeDb(db);
		return client;
	}
}
