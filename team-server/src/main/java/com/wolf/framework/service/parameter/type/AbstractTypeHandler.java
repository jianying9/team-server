package com.wolf.framework.service.parameter.type;

import com.wolf.framework.service.parameter.FieldTypeClassEnum;

/**
 * 字符类型验证抽象类
 *
 * @author aladdin
 */
public abstract class AbstractTypeHandler {

    protected abstract String getErrorMessage();

    public abstract String getDefaultValue();

    public abstract FieldTypeClassEnum getFieldTypeClassEnum();
}
