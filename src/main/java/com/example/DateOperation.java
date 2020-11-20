package com.example;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Arrays;
import java.util.Date;

public class DateOperation {
    public static void main(String[] args) {
        DateOperation operation = new DateOperation();
        //operation.insertDocumentSystemDate();
        //operation.insertDocumentCustoDate();
        //operation.selectDocumentDateUserEq();
        operation.selectDocumentDateGt();
    }

    /**
     * 插入系统当前日期
     */
    public void insertDocumentSystemDate(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        //创建文档
        Document document = new Document();
        document.put("username", "wangwu");
        document.put("userage", 23);
        document.put("userdesc", "Very Good");
        document.put("userlike", Arrays.asList(new String[]{"Music","Art"}));
        document.put("userbirt", new Date());
        collection.insertOne(document);
    }
    /**
     * 插入指定日期
     */
    public void insertDocumentCustoDate(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        //创建文档
        Document document = new Document();
        document.put("username", "zhaoliu");
        document.put("userage", 24);
        document.put("userdesc", "Very Good");
        document.put("userlike", Arrays.asList(new String[]{"Music","Art"}));
        //需要插入时间2020-11-19 10:23:59
        Date date = DateUtil.StringToDate("yyyy-MM-dd HH:mm:ss","2020-11-19 10:23:59");
        document.put("userbirt", date);
        collection.insertOne(document);
    }

    /**
     * 查询用户的生日 "2020-11-19 10:23:59"的用户信息
     */
    public void selectDocumentDateUserEq() {
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        Date date = DateUtil.StringToDate("yyyy-MM-dd HH:mm:ss", "2020-11-19 10:23:59");
        FindIterable iterable = collection.find(Filters.eq("userbirt", date));
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document docu = cursor.next();
            String temp =DateUtil.dateToString("yyyy-MM-dd HH:mm:ss", (Date)docu.get("userbirt"));
            System.out.println(docu.get("username") + "\t" + docu.get("userage") + "\t" + docu.get("userdesc") + "\t" + docu.get("userlike")+"\t"+temp);

        }
    }
    /**
     * 查询用户生日，大于"2020-11-19 00:00:00"的用户信息
     */
    public  void selectDocumentDateGt(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        Date date = DateUtil.StringToDate("yyyy-MM-dd HH:mm:ss", "2020-11-19 00:00:00");
        FindIterable iterable = collection.find(Filters.gt("userbirt", date));
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document docu = cursor.next();
            String temp =DateUtil.dateToString("yyyy-MM-dd HH:mm:ss", (Date)docu.get("userbirt"));
            System.out.println(docu.get("username") + "\t" + docu.get("userage") + "\t" + docu.get("userdesc") + "\t" + docu.get("userlike")+"\t"+temp);

        }
    }
}
