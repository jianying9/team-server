package com.wolf.framework.dao.update;

import com.wolf.framework.dao.inquire.InquireByKeyHandler;
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
public class UpdateMultiIndexHandlerImpl implements UpdateHandler {

    private final UpdateHandler updateHandler;
    private final KeyHandler keyHandler;
    private final List<ColumnHandler> indexColumnHandlerList;
    private final HdfsLucene hdfsLucene;
    private final InquireByKeyHandler inquireByKeyHandler;

    public UpdateMultiIndexHandlerImpl(UpdateHandler updateHandler, KeyHandler keyHandler, List<ColumnHandler> indexColumnHandlerList, HdfsLucene hdfsLucene, InquireByKeyHandler inquireByKeyHandler) {
        this.updateHandler = updateHandler;
        this.keyHandler = keyHandler;
        this.indexColumnHandlerList = indexColumnHandlerList;
        this.hdfsLucene = hdfsLucene;
        this.inquireByKeyHandler = inquireByKeyHandler;
    }

    private Document createDocument(Map<String, String> entityMap) {
        Document doc = null;
        String columnValue;
        String columnName;
        int flag = 0;
        for (ColumnHandler columnHandler : this.indexColumnHandlerList) {
            columnName = columnHandler.getColumnName();
            columnValue = entityMap.get(columnName);
            if (columnValue != null) {
                flag++;
            }
        }
        if (flag > 0) {
            Field field;
            String keyName = this.keyHandler.getName();
            String keyValue = entityMap.get(keyName);
            Map<String, String> updateMap = entityMap;
            if (flag < this.indexColumnHandlerList.size()) {
                //只有部分索引列值,从数据库获取最新数据
                updateMap = this.inquireByKeyHandler.inquireMapByKey(keyValue);
            }
            if (updateMap != null) {
                doc = new Document();
                //key
                field = new StringField(HdfsLucene.KEY_NAME, keyValue, Field.Store.YES);
                doc.add(field);
                //索引
                for (ColumnHandler columnHandler : this.indexColumnHandlerList) {
                    columnName = columnHandler.getColumnName();
                    columnValue = entityMap.get(columnName);
                    field = new StringField(columnName, columnValue, Field.Store.NO);
                    doc.add(field);
                }
            }
        }
        return doc;
    }

    @Override
    public String update(Map<String, String> entityMap) {
        //更新数据
        String rowKey = this.updateHandler.update(entityMap);
        //构造变化文档
        Document doc = this.createDocument(entityMap);
        if (doc != null) {
            this.hdfsLucene.updateDocument(doc);
        }
        return rowKey;
    }

    @Override
    public void batchUpdate(List<Map<String, String>> entityMapList) {
        //更新数据
        this.updateHandler.batchUpdate(entityMapList);
        //重建索引
        Document doc;
        List<Document> docList = new ArrayList<Document>(entityMapList.size());
        for (Map<String, String> entityMap : entityMapList) {
            doc = this.createDocument(entityMap);
            if (doc != null) {
                docList.add(doc);
            }
        }
        //保存
        if (!docList.isEmpty()) {
            this.hdfsLucene.updateDocument(docList);
        }
    }
}
