package com.wolf.framework.service.parameter.type;

/**
 * 验证类对象工厂
 *
 * @author zoe
 */
public interface TypeHandlerFactory {

    public TypeHandler getTypeHandler(final FieldTypeEnum filedTypeEnum);
}
