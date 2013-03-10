package com.wolf.framework.service.parameter;

import com.wolf.framework.service.parameter.type.FieldTypeEnum;
import com.wolf.framework.service.parameter.type.TypeHandler;

/**
 * filed处理抽象类
 *
 * @author aladdin
 */
public abstract class AbstractFieldHandler {

    protected final String name;
    protected final TypeHandler typeHandler;
    protected final FieldTypeEnum fieldType;
    protected final String dynamicDictionaryName;
    protected final String defaultValue;

    protected AbstractFieldHandler(final String name, final TypeHandler typeHandler, final FieldTypeEnum fieldType, final String dynamicDictionaryName, final String defaultValue) {
        this.name = name;
        this.typeHandler = typeHandler;
        this.fieldType = fieldType;
        this.dynamicDictionaryName = dynamicDictionaryName;
        if (defaultValue.isEmpty()) {
            this.defaultValue = this.typeHandler.getDefaultValue();
        } else {
            this.defaultValue = defaultValue;
        }
    }

    public final String getDefaultValue() {
        return this.defaultValue;
    }

    public final FieldTypeEnum getFieldType() {
        return fieldType;
    }

    public final String getName() {
        return name;
    }

    public final TypeHandler getTypeHandler() {
        return typeHandler;
    }

    public final String formatValue(final String value) {
        return this.typeHandler.formatValue(value);
    }

    public final String dynamicDictionaryName() {
        return this.dynamicDictionaryName;
    }
}
