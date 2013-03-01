package com.wolf.framework.service.parameter;

import com.wolf.framework.service.parameter.type.FieldTypeEnum;

/**
 *
 * @author zoe
 */
public interface FieldHandler {

    public String getName();

    public String getJson(String value);

    public String validate(String value);

    public String formatValue(String value);

    public String getDefaultValue();

    public FieldTypeEnum getFieldType();

    public String dynamicDictionaryName();

    public String getRandomValue();
}
