package com.wolf.framework.config;

import com.wolf.framework.logger.LoggerType;

/**
 *
 * @author zoe
 */
public enum FrameworkLoggerEnum implements LoggerType{
    FRAMEWORK,
    DAO,
    LUCENE;

    @Override
    public String getLoggerName() {
        return this.name();
    }
}
