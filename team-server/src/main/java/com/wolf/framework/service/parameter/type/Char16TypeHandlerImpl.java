package com.wolf.framework.service.parameter.type;

import com.wolf.framework.utils.StringUtils;

/**
 * 长度为16的字符
 *
 * @author aladdin
 */
public final class Char16TypeHandlerImpl extends AbstractCharTypeHandler implements TypeHandler {

    private final static String ERROR_MESSAGE = " must be char16";
    private final static int LENGTH = 16;

    @Override
    protected int getLength() {
        return Char16TypeHandlerImpl.LENGTH;
    }

    @Override
    protected String getErrorMessage() {
        return Char16TypeHandlerImpl.ERROR_MESSAGE;
    }

    @Override
    public String getRandomValue() {
        return StringUtils.getRandomStringValue(LENGTH);
    }
}
