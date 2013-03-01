package com.wolf.framework.service.parameter;

import com.wolf.framework.dictionary.DictionaryManager;
import com.wolf.framework.service.parameter.filter.FilterFactory;
import com.wolf.framework.service.parameter.type.TypeHandlerFactory;

/**
 *
 * @author zoe
 */
public interface FieldContextBuilder {

    public FilterFactory getFilterFactory();

    public TypeHandlerFactory getTypeHandlerFactory();

    public DictionaryManager getDictionaryManager();
}
