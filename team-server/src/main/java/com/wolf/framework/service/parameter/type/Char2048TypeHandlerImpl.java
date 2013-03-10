package com.wolf.framework.service.parameter.type;

import com.wolf.framework.utils.StringUtils;

/**
 * 长度为2048的字符
 *
 * @author aladdin
 */
public final class Char2048TypeHandlerImpl extends AbstractCharTypeHandler implements TypeHandler {

    private final static String ERROR_MESSAGE = " must be char2048";
    private final static int LENGTH = 2048;

    @Override
    protected int getLength() {
        return Char2048TypeHandlerImpl.LENGTH;
    }

    @Override
    protected String getErrorMessage() {
        return Char2048TypeHandlerImpl.ERROR_MESSAGE;
    }

    @Override
    public String getRandomValue() {
        return StringUtils.getRandomStringValue(LENGTH);
    }
}
