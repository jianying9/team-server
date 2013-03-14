package com.wolf.framework.dao;

import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.dao.parser.KeyHandler;
import com.wolf.framework.logger.LogFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.slf4j.Logger;

/**
 * dynamic entity dao
 *
 * @author aladdin
 */
public abstract class AbstractDaoHandler<T extends Entity> {

    protected final Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.DAO);
    //实体class
    protected final Class<T> clazz;
    protected final KeyHandler keyHandler;
    protected final List<ColumnHandler> columnHandlerList;
    protected final String tableName;

    public AbstractDaoHandler(String tableName, Class<T> clazz, List<ColumnHandler> columnHandlerList, KeyHandler keyHandler) {
        this.clazz = clazz;
        this.columnHandlerList = columnHandlerList;
        this.keyHandler = keyHandler;
        this.tableName = tableName;
    }

    /**
     * 解析map数据，实例化clazz
     *
     * @param entityMap
     * @return
     */
    protected T newInstance(final Map<String, String> resultMap) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            logger.error("There was an error instancing  class {}.Cause: {}", clazz.getName(), e.getMessage());
            throw new RuntimeException("There was an error instancing class ".concat(clazz.getName()));
        }
        t.parseMap(resultMap);
        return t;
    }

    protected final Map<String, String> documentToMap(Document doc) {
        Map<String, String> resultMap = new HashMap<String, String>(this.columnHandlerList.size() + 1, 1);
        //读取column
        String columnName;
        String columnValue;
        for (ColumnHandler columnHandler : columnHandlerList) {
            columnName = columnHandler.getColumnName();
            columnValue = doc.get(columnName);
            if (columnValue == null) {
                this.logger.warn("inquire table {} waring message: can not find column:{} value", this.tableName, columnName);
                columnValue = "";
            }
            resultMap.put(columnName, columnValue);
        }
        //放入key
        String keyName = this.keyHandler.getName();
        String keyValue = doc.get(keyName);
        resultMap.put(keyName, keyValue);
        return resultMap;
    }

    protected final List<Map<String, String>> documentToMap(List<Document> docList) {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>(docList.size());
        Map<String, String> map;
        for (Document doc : docList) {
            map = this.documentToMap(doc);
            mapList.add(map);
        }
        return mapList;
    }

    protected final T readDocument(Document doc) {
        Map<String, String> resultMap = this.documentToMap(doc);
        return this.newInstance(resultMap);
    }

    protected final List<T> readDocumentList(List<Document> docList) {
        List<T> tList = new ArrayList<T>(docList.size());
        T t;
        for (Document doc : docList) {
            t = this.readDocument(doc);
            tList.add(t);
        }
        return tList;
    }

    protected final Document mapToDocument(final Map<String, String> entityMap) {
        String keyName = this.keyHandler.getName();
        String keyValue = entityMap.get(keyName);
        if (keyName == null) {
            this.logger.error("insert table {} failure message: can not find keyName:{} value", this.tableName, keyName);
            throw new RuntimeException("insert failure message: can not find keyName value...see log");
        }
        Document doc = new Document();
        String columnName;
        String columnValue;
        Field field;
        for (ColumnHandler columnHandler : this.columnHandlerList) {
            columnName = columnHandler.getColumnName();
            columnValue = entityMap.get(columnName);
            if (columnValue != null) {
                field = new StringField(columnName, columnValue, Field.Store.YES);
                doc.add(field);
            }
        }
        field = new StringField(keyName, keyValue, Field.Store.YES);
        doc.add(field);
        return doc;
    }
}
