package com.example;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class AggregateOper {
    public static void main(String[] args) {
        AggregateOper aggregateOper = new AggregateOper();
       // aggregateOper.selectDocumentAggregateCount();
       // aggregateOper.selectDocumentAggregateSum();
       // aggregateOper.selectDocumentAggregateGroupBySum();
        //aggregateOper.selectDocumentAggregateGroupByWhere();
        aggregateOper.selectDocumentAggregateGroupByHaving();
    }
    /**
     * 需求：查询集合中的文档数量,将查询的值付给count，这个名字随意起
     *  db.dev.aggregate([{$group:{_id:null,count:{$sum:1}}}])
     */
    public void selectDocumentAggregateCount(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "dev");
        Document sum = new org.bson.Document();
        sum.put("$sum", 1);//{$sum:1}

        Document count =new Document();
        count.put("_id", null);
        count.put("count", sum);

        Document group = new Document();
        group.put("$group", count);
        List<Document> list =new ArrayList<>();
        list.add(group);
        AggregateIterable iterable =collection.aggregate(list);//需要一个list来给定条件
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document document =cursor.next();
            System.out.println(document.get("count"));
        }
    }

    /**
     * 需求：查询集合中所有size键中的值总和
     * db.dev.aggregate([{$group:{_id:null,totalSize:{$sum:"$size"}}}])
     */
    public void selectDocumentAggregateSum(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "dev");
        Document sum =new Document();
        sum.put("$sum", "$size");

        Document totalSize = new Document();
        totalSize.put("_id", null);
        totalSize.put("totalSize",sum);

        Document group =new Document();
        group.put("$group", totalSize);

        List<Document> list =new ArrayList<>();
        list.add(group);

        AggregateIterable iterable =collection.aggregate(list);//需要一个list来给定条件
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()) {
            Document document = cursor.next();
            System.out.println(document.get("totalSize"));
        }
    }

    /**
     * 需求：查询集合中所有size键中的值总和
     * db.dev.aggregate([{$group:{_id:"$title",totalSize:{$sum:"$size"}}}])
     * !!添加到list的顺序决定了是先进行条件，再分组，还是先分组，再筛选
     */
        public void selectDocumentAggregateGroupBySum(){
            MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "dev");
            Document sum = new Document();
            sum.put("$sum", "$size");

            Document totalSize =new Document();
            totalSize.put("_id", "$title");
            totalSize.put("totalSize", sum);

            Document group = new Document();
            group.put("$group", totalSize);

            List<Document> list =new ArrayList<>();
            list.add(group);

            AggregateIterable iterable =collection.aggregate(list);//需要一个list来给定条件
            MongoCursor<Document> cursor = iterable.iterator();
            while(cursor.hasNext()) {
                Document document = cursor.next();
                System.out.println(document.get("totalSize"));
            }
        }

    /**
     * 需求；查询dev集合中有多少文档的size大于200
     * db.dev.aggregate([{$match:{size:{$gt:200}}},{$group:{_id:null,totalSize:{$sum:1}}}])
     */

    public void selectDocumentAggregateGroupByWhere(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "dev");
        Document gt = new Document();
        gt.put("$gt", 200);

        Document size = new Document();
        size.put("size", gt);

        Document match = new Document();
        match.put("$match", size);

        Document sum = new Document();
        sum.put("$sum", 1);

        Document totalSize =new Document();
        totalSize.put("_id", null);
        totalSize.put("totalSize", sum);

        Document group = new Document();
        group.put("$group", totalSize);

        List<Document> list = new ArrayList<>();
        list.add(match);
        list.add(group);
        collection.aggregate(list);

        AggregateIterable iterable =collection.aggregate(list);//需要一个list来给定条件
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()) {
            Document document = cursor.next();
            System.out.println(document.get("totalSize"));
        }
    }

    /**
     * 需求：查询dev集合，根据title分组计算每组size的总和，并过滤掉总和小于200的文档
     * db.dev.aggregate([{$group:{_id:"$title",totalSize:{$sum:"$size"}}},{$match:{totalSize:{$gt:200}}}])
     */
    public void selectDocumentAggregateGroupByHaving(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "dev");
        Document sum = new Document();
        sum.put("$sum", "$size");

        Document totalSize =new Document();
        totalSize.put("_id", "$title");
        totalSize.put("totalSize", sum);

        Document group = new Document();
        group.put("$group", totalSize);

        Document gt = new Document();
        gt.put("$gt", 200);

        Document total = new Document();
        total.put("totalSize", gt);

        Document match = new Document();
        match.put("$match", total);

        List<Document> list = new ArrayList<>();
        list.add(group);
        list.add(match);

        AggregateIterable iterable =collection.aggregate(list);//需要一个list来给定条件
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()) {
            Document document = cursor.next();
            System.out.println(document.get("totalSize"));
        }

    }
}
