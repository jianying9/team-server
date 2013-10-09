package com.team.timer;

import com.team.config.ActionNames;
import com.team.config.TeamLoggerEnum;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.timer.AbstractTimer;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.slf4j.Logger;

/**
 *
 * @author aladdin
 */
@Singleton
@Startup
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class TeamTimerSessionBean extends AbstractTimer implements TeamTimerSessionBeanLocal {

    private Logger logger = LogFactory.getLogger(TeamLoggerEnum.TIMER);

//    @Schedule(minute = "0", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "1", dayOfWeek = "*")
    @Override
    public void clearInvolidSessionTimer() {
        this.logger.info("timer:ClearSessionTimer------start");
//        this.executeService(ActionNames.CLEAN_INVOLID_SESSION, null);
        this.logger.info("timer:ClearSessionTimer------finished");
    }

//    @Schedule(minute = "5", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "1", dayOfWeek = "*")
    @Override
    public void mergeLuceneIndex() {
        this.logger.info("timer:mergeLuceneIndex------start");
        this.executeService(ActionNames.MERGE_LUCENE_INDEX, null);
        this.logger.info("timer:mergeLuceneIndex------finished");
    }
}
