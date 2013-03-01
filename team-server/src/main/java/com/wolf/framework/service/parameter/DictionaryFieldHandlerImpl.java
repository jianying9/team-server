package com.wolf.framework.service.parameter;

import com.wolf.framework.dictionary.DictionaryManager;
import com.wolf.framework.service.parameter.type.FieldTypeEnum;
import com.wolf.framework.service.parameter.type.TypeHandler;
import com.wolf.framework.utils.NumberUtils;
import java.util.Set;

/**
 * 数据字典类型处理类
 *
 * @author zoe
 */
public final class DictionaryFieldHandlerImpl extends AbstractFieldHandler implements FieldHandler {

    private final DictionaryManager dictionaryManager;
    private final long dicId;

    protected DictionaryFieldHandlerImpl(final String name, final DictionaryManager dictionaryManager, final TypeHandler typeHandler, final FieldTypeEnum fieldType, final String defaultValue, final long dicId) {
        super(name, typeHandler, fieldType, "", defaultValue);
        this.dictionaryManager = dictionaryManager;
        this.dicId = dicId;
    }

    @Override
    public String getJson(final String value) {
        String result;
        String dicValue = "";
        if (!value.equals("-1")) {
            dicValue = this.getElementName(value);
        }
        StringBuilder jsonBuilder = new StringBuilder(this.name.length() * 2 + value.length() + dicValue.length() + 12);
        jsonBuilder.append('"').append(this.name).append("\":").append(value).append(",\"").append(this.name).append("Dic\":\"").append(dicValue).append('"');
        result = jsonBuilder.toString();
        return result;
    }

    private String getElementName(final String value) {
        String dicValue = "";
        if (!value.isEmpty()) {
            dicValue = this.dictionaryManager.getElementName(this.dicId, dicValue);
            if (dicValue == null) {
                dicValue = "";
            }
        }
        return dicValue;
    }

    @Override
    public String validate(String value) {
        String result = "";
        if (!value.equals("-1") && !this.dictionaryManager.containsKey(this.dicId, value)) {
            StringBuilder errorMessage = new StringBuilder(36);
            errorMessage.append("'s dictionary group can not find key:").append(value);
            result = errorMessage.toString();
        }
        return result;
    }

    @Override
    public String getRandomValue() {
        String randomValue = "-1";
        Set<String> keySet = this.dictionaryManager.getKeySet(this.dicId);
        int valueIndex = NumberUtils.getRandomIntegerValue(keySet.size());
        int index = 0;
        for (String key : keySet) {
            if (index == valueIndex) {
                randomValue = key;
                break;
            } else {
                index++;
            }
        }
        return randomValue;
    }
}
