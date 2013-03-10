package com.wolf.framework.service.parameter.type;

import com.wolf.framework.utils.TimeUtils;
import java.util.regex.Matcher;

/**
 * 时间类型YYYY-m-d,YYYY-MM-DD
 *
 * @author aladdin
 */
public final class DateTypeHandlerImpl extends AbstractDateTypeHandler implements TypeHandler {

    private final static String ERROR_MESSAGE = " must be date";

    @Override
    protected String getErrorMessage() {
        return DateTypeHandlerImpl.ERROR_MESSAGE;
    }

    @Override
    protected boolean patternValidate(final String value) {
        boolean result = false;
        if (value.length() > 7 && value.length() < 11) {
            Matcher matcher = AbstractDateTypeHandler.DATA_PATTERN.matcher(value);
            result = matcher.matches();
        }
        return result;
    }

    @Override
    public String getRandomValue() {
        return TimeUtils.getDateFotmatYYMMDD();
    }
}
