package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.lucene.DocumentResult;
import com.wolf.framework.lucene.HdfsLucene;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

/**
 *
 * @author aladdin
 */
public final class InquireKeyByConditionFromIndexHandlerImpl implements InquireKeyByConditionHandler {

    private final HdfsLucene hdfsLucene;

    public InquireKeyByConditionFromIndexHandlerImpl(HdfsLucene hdfsLucene) {
        this.hdfsLucene = hdfsLucene;
    }

    private Query conditionToQuery(Condition condition) {
        Query query;
        OperateTypeEnum operateTypeEnum = condition.getOperateTypeEnum();
        Term term = new Term(condition.getColumnName(), condition.getColumnValue());
        switch (operateTypeEnum) {
            case LIKE:
                query = new PrefixQuery(term);
                break;
            default:
                query = new TermQuery(term);
        }
        return query;
    }

    @Override
    public InquireKeyResultImpl inquireByConditon(InquireContext inquireContext) {
        InquireKeyResultImpl inquireKeyResult = new InquireKeyResultImpl();
        //判断是否有索引条件
        Condition indexCondition;
        //获取所有的索引条件
        List<Condition> indexConditionList = inquireContext.getConditionList();
        //构造查询条件
        Query query;
        if (indexConditionList.size() == 1) {
            //只有一个条件
            indexCondition = indexConditionList.get(0);
            query = this.conditionToQuery(indexCondition);
        } else {
            //多个条件
            BooleanQuery booleanQuery = new BooleanQuery();
            for (Condition condition : indexConditionList) {
                query = this.conditionToQuery(condition);
                booleanQuery.add(query, BooleanClause.Occur.MUST);
            }
            query = booleanQuery;
        }
        //查询
        List<String> keyList;
        DocumentResult documentResult = this.hdfsLucene.searchAfter(inquireContext.getLastPageIndex(), query, inquireContext.getPageSize());
        if (documentResult.isEmpty()) {
            keyList = new ArrayList<String>(0);
        } else {
            List<Document> docList = documentResult.getResultList();
            String keyValue;
            keyList = new ArrayList<String>(docList.size());
            for (Document document : docList) {
                keyValue = document.get(HdfsLucene.KEY_NAME);
                keyList.add(keyValue);
            }
        }
        inquireKeyResult.setTotal(documentResult.getTotal());
        inquireKeyResult.setPageSize(documentResult.getPageSize());
        inquireKeyResult.setResultList(keyList);
        inquireKeyResult.setNextPageIndex(documentResult.getNextPageIndex());
        return inquireKeyResult;
    }
}
