package com.wolf.framework.service.parameter;

import com.wolf.framework.dictionary.DictionaryManager;
import com.wolf.framework.service.parameter.type.FieldTypeEnum;
import com.wolf.framework.service.parameter.type.TypeHandler;
import com.wolf.framework.utils.NumberUtils;
import java.util.Set;

/**
 *
 * @author aladdin
 */
public final class DictionaryArrayFieldHandlerImpl extends AbstractFieldHandler implements FieldHandler {

    private final DictionaryManager dictionaryManager;
    private final long dicId;

    protected DictionaryArrayFieldHandlerImpl(final String name, final DictionaryManager dictionaryManager, final TypeHandler typeHandler, final FieldTypeEnum fieldType, final String defaultValue, final long dicId) {
        super(name, typeHandler, fieldType, "", defaultValue);
        this.dictionaryManager = dictionaryManager;
        this.dicId = dicId;
    }

    @Override
    public String getJson(final String value) {
        String result;
        String eleValue = this.getElementNames(value);
        StringBuilder jsonBuilder = new StringBuilder(this.name.length() * 2 + value.length() + eleValue.length() + 14);
        jsonBuilder.append('"').append(this.name).append("\":\"").append(value).append("\",\"").append(this.name).append("Dic\":\"").append(eleValue).append('"');
        result = jsonBuilder.toString();
        return result;
    }

    private String getElementNames(final String values) {
        return this.dictionaryManager.getElementNames(this.dicId, values);
    }

    @Override
    public String validate(String value) {
        String result = "";
        String[] keys = value.split(",");
        for (String key : keys) {
            if (!key.equals("-1") && !this.dictionaryManager.containsKey(this.dicId, key)) {
                StringBuilder errorMessage = new StringBuilder(64);
                errorMessage.append("'s dictionary group can not find key:").append(key);
                result = errorMessage.toString();
                break;
            }
        }
        return result;
    }

    @Override
    public String getRandomValue() {
        int valueIndex;
        int length = NumberUtils.getRandomIntegerValue(3) + 1;
        StringBuilder valueBuilder = new StringBuilder(length * 5);
        Set<String> keySet = this.dictionaryManager.getKeySet(this.dicId);
        int index;
        for (int lengthIndex = 0; lengthIndex < length; lengthIndex++) {
            valueIndex = NumberUtils.getRandomIntegerValue(keySet.size());
            index = 0;
            for (String key : keySet) {
                if (index == valueIndex) {
                    valueBuilder.append(key).append(',');
                    break;
                } else {
                    index++;
                }
            }
        }
        valueBuilder.setLength(valueBuilder.length() - 1);
        String randomValue = valueBuilder.toString();
        return randomValue;
    }
}
