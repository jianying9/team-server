package com.wolf.framework.dao.inquire;

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
public final class InquireByKeyFromDataHandlerImpl<T extends Entity> extends AbstractDaoHandler<T> implements InquireByKeyHandler<T> {

    private final HdfsLucene hdfsLucene;

    public InquireByKeyFromDataHandlerImpl(HdfsLucene hdfsLucene, String tableName, Class<T> clazz, List<ColumnHandler> columnHandlerList, KeyHandler keyHandler) {
        super(tableName, clazz, columnHandlerList, keyHandler);
        this.hdfsLucene = hdfsLucene;
    }

    @Override
    public T inquireByKey(String keyValue) {
        T t = null;
        Document doc = this.hdfsLucene.getByKey(keyValue);
        if (doc != null) {
            t = this.readDocument(doc);
        }
        return t;
    }

    @Override
    public List<T> inquireByKeys(List<String> keyValues) {
        List<T> tList;
        List<Document> docList = this.hdfsLucene.getByKeys(keyValues);
        if (keyValues.isEmpty() == false) {
            tList = this.readDocumentList(docList);
        } else {
            tList = new ArrayList<T>(0);
        }
        return tList;
    }

    @Override
    public Map<String, String> inquireMapByKey(String keyValue) {
        Map<String, String> map = null;
        Document doc = this.hdfsLucene.getByKey(keyValue);
        if (doc != null) {
            map = this.documentToMap(doc);
        }
        return map;
    }

    @Override
    public List<Map<String, String>> inquireMapByKeys(List<String> keyValues) {
        List<Map<String, String>> mapList;
        List<Document> docList = this.hdfsLucene.getByKeys(keyValues);
        if (keyValues.isEmpty() == false) {
            mapList = this.documentToMap(docList);
        } else {
            mapList = new ArrayList<Map<String, String>>(0);
        }
        return mapList;
    }
}
