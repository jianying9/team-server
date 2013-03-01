package com.wolf.framework.dao.update;

import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.dao.parser.KeyHandler;
import com.wolf.framework.lucene.HdfsLucene;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.Term;

/**
 *
 * @author zoe
 */
public class UpdateIndexHandlerImpl implements UpdateHandler {

    private final UpdateHandler updateHandler;
    private final KeyHandler keyHandler;
    private final ColumnHandler indexColumnHandler;
    private final HdfsLucene hdfsLucene;

    public UpdateIndexHandlerImpl(UpdateHandler updateHandler, KeyHandler keyHandler, ColumnHandler indexColumnHandler, HdfsLucene hdfsLucene) {
        this.updateHandler = updateHandler;
        this.keyHandler = keyHandler;
        this.indexColumnHandler = indexColumnHandler;
        this.hdfsLucene = hdfsLucene;
    }

    @Override
    public String update(Map<String, String> entityMap) {
        //更新数据
        String rowKey = this.updateHandler.update(entityMap);
        String columnName = this.indexColumnHandler.getColumnName();
        String columnValue = entityMap.get(columnName);
        if (columnValue != null) {
            //重建索引
            String keyName = this.keyHandler.getName();
            //构造key
            Term term = new Term(keyName, rowKey);
            //构造变化文档
            Document doc = new Document();
            Field field = new StringField(columnName, columnValue, Field.Store.NO);
            doc.add(field);
            //保存
            this.hdfsLucene.updateDocument(term, doc);
        }
        return rowKey;
    }

    @Override
    public void batchUpdate(List<Map<String, String>> entityMapList) {
        //更新数据
        this.updateHandler.batchUpdate(entityMapList);
        //重建索引
        String keyName = this.keyHandler.getName();
        String rowKey;
        String columnValue;
        String columnName = this.indexColumnHandler.getColumnName();
        Document doc;
        Term term;
        Field field;
        Map<Term, Document> updateIndexMap = new HashMap<Term, Document>(entityMapList.size(), 1);
        for (Map<String, String> entityMap : entityMapList) {
            columnValue = entityMap.get(columnName);
            //如果存在索引列更新，则将该文档放入待更新集合
            if (columnValue != null) {
                rowKey = entityMap.get(keyName);
                doc = new Document();
                term = new Term(keyName, rowKey);
                field = new StringField(columnName, columnValue, Field.Store.NO);
                doc.add(field);
                updateIndexMap.put(term, doc);
            }
        }
        //保存
        if (!updateIndexMap.isEmpty()) {
            this.hdfsLucene.updateDocument(updateIndexMap);
        }
    }
}
