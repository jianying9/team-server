package com.team.system.service;

import com.team.config.ActionNames;
import com.wolf.framework.dao.EntityDaoBuilder;
import com.wolf.framework.lucene.HdfsLucene;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.MessageContext;
import java.util.List;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.MERGE_LUCENE_INDEX,
response = true,
description = "合并hbase的lucene索引")
public class MergeLuceneIndexServiceImpl implements Service {

    @Override
    public void execute(MessageContext messageContext) {
        List<HdfsLucene> hdfsLuceneList = EntityDaoBuilder.ALL_HDFS_LUCENE;
        if (hdfsLuceneList.isEmpty() == false) {
            for (HdfsLucene hdfsLucene : hdfsLuceneList) {
                hdfsLucene.tryToMerge();
            }
        }
        messageContext.success();
    }
}
