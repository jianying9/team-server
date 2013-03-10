package com.wolf.framework.service.parameter;

import com.wolf.framework.dictionary.DictionaryManager;
import com.wolf.framework.service.parameter.filter.FilterFactory;
import com.wolf.framework.service.parameter.filter.FilterFactoryImpl;
import com.wolf.framework.service.parameter.type.TypeHandlerFactory;
import com.wolf.framework.service.parameter.type.TypeHandlerFactoryImpl;

/**
 * 全局信息构造类
 *
 * @author aladdin
 */
public class FieldContextBuilderImpl implements FieldContextBuilder {

    private final FilterFactory filterFactory;

    @Override
    public final FilterFactory getFilterFactory() {
        return this.filterFactory;
    }
    private final TypeHandlerFactory typeHandlerFactory;

    @Override
    public final TypeHandlerFactory getTypeHandlerFactory() {
        return this.typeHandlerFactory;
    }
    private final DictionaryManager dictionaryManager;

    @Override
    public final DictionaryManager getDictionaryManager() {
        return this.dictionaryManager;
    }

    /**
     * 构造函数
     *
     * @param properties
     */
    public FieldContextBuilderImpl(final DictionaryManager dictionaryManager) {
        this.filterFactory = new FilterFactoryImpl();
        this.typeHandlerFactory = new TypeHandlerFactoryImpl();
        this.dictionaryManager = dictionaryManager;
    }
}
