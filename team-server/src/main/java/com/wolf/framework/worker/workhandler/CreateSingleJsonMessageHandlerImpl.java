package com.wolf.framework.worker.workhandler;

import com.wolf.framework.service.parameter.FieldHandler;
import com.wolf.framework.worker.FrameworkMessageContext;
import java.util.Map;

/**
 * 生成json消息
 * @author aladdin
 */
public class CreateSingleJsonMessageHandlerImpl implements WorkHandler {

    private final String[] returnParameter;
    private final Map<String, FieldHandler> fieldHandlerMap;
    private final WorkHandler nextWorkHandler;

    public CreateSingleJsonMessageHandlerImpl(final String[] returnParameter, final Map<String, FieldHandler> fieldHandlerMap, final WorkHandler nextWorkHandler) {
        this.returnParameter = returnParameter;
        this.fieldHandlerMap = fieldHandlerMap;
        this.nextWorkHandler = nextWorkHandler;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        this.nextWorkHandler.execute(frameworkMessageContext);
        frameworkMessageContext.createJsonMessage(returnParameter, fieldHandlerMap);
    }
}
