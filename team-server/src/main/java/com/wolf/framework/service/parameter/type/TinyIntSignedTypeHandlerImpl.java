package com.wolf.framework.service.parameter.type;

import com.wolf.framework.utils.NumberUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一字节带符号类型-99到99
 *
 * @author zoe
 */
public final class TinyIntSignedTypeHandlerImpl extends AbstractNumberTypeHandler implements TypeHandler {

    private final String ERROR_MESSAGE = " must be -99~99";
    private final Pattern TINY_INT_SIGNED_PATTERN = Pattern.compile("^\\d|[1-9]\\d|-[1-9]\\d?$");

    @Override
    protected String getErrorMessage() {
        return this.ERROR_MESSAGE;
    }

    @Override
    protected boolean patternValidate(final String value) {
        Matcher matcher = this.TINY_INT_SIGNED_PATTERN.matcher(value);
        return matcher.matches();
    }

    @Override
    public String getRandomValue() {
        int value = NumberUtils.getRandomIntegerValue(127);
        return Integer.toString(value);
    }
}
