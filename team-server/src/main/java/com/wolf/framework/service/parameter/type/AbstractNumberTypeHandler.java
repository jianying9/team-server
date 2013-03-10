package com.wolf.framework.service.parameter.type;

import com.wolf.framework.service.parameter.FieldTypeClassEnum;

/**
 * 数字类型验证抽象类
 *
 * @author aladdin
 */
public abstract class AbstractNumberTypeHandler extends AbstractTypeHandler {

    @Override
    public final String getDefaultValue() {
        return TypeHandler.DEFAULT_NUMBER_VALUE;
    }

    @Override
    public final FieldTypeClassEnum getFieldTypeClassEnum() {
        return FieldTypeClassEnum.NUMBER;
    }

    protected abstract boolean patternValidate(String value);

    public final String validate(final String value) {
        return this.patternValidate(value) ? "" : this.getErrorMessage();
    }

    public final String formatValue(final String value) {
        String result = value;
        if (value == null || !this.validate(value).isEmpty()) {
            result = TypeHandler.DEFAULT_NUMBER_VALUE;
        }
        return result;
    }
}
