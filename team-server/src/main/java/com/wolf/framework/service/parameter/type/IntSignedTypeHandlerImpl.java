package com.wolf.framework.service.parameter.type;

import com.wolf.framework.utils.NumberUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 四字节有符号类型-999999999到999999999
 *
 * @author aladdin
 */
public final class IntSignedTypeHandlerImpl extends AbstractNumberTypeHandler implements TypeHandler {

    private final String ERROR_MESSAGE = " must be -999999999~999999999";
    private final Pattern INT_SIGNED_PATTERN = Pattern.compile("^\\d|[1-9]\\d{1,8}|-[1-9]\\d{0,8}$");

    @Override
    protected String getErrorMessage() {
        return this.ERROR_MESSAGE;
    }

    @Override
    protected boolean patternValidate(final String value) {
        Matcher matcher = this.INT_SIGNED_PATTERN.matcher(value);
        return matcher.matches();
    }

    @Override
    public String getRandomValue() {
        int value = NumberUtils.getRandomIntegerValue();
        return Integer.toString(value);
    }
}
