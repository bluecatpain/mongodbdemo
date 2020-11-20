package com.example;

import com.mongodb.client.MongoCollection;

public class MongoDBDemo {
    public static void main(String[] args) {

//        //创建连接对象
//        MongoClient client = new MongoClient("192.168.112.128", 27017);
//
//        //获取数据库——集合——文档
//        MongoDatabase database = client.getDatabase("develop");
//
//        //获取MongoDB集合
//        MongoCollection collection = database.getCollection("dev");

        MongoCollection collection  =MongoDBAuthPoolUtil.getCollection("develop", "dev");

        System.out.println("ok......");


//         //创建连接对象
//        MongoClient client = new MongoClient("192.168.112.128", 27017);
//
//        //获取数据库——集合——文档
//        MongoDatabase database = client.getDatabase("develop");

        //获取需要创建集合的数据库对象
//        MongoDatabase database =MongoDBAuthUtil.getDatabase("develop");
//        //创建test集合
//        database.createCollection("test");

//        MongoCollection coll =MongoDBAuthPoolUtil.getCollection("develop", "test");
//
//
//      MongoDBAuthPoolUtil.dropCollection(coll);

        //创建集合
        MongoDBAuthPoolUtil.createCollection("develop", "devtest");

    }

}
