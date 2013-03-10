package com.team;

import com.wolf.framework.lucene.HdfsLucene;
import java.io.IOException;
import java.util.Set;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.FixedBitSet;

/**
 *
 * @author aladdin
 */
public class TestFilter extends Filter {

    private final Set<String> deleteIdSet;

    public TestFilter(Set<String> deleteIdSet) {
        this.deleteIdSet = deleteIdSet;
    }

    @Override
    public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs) throws IOException {
        AtomicReader reader = context.reader();
        int maxDoc = reader.maxDoc();
        Object object = reader.getCoreCacheKey();
        System.out.println(object);
        FixedBitSet result = new FixedBitSet(maxDoc);
        Document doc;
        String id;
        for (int index = 0; index < maxDoc; index++) {
            doc = reader.document(index);
            id = doc.get("key");
            if (id != null) {
                if (this.deleteIdSet.contains(id) == false) {
                    result.set(index);
                }
            }
        }
        return result;
    }
}
