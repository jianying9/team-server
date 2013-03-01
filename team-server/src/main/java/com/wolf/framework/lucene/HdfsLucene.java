package com.wolf.framework.lucene;

import java.util.List;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;

/**
 *
 * @author zoe
 */
public interface HdfsLucene {
    
    public DocumentResult searchAfter(String pageIndex, Query query, int pageSize);

    public void addDocument(Document doc);

    public void addDocument(List<Document> docList);

    public void updateDocument(Term term, Document doc);

    public void updateDocument(Map<Term, Document> updateMap);

    public void deleteDocument(Term term);

    public void deleteDocument(List<Term> termList);
    
    public void tryToMerge();
}
