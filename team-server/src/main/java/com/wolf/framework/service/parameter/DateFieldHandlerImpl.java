package com.wolf.framework.service.parameter;

import com.wolf.framework.service.parameter.type.FieldTypeEnum;
import com.wolf.framework.service.parameter.type.TypeHandler;

/**
 * 时间类型处理类
 *
 * @author zoe
 */
public final class DateFieldHandlerImpl extends AbstractFieldHandler implements FieldHandler {

    DateFieldHandlerImpl(final String name, final TypeHandler typeHandler, final FieldTypeEnum fieldType, final String defaultValue) {
        super(name, typeHandler, fieldType, "", defaultValue);
    }

    @Override
    public String getJson(String value) {
        if (value.equals(TypeHandler.DEFAULT_DATE_VALUE)) {
            value = "";
        }
        String result;
        StringBuilder jsonBuilder = new StringBuilder(this.name.length() + value.length() + 5);
        jsonBuilder.append('"').append(this.name).append("\":\"").append(value).append('"');
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
