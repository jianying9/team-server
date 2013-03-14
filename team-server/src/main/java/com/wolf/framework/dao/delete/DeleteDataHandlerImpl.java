package com.wolf.framework.dao.delete;

import com.wolf.framework.lucene.HdfsLucene;
import java.util.List;

/**
 *
 * @author aladdin
 */
public class DeleteDataHandlerImpl implements DeleteHandler {
    
    private final HdfsLucene hdfsLucene;
    
    public DeleteDataHandlerImpl(HdfsLucene hdfsLucene) {
        this.hdfsLucene = hdfsLucene;
    }

    @Override
    public void delete(String keyValue) {
        this.hdfsLucene.deleteDocument(keyValue);
    }

    @Override
    public void batchDelete(List<String> keyValues) {
        if(keyValues.isEmpty() == false) {
            this.hdfsLucene.deleteDocument(keyValues);
        }
    }
}
