package com.wolf.framework.service.parameter.type;

import com.wolf.framework.utils.StringUtils;

/**
 * 长度为24的字符
 *
 * @author aladdin
 */
public final class Char24TypeHandlerImpl extends AbstractCharTypeHandler implements TypeHandler {

    private final static String ERROR_MESSAGE = " must be char24";
    private final static int LENGTH = 24;

    @Override
    protected int getLength() {
        return Char24TypeHandlerImpl.LENGTH;
    }

    @Override
    protected String getErrorMessage() {
        return Char24TypeHandlerImpl.ERROR_MESSAGE;
    }

    @Override
    public String getRandomValue() {
        return StringUtils.getRandomStringValue(LENGTH);
    }
}
