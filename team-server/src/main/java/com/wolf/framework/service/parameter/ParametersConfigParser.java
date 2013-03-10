package com.wolf.framework.service.parameter;

import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.logger.LogFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

/**
 * 负责解析annotation ExtendedEntityConfig
 *
 * @author aladdin
 */
public class ParametersConfigParser {

    private final Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.FRAMEWORK);
    private final ParametersContext parametersContextBuilder;

    public ParametersConfigParser(ParametersContext parametersContextBuilder) {
        this.parametersContextBuilder = parametersContextBuilder;
    }

    /**
     * 解析方法
     *
     * @param <?>
     * @param clazz
     */
    public void parse(final Class<?> clazz) {
        this.logger.debug("--parsing parameter entity {}--", clazz.getName());
        if (clazz.isAnnotationPresent(ParametersConfig.class)) {
            //3.获取该实体所有字段集合
            final Field[] fields = clazz.getDeclaredFields();
            FieldConfig fieldConfig;
            final Map<String, FieldHandler> fieldHandlerMap = new HashMap<String, FieldHandler>(2, 1);
            FieldHandlerBuilder fieldHandlerBuilder;
            FieldHandler fieldHandler;
            final FieldContextBuilder fieldContextBuilder = parametersContextBuilder.getFieldContextBuilder();
            int modifier;
            for (Field field : fields) {
                modifier = field.getModifiers();
                if (!Modifier.isStatic(modifier) && field.isAnnotationPresent(FieldConfig.class)) {
                    fieldConfig = field.getAnnotation(FieldConfig.class);
                    fieldHandlerBuilder = new FieldHandlerBuilder(fieldContextBuilder, field.getName(), fieldConfig);
                    fieldHandler = fieldHandlerBuilder.build();
                    fieldHandlerMap.put(field.getName(), fieldHandler);
                }
            }
            //4.保存ExtendedEntityHandler
            ParametersHandlerImpl extendedEntityHandler = new ParametersHandlerImpl(fieldHandlerMap);
            parametersContextBuilder.putParametersHandler(clazz, extendedEntityHandler);
            this.logger.debug("--parse parameter entity {} finished--", clazz.getName());
        } else {
            this.logger.error("--parse parameter entity {} missing annotation ParametersConfig-----------------", clazz.getName());
        }
    }
}
