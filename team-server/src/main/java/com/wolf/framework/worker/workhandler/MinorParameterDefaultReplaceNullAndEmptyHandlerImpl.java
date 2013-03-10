package com.wolf.framework.worker.workhandler;

import com.wolf.framework.service.parameter.FieldHandler;
import com.wolf.framework.worker.FrameworkMessageContext;
import java.util.Map;

/**
 * 次要参数处理类,次要参数如果为null或则空字符串，用default值代替
 *
 * @author aladdin
 */
public class MinorParameterDefaultReplaceNullAndEmptyHandlerImpl implements MinorParameterHandler {

    private final String[] minorParameter;
    private final Map<String, FieldHandler> fieldHandlerMap;

    public MinorParameterDefaultReplaceNullAndEmptyHandlerImpl(String[] minorParameter, Map<String, FieldHandler> fieldHandlerMap) {
        this.minorParameter = minorParameter;
        this.fieldHandlerMap = fieldHandlerMap;
    }

    @Override
    public String execute(FrameworkMessageContext frameworkMessageContext) {
        String paraValue;
        String errorParaName = "";
        String errorMsg = "";
        FieldHandler fieldHandler;
        //验证必要参数是否合法
        for (String parameter : this.minorParameter) {
            paraValue = frameworkMessageContext.getParameter(parameter);
            fieldHandler = this.fieldHandlerMap.get(parameter);
            if (paraValue == null) {
                paraValue = fieldHandler.getDefaultValue();
                frameworkMessageContext.putParameter(parameter, paraValue);
            } else {
                if (paraValue.isEmpty()) {
                    paraValue = fieldHandler.getDefaultValue();
                    frameworkMessageContext.putParameter(parameter, paraValue);
                } else {
                    errorMsg = fieldHandler.validate(paraValue);
                    if (!errorMsg.isEmpty()) {
                        errorParaName = parameter;
                        break;
                    }
                }
            }
        }
        if (!errorMsg.isEmpty()) {
            errorMsg = errorParaName.concat(errorMsg);
        }
        return errorMsg;
    }
}
