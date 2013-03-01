package com.wolf.framework.service.parameter.filter;

/**
 * 过滤对象工厂类
 *
 * @author zoe
 */
public interface FilterFactory {

    public Filter getFilter(final FilterTypeEnum filterTypeEnum);
}
