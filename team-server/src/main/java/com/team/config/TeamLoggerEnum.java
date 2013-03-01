package com.team.config;

import com.wolf.framework.logger.LoggerType;

/**
 *
 * @author zoe
 */
public enum TeamLoggerEnum implements LoggerType{
    
    TIMER,
    TEAM;

    @Override
    public String getLoggerName() {
        return this.name();
    }
}
