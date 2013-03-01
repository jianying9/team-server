package com.wolf.framework.service;

import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.injecter.Injecter;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.service.parameter.FieldContextBuilder;
import com.wolf.framework.service.parameter.FieldHandler;
import com.wolf.framework.service.parameter.NumberFieldHandlerImpl;
import com.wolf.framework.service.parameter.ParametersContext;
import com.wolf.framework.service.parameter.ParametersHandler;
import com.wolf.framework.service.parameter.type.FieldTypeEnum;
import com.wolf.framework.service.parameter.type.TypeHandler;
import com.wolf.framework.service.parameter.type.TypeHandlerFactory;
import com.wolf.framework.worker.ServiceWorker;
import com.wolf.framework.worker.ServiceWorkerContext;
import com.wolf.framework.worker.ServiceWorkerImpl;
import com.wolf.framework.worker.workhandler.CloseWorkHandlerImpl;
import com.wolf.framework.worker.workhandler.CreatePageJsonMessageHandlerImpl;
import com.wolf.framework.worker.workhandler.CreateSingleJsonMessageHandlerImpl;
import com.wolf.framework.worker.workhandler.DefaultWorkHandlerImpl;
import com.wolf.framework.worker.workhandler.ExceptionWorkHandlerImpl;
import com.wolf.framework.worker.workhandler.ImportantParameterWorkHandlerImpl;
import com.wolf.framework.worker.workhandler.MinorParameterDefaultReplaceNullAndEmptyHandlerImpl;
import com.wolf.framework.worker.workhandler.MinorParameterDiscardEmptyHandlerImpl;
import com.wolf.framework.worker.workhandler.MinorParameterHandler;
import com.wolf.framework.worker.workhandler.MinorParameterKeepEmptyHandlerImpl;
import com.wolf.framework.worker.workhandler.MinorParameterWorkHandlerImpl;
import com.wolf.framework.worker.workhandler.MultiBroadcastMessageHandlerImpl;
import com.wolf.framework.worker.workhandler.PageParameterWorkHandlerImpl;
import com.wolf.framework.worker.workhandler.ReadParameterWorkHandlerImpl;
import com.wolf.framework.worker.workhandler.ResponseMessageWorkHandlerImpl;
import com.wolf.framework.worker.workhandler.SessionWorkHandlerImpl;
import com.wolf.framework.worker.workhandler.SingleBroadcastMessageHandlerImpl;
import com.wolf.framework.worker.workhandler.ValidateSessionWorkHandlerImpl;
import com.wolf.framework.worker.workhandler.WorkHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;

/**
 * 负责解析annotation ServiceConfig
 *
 * @author zoe
 */
public class ServiceConfigParser<K extends Service, T extends Entity> {

    private final ServiceWorkerContext serviceWorkerContextBuilder;
    private final Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.FRAMEWORK);
    private final String[] pageParameter = {"pageIndex", "pageSize"};
    private final Map<String, FieldHandler> pageHandlerMap = new HashMap<String, FieldHandler>(2, 1);

    public ServiceConfigParser(ServiceWorkerContext serviceWorkerContextBuilder) {
        this.serviceWorkerContextBuilder = serviceWorkerContextBuilder;
        //初始化分页参数配置
        ParametersContext parametersContextBuilder = this.serviceWorkerContextBuilder.getParametersContextBuilder();
        FieldContextBuilder fieldContextBuilder = parametersContextBuilder.getFieldContextBuilder();
        TypeHandlerFactory typeHandlerFactory = fieldContextBuilder.getTypeHandlerFactory();
        TypeHandler intTypeHandler = typeHandlerFactory.getTypeHandler(FieldTypeEnum.INT_SIGNED);
        TypeHandler stringTypeHandler = typeHandlerFactory.getTypeHandler(FieldTypeEnum.CHAR64);
        this.pageHandlerMap.put("pageIndex", new NumberFieldHandlerImpl("pageIndex", stringTypeHandler, FieldTypeEnum.CHAR64, ""));
        this.pageHandlerMap.put("pageSize", new NumberFieldHandlerImpl("pageSize", intTypeHandler, FieldTypeEnum.INT_SIGNED, "15"));
    }

    /**
     * 解析方法
     *
     * @param clazz
     * @param serviceCtxBuilder
     */
    public void parse(final Class<K> clazz) {
        this.logger.debug("--parsing service {}--", clazz.getName());
        if (clazz.isAnnotationPresent(ServiceConfig.class)) {
            //1.获取注解ServiceConfig
            final ServiceConfig serviceConfig = clazz.getAnnotation(ServiceConfig.class);
            final String actionName = serviceConfig.actionName();
            final String[] importantParameter = serviceConfig.importantParameter();
            final String[] minorParameter = serviceConfig.minorParameter();
            final MinorHandlerTypeEnum minorHandlerTypeEnum = serviceConfig.minorHandlerTypeEnum();
            final String[] returnParameter = serviceConfig.returnParameter();
            final Class<?>[] parametersConfigs = serviceConfig.parametersConfigs();
            final ParameterTypeEnum parameterTypeEnum = serviceConfig.parameterTypeEnum();
            final ResponseDataTypeEnum responseDataTypeEnum = serviceConfig.responseDataTypeEnum();
            final boolean requireTransaction = serviceConfig.requireTransaction();
            final SessionHandleTypeEnum sessionHandleTypeEnum = serviceConfig.sessionHandleTypeEnum();
            final boolean response = serviceConfig.response();
            final boolean broadcast = serviceConfig.broadcast();
            final BroadcastTypeEnum broadcastTypeEnum = serviceConfig.boradcastTypeEnum();
            final boolean validateSession = serviceConfig.validateSession();
            //获取字段处理对象集合
            final Map<String, FieldHandler> fieldHandlerMapTemp = new HashMap<String, FieldHandler>(2, 1);
            Set<Entry<String, FieldHandler>> entrySet;
            Map<String, FieldHandler> fieldHandlerMapTmp;
            //参数配置
            final ParametersContext parametersContextBuilder = this.serviceWorkerContextBuilder.getParametersContextBuilder();
            ParametersHandler parametersHandler;
            for (Class<?> parametersConfig : parametersConfigs) {
                parametersHandler = parametersContextBuilder.getParametersHandler(parametersConfig);
                //如果parametersConfig找不到，则抛出异常，停止加载
                if (parametersHandler == null) {
                    StringBuilder mesBuilder = new StringBuilder(512);
                    mesBuilder.append("There was an error parsing service worker. Cause: can not find parametersConfig handler : ").append(clazz);
                    mesBuilder.append("\n").append("error class is ").append(clazz.getName());
                    throw new RuntimeException(mesBuilder.toString());
                }
                fieldHandlerMapTmp = parametersHandler.getFieldHandlerMap();
                entrySet = parametersHandler.getFieldHandlerMap().entrySet();
                for (Entry<String, FieldHandler> entry : entrySet) {
                    if (!fieldHandlerMapTemp.containsKey(entry.getKey())) {
                        fieldHandlerMapTemp.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            final Map<String, FieldHandler> fieldHandlerMap = new HashMap<String, FieldHandler>(2, 1);
            //获取必要参数处理对象
            FieldHandler fieldHandler;
            for (String parameter : importantParameter) {
                fieldHandler = fieldHandlerMapTemp.get(parameter);
                if (fieldHandler == null) {
                    StringBuilder mesBuilder = new StringBuilder(512);
                    mesBuilder.append("There was an error parsing service worker. Cause: can not find important parameter config : ").append(parameter);
                    mesBuilder.append("\n").append("error class is ").append(clazz.getName());
                    throw new RuntimeException(mesBuilder.toString());
                }
                if (!fieldHandlerMap.containsKey(parameter)) {
                    fieldHandlerMap.put(parameter, fieldHandler);
                }
            }
            //获取次要参数
            for (String parameter : minorParameter) {
                fieldHandler = fieldHandlerMapTemp.get(parameter);
                if (fieldHandler == null) {
                    StringBuilder mesBuilder = new StringBuilder(512);
                    mesBuilder.append("There was an error parsing service worker. Cause: can not find minor parameter config : ").append(parameter);
                    mesBuilder.append("\n").append("error class is ").append(clazz.getName());
                    throw new RuntimeException(mesBuilder.toString());
                }
                if (!fieldHandlerMap.containsKey(parameter)) {
                    fieldHandlerMap.put(parameter, fieldHandler);
                }
            }
            //获取返回参数
            for (String parameter : returnParameter) {
                fieldHandler = fieldHandlerMapTemp.get(parameter);
                if (fieldHandler == null) {
                    StringBuilder mesBuilder = new StringBuilder(512);
                    mesBuilder.append("There was an error parsing service worker. Cause: can not find return parameter config : ").append(parameter);
                    mesBuilder.append("\n").append("error class is ").append(clazz.getName());
                    throw new RuntimeException(mesBuilder.toString());
                }
                if (!fieldHandlerMap.containsKey(parameter)) {
                    fieldHandlerMap.put(parameter, fieldHandler);
                }
            }
            //开始生成业务处理链
            //实例化该clazz
            Service service = null;
            try {
                service = clazz.newInstance();
            } catch (Exception e) {
                this.logger.error("There was an error instancing class {}. Cause: {}", clazz.getName(), e.getMessage());
                throw new RuntimeException("There wa an error instancing class ".concat(clazz.getName()));
            }
            //注入相关对象
            Injecter injecter = this.serviceWorkerContextBuilder.getInjecter();
            injecter.parse(service);
            //包装服务类
            WorkHandler workHandler = new DefaultWorkHandlerImpl(service);
            //判断是否需要事务，如果需要则加入事务处理环节
//            if (requireTransaction) {
//                workHandler = new TransactionWorkHandlerImpl(workHandler);
//            }
            //异常处理
            workHandler = new ExceptionWorkHandlerImpl(workHandler);
            //生成消息
            switch (responseDataTypeEnum) {
                case SINGLE:
                    workHandler = new CreateSingleJsonMessageHandlerImpl(returnParameter, fieldHandlerMap, workHandler);
                    break;
                case PAGE:
                    workHandler = new CreatePageJsonMessageHandlerImpl(returnParameter, fieldHandlerMap, workHandler);
                    break;
            }
            //是否响应消息
            if (response) {
                workHandler = new ResponseMessageWorkHandlerImpl(workHandler);
            }
            //是否广播消息
            if (broadcast) {
                switch (broadcastTypeEnum) {
                    case SINGLE:
                        workHandler = new SingleBroadcastMessageHandlerImpl(workHandler);
                        break;
                    case MULTI:
                        workHandler = new MultiBroadcastMessageHandlerImpl(workHandler);
                        break;
                }
            }
            //是否设置session
            if (sessionHandleTypeEnum != SessionHandleTypeEnum.NONE) {
                workHandler = new SessionWorkHandlerImpl(workHandler, sessionHandleTypeEnum);
            }
            workHandler = new CloseWorkHandlerImpl(workHandler);
            //获取参数集合
            Set<String> filterSet = new HashSet<String>(16, 1);
            //是否获取分页参数
            if (responseDataTypeEnum == ResponseDataTypeEnum.PAGE) {
                filterSet.addAll(Arrays.asList(this.pageParameter));
                workHandler = new PageParameterWorkHandlerImpl(this.pageParameter, this.pageHandlerMap, workHandler);
            }
            //判断取值验证类型,将对应处理对象加入到处理环节
            if (parameterTypeEnum == ParameterTypeEnum.PARAMETER) {
                //次要参数
                if (minorParameter.length > 0) {
                    filterSet.addAll(Arrays.asList(minorParameter));
                    MinorParameterHandler minorParameterHandler = null;
                    switch (minorHandlerTypeEnum) {
                        case KEEP_EMPTY:
                            minorParameterHandler = new MinorParameterKeepEmptyHandlerImpl(minorParameter, fieldHandlerMap);
                            break;
                        case DISCARD_EMPTY:
                            minorParameterHandler = new MinorParameterDiscardEmptyHandlerImpl(minorParameter, fieldHandlerMap);
                            break;
                        case DEFAULT_REPLACE_NULL_AND_EMPTY:
                            minorParameterHandler = new MinorParameterDefaultReplaceNullAndEmptyHandlerImpl(minorParameter, fieldHandlerMap);
                            break;
                    }
                    workHandler = new MinorParameterWorkHandlerImpl(minorParameterHandler, workHandler);
                }
                //重要参数
                if (importantParameter.length > 0) {
                    filterSet.addAll(Arrays.asList(importantParameter));
                    workHandler = new ImportantParameterWorkHandlerImpl(importantParameter, fieldHandlerMap, workHandler);
                }
            }
            //是否读参数
            if (!filterSet.isEmpty()) {
                StringBuilder filterBuilder = new StringBuilder(512);
                for (String filter : filterSet) {
                    filterBuilder.append(filter).append("-");
                }
                filterBuilder.setLength(filterBuilder.length() - 1);
                String filter = filterBuilder.toString();
                workHandler = new ReadParameterWorkHandlerImpl(workHandler, filter);
            }
            //是否验证session
            if (validateSession) {
                workHandler = new ValidateSessionWorkHandlerImpl(workHandler);
            }
            //创建对应的工作对象
            final ServiceWorker serviceWorker = new ServiceWorkerImpl(workHandler);
            this.serviceWorkerContextBuilder.putServiceWorker(actionName, serviceWorker, clazz.getName());
            this.logger.debug("--parse service {} finished--", clazz.getName());
        } else {
            this.logger.error("--parse service {} missing annotation ServiceConfig--", clazz.getName());
        }
    }
}
