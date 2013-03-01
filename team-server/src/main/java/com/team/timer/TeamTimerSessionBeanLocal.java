package com.team.timer;

import javax.ejb.Local;

/**
 *
 * @author zoe
 */
@Local
public interface TeamTimerSessionBeanLocal {

    /**
     * 清除无效的sessionId
     */
    public void clearInvolidSessionTimer();
    
    /**
     * 合并表的索引
     */
    public void mergeLuceneIndex();
    
}
