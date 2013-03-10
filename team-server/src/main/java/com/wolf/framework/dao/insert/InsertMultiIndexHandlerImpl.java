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
public class InsertMultiIndexHandlerImpl<T extends Entity> implements InsertHandler<T> {

    private final KeyHandler keyHandler;
    private final List<ColumnHandler> indexColumnHandlerList;
    private final InsertHandler<T> insertHandler;
    private final HdfsLucene hdfsLucene;

    public InsertMultiIndexHandlerImpl(KeyHandler keyHandler, List<ColumnHandler> indexColumnHandlerList, InsertHandler<T> insertHandler, HdfsLucene hdfsLucene) {
        this.keyHandler = keyHandler;
        this.indexColumnHandlerList = indexColumnHandlerList;
        this.insertHandler = insertHandler;
        this.hdfsLucene = hdfsLucene;
    }

    @Override
    public T insert(Map<String, String> entityMap) {
        //插入
        T t = this.insertHandler.insert(entityMap);
        //获取rowkey,开始建立索引
        String rowKey = t.getKeyValue();
        String columnValue;
        String columnName;
        //保存key
        Document doc = new Document();
        Field field = new StringField(HdfsLucene.KEY_NAME, rowKey, Field.Store.YES);
        doc.add(field);
        //建立索引
        for (ColumnHandler columnHandler : indexColumnHandlerList) {
            columnName = columnHandler.getColumnName();
            columnValue = entityMap.get(columnName);
            field = new StringField(columnName, columnValue, Field.Store.NO);
            doc.add(field);
        }
        //保存
        this.hdfsLucene.addDocument(doc);
        return t;
    }

    @Override
    public void batchInsert(List<Map<String, String>> entityMapList) {
        this.insertHandler.batchInsert(entityMapList);
        final String keyName = this.keyHandler.getName();
        String rowKey;
        String columnValue;
        String columnName;
        Document doc;
        Field field;
        List<Document> docList = new ArrayList<Document>(entityMapList.size());
        for (Map<String, String> entityMap : entityMapList) {
            doc = new Document();
            //保存key
            rowKey = entityMap.get(keyName);
            field = new StringField(HdfsLucene.KEY_NAME, rowKey, Field.Store.YES);
            doc.add(field);
            //建立索引
            for (ColumnHandler columnHandler : indexColumnHandlerList) {
                columnName = columnHandler.getColumnName();
                columnValue = entityMap.get(columnName);
                field = new StringField(columnName, columnValue, Field.Store.NO);
                doc.add(field);
            }
            docList.add(doc);
        }
        //保存
        this.hdfsLucene.addDocument(docList);
    }
}
