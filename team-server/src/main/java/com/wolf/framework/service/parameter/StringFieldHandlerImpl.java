package com.wolf.framework.service.parameter;

import com.wolf.framework.service.parameter.filter.Filter;
import com.wolf.framework.service.parameter.type.FieldTypeEnum;
import com.wolf.framework.service.parameter.type.TypeHandler;

/**
 * 字符类型处理类
 *
 * @author aladdin
 */
public final class StringFieldHandlerImpl extends AbstractFieldHandler implements FieldHandler {

    private final Filter[] filters;

    StringFieldHandlerImpl(final String name, final Filter[] filters, final TypeHandler typeHandler, final FieldTypeEnum fieldType, final String defaultValue) {
        super(name, typeHandler, fieldType, "", defaultValue);
        this.filters = filters;
    }

    @Override
    public String getJson(final String value) {
        String result;
        String filterValue = value;
        for (Filter filter : filters) {
            filterValue = filter.doFilter(filterValue);
        }
        StringBuilder jsonBuilder = new StringBuilder(this.name.length() + filterValue.length() + 5);
        jsonBuilder.append('"').append(this.name).append("\":\"").append(filterValue).append('"');
        result = jsonBuilder.toString();
        return result;
    }

    @Override
    public String validate(final String value) {
        return this.typeHandler.validate(value);
    }

    @Override
    public String getRandomValue() {
        return this.typeHandler.getRandomValue();
    }
}
