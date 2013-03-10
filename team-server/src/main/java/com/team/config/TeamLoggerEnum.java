package com.team.config;

import com.wolf.framework.logger.LoggerType;

/**
 *
 * @author aladdin
 */
public enum TeamLoggerEnum implements LoggerType{
    
    TIMER,
    TEAM;

    @Override
    public String getLoggerName() {
        return this.name();
    }
}
