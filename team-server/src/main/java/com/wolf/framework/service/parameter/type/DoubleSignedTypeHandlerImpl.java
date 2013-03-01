package com.wolf.framework.service.parameter.type;

import com.wolf.framework.utils.NumberUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DOUBLE有符号类型 -9999999999999999.999999到9999999999999999.999999
 *
 * @author zoe
 */
public final class DoubleSignedTypeHandlerImpl extends AbstractNumberTypeHandler implements TypeHandler {

    private final String ERROR_MESSAGE = " must be double signed";
    private final Pattern DOUBLE_SIGNED_PATTERN = Pattern.compile("^\\d(\\.\\d{1,6})?|[1-9]\\d{1,15}(\\.\\d{1,6})?|-[1-9]\\d{0,15}(\\.\\d{1,24})?$");

    @Override
    protected String getErrorMessage() {
        return this.ERROR_MESSAGE;
    }

    @Override
    protected boolean patternValidate(final String value) {
        Matcher matcher = this.DOUBLE_SIGNED_PATTERN.matcher(value);
        return matcher.matches();
    }

    @Override
    public String getRandomValue() {
        double value = NumberUtils.getRandomDoubleValue();
        String result = NumberUtils.numberDf.format(value);
        return result;
    }
}
