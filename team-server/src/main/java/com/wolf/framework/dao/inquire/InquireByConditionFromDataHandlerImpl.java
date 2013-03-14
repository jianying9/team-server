package com.wolf.framework.dao.inquire;

import com.wolf.framework.dao.AbstractDaoHandler;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.InquireResult;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.condition.OperateTypeEnum;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.dao.parser.KeyHandler;
import com.wolf.framework.lucene.DocumentResult;
import com.wolf.framework.lucene.HdfsLucene;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public final class InquireByConditionFromDataHandlerImpl<T extends Entity> extends AbstractDaoHandler<T> implements InquireByConditionHandler<T> {

    private final HdfsLucene hdfsLucene;

    public InquireByConditionFromDataHandlerImpl(HdfsLucene hdfsLucene, String tableName, Class<T> clazz, List<ColumnHandler> columnHandlerList, KeyHandler keyHandler) {
        super(tableName, clazz, columnHandlerList, keyHandler);
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
    public InquireResult<T> inquireByConditon(InquireContext inquireContext) {
        InquireResultImpl<T> inquireResultImpl = new InquireResultImpl<T>();
        //获取所有的索引条件
        List<Condition> indexConditionList = inquireContext.getConditionList();
        //构造查询条件
        Condition indexCondition;
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
        List<T> tList;
        T t;
        Map<String, String> entityMap;
        DocumentResult documentResult = this.hdfsLucene.searchAfter(inquireContext.getLastPageIndex(), query, inquireContext.getPageSize());
        if (documentResult.isEmpty()) {
            tList = new ArrayList<T>(0);
        } else {
            List<Document> docList = documentResult.getResultList();
            tList = new ArrayList<T>(docList.size());
            for (Document document : docList) {
                entityMap = this.documentToMap(document);
                t = this.newInstance(entityMap);
                tList.add(t);
            }
        }
        inquireResultImpl.setTotal(documentResult.getTotal());
        inquireResultImpl.setPageSize(documentResult.getPageSize());
        inquireResultImpl.setResultList(tList);
        inquireResultImpl.setNextPageIndex(documentResult.getNextPageIndex());
        return inquireResultImpl;
    }
}
