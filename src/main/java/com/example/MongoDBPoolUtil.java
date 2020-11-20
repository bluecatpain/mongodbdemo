package com.example;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 使用连接池连接
 */
public class MongoDBPoolUtil {
    public static MongoClient client = null;

    //获取连接对象
    static {
        if (client == null) {
            //设置连接池
            MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
            //设置连接池属性
            builder.connectionsPerHost(10);//每个地址的最大连接数，默认10
            builder.connectTimeout(5000);//连接超时时间,单位是毫秒
            builder.socketTimeout(5000);//设置读写操作超时时间
            ServerAddress address = new ServerAddress("192.168.112.128", 27017);
            client = new MongoClient(address, builder.build());
        }
    }

    //获取数据库
    public static MongoDatabase getDatabase(String dbName) {
        return client.getDatabase(dbName);
    }

    //获取集合
    public static MongoCollection getCollection(String dbName, String collName) {
        MongoDatabase database = getDatabase(dbName);
        return database.getCollection(collName);
    }
}
