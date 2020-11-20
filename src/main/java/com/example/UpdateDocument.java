package com.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

/**
 * 更新文档
 */
public class UpdateDocument {
    public static void main(String[] args) {
        UpdateDocument document = new UpdateDocument();
       // document.updateSingleDocumentKey();
       // document.updateSingleDocumentManyKey();
       // document.updateManyDocumentSingleKey();
       // document.updateManyDocumentManyKey();
        document.updateDocumentArray();
    }
    /**
     * 更新单个文档单个键
     */

    public void updateSingleDocumentKey(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        //更新文档
        //Filters封装了条件的一个工具类[查询参数key，做一个什么条件判断]，参数更新的文档
        //一个{}就相当于document对象  相当于db.test.update({username:"list"},{$set:{userage:28}})
        collection.updateOne(Filters.eq("username","list"),new Document("$set",new Document("userage",28)));
    }
    /**
     * 更新单个文档的多个键
     */
    public void updateSingleDocumentManyKey(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        collection.updateOne(Filters.eq("username","zhangsan0"), new Document("$set",new Document("userage",18).append("userdesc", "Very Good")));
    }

    /**
     * 更新多个文档的单个键
     */
    public void updateManyDocumentSingleKey(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
       //username不为空
        collection.updateMany(Filters.ne("username", null), new Document("$set",new Document("userdesc","VERY GOOD")));
    }

    /**
     * 更新多个文档的多个键
     */
    public void updateManyDocumentManyKey(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        collection.updateMany(Filters.ne("username", null), new Document("$set",new Document("userdesc","OK").append("userage", 20)));
    }

    /**
     * 更新文档中的数组
     */
    public void updateDocumentArray(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        collection.updateOne(Filters.eq("username","list"),new Document("$push",new Document("userlike","Art")) );
    }
}
