package com.wolf.framework.service.parameter.type;

import com.wolf.framework.utils.NumberUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 三字节有符号类型-999999到999999
 *
 * @author zoe
 */
public final class MediumIntSignedTypeHandlerImpl extends AbstractNumberTypeHandler implements TypeHandler {

    private final String ERROR_MESSAGE = " must be -999999~999999";
    private final Pattern MEDIUM_INT_SIGNED_PATTERN = Pattern.compile("^\\d|[1-9]\\d{1,5}|-[1-9]\\d{0,5}$");

    @Override
    protected String getErrorMessage() {
        return this.ERROR_MESSAGE;
    }

    @Override
    protected boolean patternValidate(final String value) {
        Matcher matcher = this.MEDIUM_INT_SIGNED_PATTERN.matcher(value);
        return matcher.matches();
    }

    @Override
    public String getRandomValue() {
        int value = NumberUtils.getRandomIntegerValue(8388607);
        return Integer.toString(value);
    }
}
