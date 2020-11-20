package com.example;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.regex.Pattern;

/**
 * 查询文档
 */
public class SelectDocument {
    public static void main(String[] args) {
        SelectDocument document = new SelectDocument();
        // document.selectDocument();
        //document.selectDocumentById();
        //document.selectDocumentConditionByGt();
       // document.selectDocumentConditionByType();
      //  document.selectDocumentConditionByIn();
       // document.selectDocumentConditionByNin();
      //  document.selectDocumentByRegex();
       // document.selectDocumentConditionUseAnd();
       // document.selectDocumentConditionUseOr();
       // document.selectDocumentSort();
        document.selectDocumentConditionAndOr();
    }

    /**
     * 查询所有文档
     */
    public void selectDocument() {
        //获取集合
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        //返回的是一个文档的迭代器
        FindIterable<Document> iterable = collection.find();
        //迭代器取游标
        MongoCursor<Document> cursor = iterable.iterator();
        //类似关系型数据库的resultSet,返回的是游标
        while (cursor.hasNext()) {
            Document docu = cursor.next();
            System.out.println(docu.get("username") + "\t" + docu.get("userage") + "\t" + docu.get("userdesc") + "\t" + docu.get("userlike")+"\t"+docu.get("userbirt"));

        }
    }

    /**
     * 根据_id查询文档
     */
    public void selectDocumentById() {
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        //在java中所有条件都封装到了一个Filters的工具类中，判断是否相等,查询的条件是一个object对象，因此需要new
        FindIterable<Document> iterable = collection.find(Filters.eq("_id", new ObjectId("5fb4b75da4c3cc86a5e907c5")));
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document docu = cursor.next();
            System.out.println(docu.get("username") + "\t" + docu.get("userage") + "\t" + docu.get("userdesc") + "\t" + docu.get("userlike"));
        }

    }

    /**
     * 根据年龄查询文档，条件是年龄大于19
     */
    public void selectDocumentConditionByGt() {
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        FindIterable iterable = collection.find(Filters.gt("userage", 19));
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document docu = cursor.next();
            System.out.println(docu.get("username") + "\t" + docu.get("userage") + "\t" + docu.get("userdesc") + "\t" + docu.get("userlike"));
        }
    }

    /**
     * 根据年龄查询文档，年龄的值是整数类型
     */
    public void selectDocumentConditionByType() {
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        FindIterable iterable = collection.find(Filters.type("userage", "number"));
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document docu = cursor.next();
            System.out.println(docu.get("username") + "\t" + docu.get("userage") + "\t" + docu.get("userdesc") + "\t" + docu.get("userlike"));
        }
    }

    /**
     *查询用户的名字为zhangsan1和zhangsan2的文档
     */
    public  void selectDocumentConditionByIn(){
        MongoCollection collection =MongoDBAuthPoolUtil.getCollection("develop", "test");
        FindIterable iterable = collection.find(Filters.in("username","zhangsan1","zhangsan2" ));
        MongoCursor<Document> cursor=iterable.iterator();
        while (cursor.hasNext()){
            Document docu  =cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }

    /**
     * 查询用户的名字不是zhangsan2，zhangsan3的文档
     */
    public void  selectDocumentConditionByNin(){
        MongoCollection collection =MongoDBAuthPoolUtil.getCollection("develop", "test");
        FindIterable iterable =collection.find(Filters.nin("username", "zhangsan2","zhangsan3"));
        MongoCursor<Document> cursor =iterable.iterator();
        while (cursor.hasNext()){
           Document docu = cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));

        }
    }
    /**
     * 通过正则表达式查询文档，根据用户的名字是以z开头2结尾的
     */
    public  void selectDocumentByRegex() {
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "test");
        //^表示开头.表示任意字符*表示0次或多次$表示以2结尾
        FindIterable iterable = collection.find(Filters.regex("username", Pattern.compile("^z.*2$")));
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            Document docu = cursor.next();
            System.out.println(docu.get("username") + "\t" + docu.get("userage") + "\t" + docu.get("userdesc") + "\t" + docu.get("userlike"));
        }
    }

    /**
     * 查询用户username是zhangsan1并且年龄为20岁的用户
     * db.test.find({$and:[{username:{$eq:'zhangsan1'}},{userage:{$eq:20}}]})
     */
    public void selectDocumentConditionUseAnd(){
        //先获取集合
        MongoCollection collection =MongoDBAuthPoolUtil.getCollection("develop", "test");
        FindIterable iterable =collection.find(Filters.and(Filters.eq("username","zhangsan1"),Filters.eq("userage",20)));
       MongoCursor<Document> cursor=iterable.iterator();
       while (cursor.hasNext()){
           Document docu=cursor.next();
           System.out.println(docu.get("username") + "\t" + docu.get("userage") + "\t" + docu.get("userdesc") + "\t" + docu.get("userlike"));

       }
    }

    /**
     * 查询用户要求username是list，或者userage是20，或者userdesc是ok
     */
    public void selectDocumentConditionUseOr(){
        MongoCollection collection =MongoDBAuthPoolUtil.getCollection("develop", "test");
        FindIterable iterable =collection.find(Filters.or(Filters.eq("username","list"),Filters.eq("userage",20),Filters.eq("userdesc","Ok")));
        MongoCursor<Document> cursor =iterable.iterator();
        while (cursor.hasNext()){
            Document docu =cursor.next();
            System.out.println(docu.get("username") + "\t" + docu.get("userage") + "\t" + docu.get("userdesc") + "\t" + docu.get("userlike"));

        }
    }

    /**
     * 查询文档中username是z开头的,根据username对结果做降序排序，1为升序，-1为降序 $sort:{username,-1}
     */
    public void selectDocumentSort(){
        MongoCollection collection =MongoDBAuthPoolUtil.getCollection("develop", "test");
        FindIterable iterable = collection.find(Filters.regex("username", Pattern.compile("^z"))).sort(new Document("username",-1));
        MongoCursor<Document> cursor =iterable.iterator();
        while (cursor.hasNext()){
            Document docu =cursor.next();
            System.out.println(docu.get("username") + "\t" + docu.get("userage") + "\t" + docu.get("userdesc") + "\t" + docu.get("userlike"));

        }
    }

    /**
     * 查询文档username 为list并且年龄为20，或者userdesc为ok
     *db.test.find({$or:[{$and:[{username:{$eq:'list'}},{userage:{$eq:20}}]},{userdesc:{$eq:'ok'}}]})
     * */
    public void selectDocumentConditionAndOr(){
        MongoCollection collection =MongoDBAuthPoolUtil.getCollection("develop", "test");
        FindIterable iterable =collection.find(Filters.or(Filters.and(Filters.eq("username","list"),Filters.eq("userage",20)),Filters.eq("userdesc","OK")));
        MongoCursor<Document> cursor =iterable.iterator();
        while (cursor.hasNext()){
            Document docu =cursor.next();
            System.out.println(docu.get("username") + "\t" + docu.get("userage") + "\t" + docu.get("userdesc") + "\t" + docu.get("userlike"));

        }
    }
}
