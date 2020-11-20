package com.example;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Mongo分页查询
 */
public class SelectDocumentByPage {
    public static void main(String[] args) {
        SelectDocumentByPage page = new SelectDocumentByPage();
       // page.selectDocumentByPageUserSkipAndLimit(2);
        // page.selectDocumentByPageUseCondition(1, 2, null);
        page.selectDocumentByPageUseCondition(2, 2, "5fb286c25768e37ba0936ba6");

    }
    /**
     * 通过skip和limit方法实现分页查询
     * skip跳过多少条，limit取多少条（每页显示条数）
     * 只适用于文档数量小的情况，会进行全局查询
     */
    public void selectDocumentByPageUserSkipAndLimit(int PageIndex){
        int page =(PageIndex-1)*2;//2是每页显示条数
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "dev");
       //{size:${$ne:null}}
        Document condition =new Document("size",new Document("$ne",null));
        Long countNum = collection.countDocuments(condition);
        System.out.println(countNum);
        FindIterable iterable = collection.find(condition).skip(page).limit(2);

        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    /**
     * 使用条件判断实现分页
     * 当前页，查询条数，本页的最后一条数据的id
     * 不支持跳页查询
     */

    public void selectDocumentByPageUseCondition(int pageIndex,int pageSize,String lastId){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "dev");
        Document condition =new Document("size",new Document("$ne",null));
        Long countNum = collection.countDocuments(condition);
        System.out.println(countNum);

        FindIterable iterable = null;
        if (pageIndex==1){
            iterable =collection.find(condition).limit(pageSize);

        }else{
            if (lastId!=null){
                //lastId表示当前页最后一个数据id
                condition.append("_id",new Document("$gt",new ObjectId(lastId)) );
                iterable =collection.find(condition).limit(pageSize);
            }
        }
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
}
