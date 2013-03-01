package com.wolf.framework.local;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 本地服务信息配置
 *
 * @author zoe
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LocalServiceConfig {

    Class<?> interfaceInfo();

    /**
     * 描述
     *
     * @return
     */
    public String description();
}