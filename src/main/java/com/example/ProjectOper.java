package com.example;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * $project投影操作
 */
public class ProjectOper {
    public static void main(String[] args) {
        ProjectOper projectOper = new ProjectOper();
        //projectOper.selectDocumentProject();
        //projectOper.selectDocumentProjectConcat();
        //projectOper.selectDocumentProjectAdd();
        projectOper.selectDocumentProjectDate();
    }

    /***
     * 需求:查询 dev 集合，将数组中的内容拆分显示，并只显示 title 键与 tags 键的值。
     * Mongo Shell:
     * db.dev.aggregate([{$unwind:"$tags"},{$project:{_id:0,tags:"$tags",title:"$title"}}])
     */
    public void selectDocumentProject() {
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "dev");
        Document unwind = new Document();
        unwind.put("$unwind", "$tags");

        Document pro = new Document();
        pro.put("_id", 0);
        pro.put("tags", "$tags");
        pro.put("title", "$title");

        Document project = new Document();
        project.put("$project", pro);

        List<Document> list = new ArrayList<>();
        list.add(unwind);
        list.add(project);

        AggregateIterable iterable = collection.aggregate(list);//需要一个list来给定条件
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    /**
     * 需求:查询 dev 集合，将数组中的内容拆分显示。将 title 字段和 tags 字段的值拼接为 一个完整字符串并在 Title_Tags 字段中显示。
     * Mongo Shell:
     * db.dev.aggregate([{$unwind:"$tags"},{$project:{_id:0,Title_Tags:{$concat:["$title","-","$tag s"]}}}])
     */
    public void selectDocumentProjectConcat() {
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "dev");
        Document unwind = new Document();
        unwind.put("$unwind", "$tags");

        Document concat = new Document();
        concat.put("$concat", Arrays.asList(new String[]{"$title", "-", "$tags"}));//在java中需要将数组转换成list

        Document title = new Document();
        title.put("_id", 0);
        title.put("Title_Tags", concat);

        Document project = new Document();
        project.put("$project", title);

        List<Document> list = new ArrayList<>();
        list.add(unwind);
        list.add(project);

        AggregateIterable iterable = collection.aggregate(list);//需要一个list来给定条件
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    /**
     * 需求:查询 dev 集合中数据，显示 title 和 size 字段，为 size 字段数据做加 1 操作，显 示字段命名为 New_Size。排除那些没有 size 键的文档。
     * Mongo Shell:
     * db.dev.aggregate([{$match:{size:{$ne:null}}},{$project:{_id:0,title:1,New_Size:{$add:["$size",1]}}}])
     */
    public void selectDocumentProjectAdd() {
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "dev");

        Document ne = new Document();
        ne.put("$ne", null);

        Document size = new Document();
        size.put("size", ne);

        Document match = new Document();
        match.put("$match", size);

        Document add = new Document();
        add.put("$add", Arrays.asList(new Object[]{"$size", 1}));

        Document newSize = new Document();
        newSize.put("_id", 0);
        newSize.put("title", 1);
        newSize.put("New_Size", add);

        Document project = new Document();
        project.put("$project", newSize);

        List<Document> list = new ArrayList<>();
        list.add(match);
        list.add(project);

        AggregateIterable iterable = collection.aggregate(list);//需要一个list来给定条件
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    /**
     * 需求:查询 devtest 集合查询那些有生日的用户，并按照 YYYY 年 mm 月 dd 日 HH:MM:SS 格式显示日期。
     * 如果直接在mongodb中做格式化处理，那么按照UTC时间做处理的，会少8个小时，
     * 建议在程序中通过java.util.Date来做日期转换
     * Mongo Shell:
     * db.devtest.aggregate([{$match:{userbirth:{$ne:null}}},{$project:{ 自 定 义 日 期 格 式:{$dateToString:{format:"%Y年%m月%d日 %H:%M:%S",date:"$userbirth"}}}}])
     */

    public void selectDocumentProjectDate(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "devtest");
        Document ne = new Document();
        ne.put("$ne", null);

        Document userbirth = new Document();
        userbirth.put("userbirth", ne);

        Document match = new Document();
        match.put("$match", userbirth);

        Document format = new Document();
        format.put("format", "%Y年%m月%d日 %H:%M:%S");
        format.put("date", "$userbirth");

        Document dateToString = new Document();
        dateToString.put("$dateToString",format);

        Document custoDate = new Document();
        custoDate.put("自 定 义 日 期 格 式",dateToString);

        Document project = new Document();
        project.put("$project", custoDate);

        List<Document> list = new ArrayList<>();
        list.add(match);
        list.add(project);

        AggregateIterable iterable = collection.aggregate(list);//需要一个list来给定条件
        MongoCursor<Document> cursor = iterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
}
