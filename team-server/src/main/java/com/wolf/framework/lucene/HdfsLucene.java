package com.wolf.framework.lucene;

import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;

/**
 *
 * @author aladdin
 */
public interface HdfsLucene {
    
    public String KEY_NAME = "L_KEY";
    
    public String DOCUMENT_ID = "L_ID";
    
    public DocumentResult searchAfter(String pageIndex, Query query, int pageSize);

    public void addDocument(Document doc);

    public void addDocument(List<Document> docList);

    public void updateDocument(Document doc);

    public void updateDocument(List<Document> docList);

    public void deleteDocument(Term term);

    public void deleteDocument(List<Term> termList);
    
    public void tryToRotate();
    
    public void tryToMerge();
}
