package com.example;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 支持用户验证的池连
 */
public class MongoDBAuthPoolUtil {
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

            // //创建一个封装用户认证信息,【用户名，验证数据库的名称，最后一个参数是toChar类型的数组】
            MongoCredential credential = MongoCredential.createCredential("itsxt", "develop", "itsxtpwd".toCharArray());
            ServerAddress address = new ServerAddress("192.168.112.128", 27017);
            //若是集群版MongoDB，可以将第一个参数写成list集合中Arrays.asList(address)
            client = new MongoClient(address, credential, builder.build());
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

    //创建集合
    public  static  void createCollection(String dbName,String cooName){
        MongoDatabase database =getDatabase(dbName);
        database.createCollection(cooName);
    }

    //删除集合
    public static  void dropCollection(MongoCollection collection){
        collection.drop();
    }
}
