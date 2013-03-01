package com.wolf.framework.service.parameter.type;

import com.wolf.framework.utils.NumberUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 八字节有符号类型-999999999999999999到999999999999999999
 *
 * @author zoe
 */
public final class BigIntSignedTypeHandlerImpl extends AbstractNumberTypeHandler implements TypeHandler {

    private final String ERROR_MESSAGE = " must be -999999999999999999~999999999999999999";
    private final Pattern BIG_INT_SIGNED_PATTERN = Pattern.compile("^\\d|[1-9]\\d{1,17}|-[1-9]\\d{0,17}$");

    @Override
    protected String getErrorMessage() {
        return this.ERROR_MESSAGE;
    }

    @Override
    protected boolean patternValidate(final String value) {
        Matcher matcher = this.BIG_INT_SIGNED_PATTERN.matcher(value);
        return matcher.matches();
    }

    @Override
    public String getRandomValue() {
        long value = NumberUtils.getRandomLongValue();
        return Long.toString(value);
    }
}
