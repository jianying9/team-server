package com.wolf.framework.dao.insert;

import com.wolf.framework.dao.AbstractDaoHandler;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.dao.parser.KeyHandler;
import com.wolf.framework.lucene.HdfsLucene;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.lucene.document.Document;

/**
 *
 * @author aladdin
 */
public class InsertDataHandlerImpl<T extends Entity> extends AbstractDaoHandler<T> implements InsertHandler<T> {

    private final HdfsLucene hdfsLucene;

    public InsertDataHandlerImpl(HdfsLucene hdfsLucene, String tableName, Class clazz, List<ColumnHandler> columnHandlerList, KeyHandler keyHandler) {
        super(tableName, clazz, columnHandlerList, keyHandler);
        this.hdfsLucene = hdfsLucene;
    }

    @Override
    public T insert(Map<String, String> entityMap) {
        final String keyName = this.keyHandler.getName();
        String keyValue = entityMap.get(keyName);
        if (keyValue == null) {
            keyValue = this.keyHandler.nextValue();
            entityMap.put(keyName, keyValue);
        }
        final Document doc = this.mapToDocument(entityMap);
        this.hdfsLucene.addDocument(doc);
        T t = this.newInstance(entityMap);
        return t;
    }

    @Override
    public void batchInsert(List<Map<String, String>> entityMapList) {
        final String keyName = this.keyHandler.getName();
        List<Document> docList = new ArrayList<Document>(entityMapList.size());
        Document doc;
        String keyValue;
        for (Map<String, String> entityMap : entityMapList) {
            keyValue = entityMap.get(keyName);
            if (keyValue == null) {
                keyValue = this.keyHandler.nextValue();
                entityMap.put(keyName, keyValue);
            }
            doc = this.mapToDocument(entityMap);
            docList.add(doc);
        }
        this.hdfsLucene.addDocument(docList);
    }
}
