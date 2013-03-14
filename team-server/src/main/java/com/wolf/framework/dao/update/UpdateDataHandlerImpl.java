package com.wolf.framework.dao.update;

import com.wolf.framework.dao.AbstractDaoHandler;
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
public class UpdateDataHandlerImpl extends AbstractDaoHandler implements UpdateHandler {

    private final HdfsLucene hdfsLucene;

    public UpdateDataHandlerImpl(HdfsLucene hdfsLucene, String tableName, Class clazz, List<ColumnHandler> columnHandlerList, KeyHandler keyHandler) {
        super(tableName, clazz, columnHandlerList, keyHandler);
        this.hdfsLucene = hdfsLucene;
    }

    @Override
    public String update(Map<String, String> entityMap) {
        final String keyName = this.keyHandler.getName();
        String keyValue = entityMap.get(keyName);
        if (keyValue == null) {
            this.logger.error("update table {} failure message: can not find key:{} value", this.tableName, keyName);
            this.logger.error("update failure value:{}", entityMap.toString());
            throw new RuntimeException("update failure message: can not find key value...see log");
        } else {
            final Document doc = this.mapToDocument(entityMap);
            this.hdfsLucene.updateDocument(doc);
        }
        return keyValue;
    }

    @Override
    public void batchUpdate(List<Map<String, String>> entityMapList) {
        List<Document> docList = new ArrayList<Document>(entityMapList.size());
        Document doc;
        for (Map<String, String> entityMap : entityMapList) {
            doc = this.mapToDocument(entityMap);
            docList.add(doc);
        }
        this.hdfsLucene.updateDocument(docList);
    }
}
