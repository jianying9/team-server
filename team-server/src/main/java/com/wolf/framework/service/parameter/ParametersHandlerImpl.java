package com.wolf.framework.service.parameter;

import java.util.Collections;
import java.util.Map;

/**
 * 实体处理类
 *
 * @author aladdin
 */
public final class ParametersHandlerImpl implements ParametersHandler {

    private final Map<String, FieldHandler> fieldHandlerMap;

    ParametersHandlerImpl(final Map<String, FieldHandler> fieldHandlerMap) {
        this.fieldHandlerMap = fieldHandlerMap;
    }

    @Override
    public FieldHandler getFieldHandler(final String fieldName) {
        return this.fieldHandlerMap.get(fieldName);
    }

    @Override
    public boolean containsField(String fieldName) {
        return this.fieldHandlerMap.containsKey(fieldName);
    }

    @Override
    public Map<String, FieldHandler> getFieldHandlerMap() {
        return Collections.unmodifiableMap(this.fieldHandlerMap);
    }
}
