package com.wolf.framework.service.parameter;

import com.wolf.framework.service.parameter.type.FieldTypeEnum;
import com.wolf.framework.service.parameter.type.TypeHandler;

/**
 * 数字类型处理类
 *
 * @author aladdin
 */
public final class NumberFieldHandlerImpl extends AbstractFieldHandler implements FieldHandler {

    public NumberFieldHandlerImpl(final String name, final TypeHandler typeHandler, final FieldTypeEnum fieldType, final String defaultValue) {
        super(name, typeHandler, fieldType, "", defaultValue);
    }

    @Override
    public String getJson(final String value) {
        String result;
        StringBuilder jsonBuilder = new StringBuilder(this.name.length() + value.length() + 3);
        jsonBuilder.append('"').append(this.name).append("\":").append(value);
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
