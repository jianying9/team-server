package com.wolf.framework.worker.workhandler;

import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.logger.LogFactory;
import com.wolf.framework.utils.StringUtils;
import com.wolf.framework.websocket.GlobalWebSocket;
import com.wolf.framework.worker.FrameworkMessageContext;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;

/**
 * 必要参数处理
 *
 * @author zoe
 */
public class ReadParameterWorkHandlerImpl implements WorkHandler {

    private final String filter;
    private final WorkHandler nextWorkHandler;

    public ReadParameterWorkHandlerImpl(
            final WorkHandler workHandler,
            final String filter) {
        this.nextWorkHandler = workHandler;
        this.filter = filter;
    }

    @Override
    public void execute(FrameworkMessageContext frameworkMessageContext) {
        String message = frameworkMessageContext.getRequestMessage();
        if (message.isEmpty()) {
            //没有json消息，直接执行
            this.nextWorkHandler.execute(frameworkMessageContext);
        } else {
            //解析json
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = null;
            try {
                rootNode = mapper.readValue(message, JsonNode.class);
            } catch (IOException e) {
                Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.FRAMEWORK);
                logger.error("error json message:{}", message);
                logger.error("parse json error:", e);
            }
            if (rootNode == null) {
                GlobalWebSocket globalWebSocket = frameworkMessageContext.getGlobalWebSocket();
                globalWebSocket.send("{\"flag\":\"INVALID\",\"info\":\"no or involid json message\"}");
            } else {
                //读数据
                Map.Entry<String, JsonNode> entry;
                String name;
                String value;
                Iterator<Map.Entry<String, JsonNode>> iterator = rootNode.getFields();
                while (iterator.hasNext()) {
                    entry = iterator.next();
                    name = entry.getKey();
                    if (this.filter.indexOf(name) > -1) {
                        value = entry.getValue().getTextValue();
                        value = StringUtils.trim(value);
                        frameworkMessageContext.putParameter(name, value);
                    }
                }
                this.nextWorkHandler.execute(frameworkMessageContext);
            }
        }
    }
}
