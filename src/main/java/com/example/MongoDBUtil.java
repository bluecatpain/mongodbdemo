package com.example;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 连接MongoDB工具类，不支持验证
 */
public class MongoDBUtil {
    private static MongoClient client = null;

    static {
        if (client == null) {
            client = new MongoClient("192.168.112.128", 27017);
        }
    }

    //获取MongoDB数据库
    public static MongoDatabase getDatabase(String dbName) {
        return client.getDatabase(dbName);
    }

    //获取集合
    public static MongoCollection getCollection(String dbName, String collName) {
        MongoDatabase database = getDatabase(dbName);
        return database.getCollection(collName);
    }
}
