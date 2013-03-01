package com.wolf.framework.worker.workhandler;

import com.wolf.framework.service.parameter.FieldHandler;
import com.wolf.framework.worker.FrameworkMessageContext;
import java.util.Map;

/**
 * 次要参数处理类,丢弃空字符
 *
 * @author zoe
 */
public class MinorParameterDiscardEmptyHandlerImpl implements MinorParameterHandler {

    private final String[] minorParameter;
    private final Map<String, FieldHandler> fieldHandlerMap;

    public MinorParameterDiscardEmptyHandlerImpl(String[] minorParameter, Map<String, FieldHandler> fieldHandlerMap) {
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
            if (paraValue != null) {
                if (paraValue.isEmpty()) {
                    //丢弃空值
                    frameworkMessageContext.removeParameter(parameter);
                } else {
                    //非空验证
                    fieldHandler = this.fieldHandlerMap.get(parameter);
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
