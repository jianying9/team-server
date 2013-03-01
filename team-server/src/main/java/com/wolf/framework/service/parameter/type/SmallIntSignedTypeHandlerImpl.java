package com.wolf.framework.service.parameter.type;

import com.wolf.framework.utils.NumberUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 三字节有符号类型-9999到9999
 *
 * @author zoe
 */
public final class SmallIntSignedTypeHandlerImpl extends AbstractNumberTypeHandler implements TypeHandler {

    private final String ERROR_MESSAGE = " must be -9999~9999";
    private final Pattern SMALL_INT_SIGNED_PATTERN = Pattern.compile("^\\d|[1-9]\\d{1,3}|-[1-9]\\d{0,3}$");

    @Override
    protected String getErrorMessage() {
        return this.ERROR_MESSAGE;
    }

    @Override
    protected boolean patternValidate(final String value) {
        Matcher matcher = this.SMALL_INT_SIGNED_PATTERN.matcher(value);
        return matcher.matches();
    }

    @Override
    public String getRandomValue() {
        int value = NumberUtils.getRandomIntegerValue(32767);
        return Integer.toString(value);
    }
}
