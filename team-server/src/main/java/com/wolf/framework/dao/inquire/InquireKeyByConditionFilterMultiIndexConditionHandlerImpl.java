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
 * @author zoe
 */
public final class InquireKeyByConditionFilterMultiIndexConditionHandlerImpl implements InquireKeyByConditionHandler {

    private final String tableName;
    private final List<ColumnHandler> indexColumnHandlerList;
    private final InquireKeyByConditionHandler inquireKeyByConditionHandler;
    private final Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.DAO);

    public InquireKeyByConditionFilterMultiIndexConditionHandlerImpl(String tableName, List<ColumnHandler> indexColumnHandlerList, InquireKeyByConditionHandler inquireKeyByConditionHandler) {
        this.tableName = tableName;
        this.indexColumnHandlerList = indexColumnHandlerList;
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
            List<Condition> indexConditionList = new ArrayList<Condition>(conditionList.size());
            boolean isIndexCondition;
            for (Condition condition : conditionList) {
                isIndexCondition = false;
                for (ColumnHandler columnHandler : this.indexColumnHandlerList) {
                    if (condition.getColumnName().equals(columnHandler.getColumnName())) {
                        isIndexCondition = true;
                        break;
                    }
                }
                if (isIndexCondition) {
                    indexConditionList.add(condition);
                } else {
                    this.logger.error("inquire table:{} by condition {} must be index column...", this.tableName, condition.getColumnName());
                }
            }
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
