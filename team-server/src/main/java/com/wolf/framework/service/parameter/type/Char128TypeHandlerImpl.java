package com.wolf.framework.service.parameter.type;

import com.wolf.framework.utils.StringUtils;

/**
 * 长度为128的字符
 *
 * @author aladdin
 */
public final class Char128TypeHandlerImpl extends AbstractCharTypeHandler implements TypeHandler {

    private final static String ERROR_MESSAGE = " must be char128";
    private final static int LENGTH = 128;

    @Override
    protected int getLength() {
        return Char128TypeHandlerImpl.LENGTH;
    }

    @Override
    protected String getErrorMessage() {
        return Char128TypeHandlerImpl.ERROR_MESSAGE;
    }

    @Override
    public String getRandomValue() {
        return StringUtils.getRandomStringValue(LENGTH);
    }
}
