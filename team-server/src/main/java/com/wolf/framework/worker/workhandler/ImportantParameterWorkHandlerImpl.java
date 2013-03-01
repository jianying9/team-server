package com.wolf.framework.worker.workhandler;

import com.wolf.framework.service.parameter.FieldHandler;
import com.wolf.framework.websocket.GlobalWebSocket;
import com.wolf.framework.worker.FrameworkMessageContext;
import java.util.Map;

/**
 * 必要参数处理
 *
 * @author zoe
 */
public class ImportantParameterWorkHandlerImpl implements WorkHandler {

    private final WorkHandler nextWorkHandler;
    private final String[] importantParameter;
    private final Map<String, FieldHandler> fieldHandlerMap;

    public ImportantParameterWorkHandlerImpl(
            final String[] importantParameter,
            final Map<String, FieldHandler> fieldHandlerMap,
            final WorkHandler workHandler) {
        this.nextWorkHandler = workHandler;
        this.importantParameter = importantParameter;
        this.fieldHandlerMap = fieldHandlerMap;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        String paraValue;
        String errorParaName = "";
        String errorMsg = "";
        FieldHandler fieldHandler;
        //验证必要参数是否合法
        for (String parameter : this.importantParameter) {
            paraValue = frameworkMessageContext.getParameter(parameter);
            if (paraValue == null) {
                errorMsg = WorkHandler.NULL_MESSAGE;
                errorParaName = parameter;
                break;
            }
            if (paraValue.isEmpty()) {
                errorMsg = WorkHandler.EMPTY_MESSAGE;
                errorParaName = parameter;
                break;
            }
            fieldHandler = this.fieldHandlerMap.get(parameter);
            errorMsg = fieldHandler.validate(paraValue);
            if (!errorMsg.isEmpty()) {
                errorParaName = parameter;
                break;
            }
        }
        if (errorMsg.isEmpty()) {
            //验证通过
            this.nextWorkHandler.execute(frameworkMessageContext);
        } else {
            //返回错误消息
            errorMsg = errorParaName.concat(errorMsg);
            frameworkMessageContext.invalid();
            frameworkMessageContext.setInfo(errorMsg);
            String message = frameworkMessageContext.getJson();
            GlobalWebSocket globalWebSocket = frameworkMessageContext.getGlobalWebSocket();
            globalWebSocket.send(message);
        }
    }
}
