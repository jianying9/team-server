package com.wolf.framework.dao.insert;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.dao.parser.KeyHandler;
import com.wolf.framework.lucene.HdfsLucene;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;

/**
 *
 * @author aladdin
 */
public class InsertIndexHandlerImpl<T extends Entity> implements InsertHandler<T> {

    private final KeyHandler keyHandler;
    private final ColumnHandler indexColumnHandler;
    private final InsertHandler<T> insertHandler;
    private final HdfsLucene hdfsLucene;

    public InsertIndexHandlerImpl(KeyHandler keyHandler, ColumnHandler indexColumnHandler, InsertHandler<T> insertHandler, HdfsLucene hdfsLucene) {
        this.keyHandler = keyHandler;
        this.indexColumnHandler = indexColumnHandler;
        this.insertHandler = insertHandler;
        this.hdfsLucene = hdfsLucene;
    }

    @Override
    public T insert(Map<String, String> entityMap) {
        //插入
        T t = this.insertHandler.insert(entityMap);
        //获取rowkey,开始建立索引
        String rowKey = t.getKeyValue();
        //保存key
        Document doc = new Document();
        Field field = new StringField(HdfsLucene.KEY_NAME, rowKey, Field.Store.YES);
        doc.add(field);
        //建立索引
        String columnName = indexColumnHandler.getColumnName();
        String columnValue = entityMap.get(columnName);
        field = new StringField(columnName, columnValue, Field.Store.NO);
        doc.add(field);
        //保存
        this.hdfsLucene.addDocument(doc);
        return t;
    }

    @Override
    public void batchInsert(List<Map<String, String>> entityMapList) {
        //插入
        this.insertHandler.batchInsert(entityMapList);
        //开始建立索引
        final String keyName = this.keyHandler.getName();
        String rowKey;
        String columnValue;
        Document doc;
        Field field;
        String columnName = indexColumnHandler.getColumnName();
        List<Document> docList = new ArrayList<Document>(entityMapList.size());
        for (Map<String, String> entityMap : entityMapList) {
            doc = new Document();
            //保存key
            rowKey = entityMap.get(keyName);
            field = new StringField(HdfsLucene.KEY_NAME, rowKey, Field.Store.YES);
            doc.add(field);
            //建立索引
            columnValue = entityMap.get(columnName);
            field = new StringField(columnName, columnValue, Field.Store.NO);
            doc.add(field);
            docList.add(doc);
        }
        //保存
        this.hdfsLucene.addDocument(docList);
    }
}
