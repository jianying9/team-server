package com.wolf.framework.worker.workhandler;

import com.wolf.framework.service.parameter.FieldHandler;
import com.wolf.framework.worker.FrameworkMessageContext;
import java.util.Map;

/**
 * 分页参数处理
 *
 * @author zoe
 */
public class PageParameterWorkHandlerImpl implements WorkHandler {

    private final WorkHandler nextWorkHandler;
    private final String[] pageParameter;
    private final Map<String, FieldHandler> fieldHandlerMap;

    public PageParameterWorkHandlerImpl(
            final String[] pageParameter,
            final Map<String, FieldHandler> fieldHandlerMap,
            final WorkHandler workHandler) {
        this.nextWorkHandler = workHandler;
        this.pageParameter = pageParameter;
        this.fieldHandlerMap = fieldHandlerMap;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        String paraValue;
        FieldHandler fieldHandler;
        String errorMsg = "";
        for (String parameter : pageParameter) {
            fieldHandler = this.fieldHandlerMap.get(parameter);
            paraValue = frameworkMessageContext.getParameter(parameter);
            if (paraValue == null) {
                paraValue = fieldHandler.getDefaultValue();
            } else {
                errorMsg = fieldHandler.validate(paraValue);
                if (!errorMsg.isEmpty()) {
                    paraValue = fieldHandler.getDefaultValue();
                }
                frameworkMessageContext.removeParameter(parameter);
            }
            frameworkMessageContext.setPageParameter(parameter, paraValue);
        }
        this.nextWorkHandler.execute(frameworkMessageContext);
    }
}
