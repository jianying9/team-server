package com.team.system.service;

import com.team.config.ActionNames;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.MERGE_LUCENE_INDEX,
        validateSession = false,
        response = true,
        description = "合并hbase的lucene索引")
public class MergeLuceneIndexServiceImpl implements Service {

    @Override
    public void execute(MessageContext messageContext) {
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("jdbc/team");
            dataSource.getConnection();
            //        List<HdfsLucene> hdfsLuceneList = messageContext.getApplicationContext().getHdfsLuceneList();
            //        if (hdfsLuceneList.isEmpty() == false) {
            //            for (HdfsLucene hdfsLucene : hdfsLuceneList) {
            //                hdfsLucene.tryToMerge();
            //        }
            //        }
        } catch (SQLException ex) {
            Logger.getLogger(MergeLuceneIndexServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(MergeLuceneIndexServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        messageContext.success();
    }
}
