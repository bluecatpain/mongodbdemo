package com.example;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 添加文档
 */
public class InsertDocument {
    public static void main(String[] args) {
    InsertDocument insertDocument = new InsertDocument();
   // insertDocument.insertSingleDocument();
        insertDocument.insertManyDocument();
    }

    /**
     * 添加单个文档
     */
    public void insertSingleDocument(){
        //获取集合
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        //{}----表示document对象.也就是文档内容要写在document中
        //append(String key,Object value)----(key,value)
        Document docu =new Document();
        //数组内容需要使用list,不能直接new数组，因此使用Arrays.asList()转换
        docu.append("username","list").append("userage", 26).append("userdesc", "Very Good").append("userlike", Arrays.asList(new String[]{"Music,Sport"}));
        collection.insertOne(docu);

    }

    /**
     * 文档批量添加
     */

    public void insertManyDocument(){
        //获取集合
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        //需要将文档内容放在list中
        List<Document> list =new ArrayList<>();
        for (int i =0 ;i<5;i++){
            Document document =new Document();
            document.append("username", "zhangsan"+i);
            document.append("userage",20+i);
            document.append("userdesc", "ok"+i);
            document.append("userlike", Arrays.asList(new String[]{"Music","Sport"}));
            list.add(document);
        }
        collection.insertMany(list);
    }
}
