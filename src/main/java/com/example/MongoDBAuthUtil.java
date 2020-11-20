package com.example;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

/**
 * 创建MongoDB连接——使用用户验证
 */
public class MongoDBAuthUtil {
    //创建连接
    private static MongoClient client = null;

    static {
        if (client == null) {
            //创建一个封装用户认证信息,最后一个参数是toChar类型的数组
            MongoCredential credential = MongoCredential.createCredential("itsxt", "develop", "itsxtpwd".toCharArray());
            //封装MongoDB的地址信息和端口
            ServerAddress address = new ServerAddress("192.168.112.128", 27017);
            client = new MongoClient(address, Arrays.asList(credential));//Arrays.asList()将数组转化成List集合的方法。
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
