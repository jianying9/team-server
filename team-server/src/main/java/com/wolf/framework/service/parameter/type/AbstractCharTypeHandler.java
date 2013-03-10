package com.wolf.framework.service.parameter.type;

import com.wolf.framework.service.parameter.FieldTypeClassEnum;

/**
 * 字符类型验证抽象类
 *
 * @author aladdin
 */
public abstract class AbstractCharTypeHandler extends AbstractTypeHandler {

    protected abstract int getLength();

    @Override
    public final FieldTypeClassEnum getFieldTypeClassEnum() {
        return FieldTypeClassEnum.STRING;
    }

    @Override
    public final String getDefaultValue() {
        return TypeHandler.DEFAULT_CHAR_VALUE;
    }

    public final String validate(final String value) {
        return value.length() <= this.getLength() ? "" : this.getErrorMessage();
    }

    public final String formatValue(final String value) {
        String result = value;
        if (value == null) {
            result = "";
        } else {
            if (!this.validate(value).isEmpty()) {
                result = value.substring(0, this.getLength());
            }
        }
        return result;
    }
}
