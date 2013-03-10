package com.wolf.framework.dao.inquire;

import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.dao.InquireKeyResult;
import com.wolf.framework.dao.condition.Condition;
import com.wolf.framework.dao.condition.InquireContext;
import com.wolf.framework.dao.parser.ColumnHandler;
import com.wolf.framework.logger.LogFactory;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

/**
 *
 * @author aladdin
 */
public final class InquireKeyByConditionFilterIndexConditionHandlerImpl implements InquireKeyByConditionHandler {

    private final String tableName;
    private final ColumnHandler indexColumnHandler;
    private final InquireKeyByConditionHandler inquireKeyByConditionHandler;
    private final Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.DAO);

    public InquireKeyByConditionFilterIndexConditionHandlerImpl(String tableName, ColumnHandler indexColumnHandler, InquireKeyByConditionHandler inquireKeyByConditionHandler) {
        this.tableName = tableName;
        this.indexColumnHandler = indexColumnHandler;
        this.inquireKeyByConditionHandler = inquireKeyByConditionHandler;
    }

    @Override
    public InquireKeyResult inquireByConditon(InquireContext inquireContext) {
        InquireKeyResult inquireKeyResult;
        //判断是否有索引条件
        List<Condition> conditionList = inquireContext.getConditionList();
        if (conditionList.isEmpty()) {
            InquireKeyResultImpl inquireKeyResultImpl = new InquireKeyResultImpl();
            inquireKeyResultImpl.setResultList(new ArrayList<String>(0));
            inquireKeyResultImpl.setPageSize(inquireContext.getPageSize());
            inquireKeyResult = inquireKeyResultImpl;
        } else {
            String indexColumnName = this.indexColumnHandler.getColumnName();
            List<Condition> indexConditionList = new ArrayList<Condition>(conditionList.size());
            for (Condition condition : conditionList) {
                if (condition.getColumnName().equals(indexColumnName)) {
                    indexConditionList.add(condition);
                } else {
                    this.logger.error("inquire table:{} by condition {} must be index column...", this.tableName, condition.getColumnName());
                }
            }
            //移除索引条件
            if (indexConditionList.isEmpty()) {
                InquireKeyResultImpl inquireKeyResultImpl = new InquireKeyResultImpl();
                inquireKeyResultImpl.setResultList(new ArrayList<String>(0));
                inquireKeyResultImpl.setPageSize(inquireContext.getPageSize());
                inquireKeyResult = inquireKeyResultImpl;
            } else {
                inquireContext.clearCondition();
                inquireContext.addCondition(indexConditionList);
                inquireKeyResult = this.inquireKeyByConditionHandler.inquireByConditon(inquireContext);
            }
        }
        return inquireKeyResult;
    }
}
