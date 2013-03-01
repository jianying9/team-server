package com.wolf.framework.test;

import com.wolf.framework.websocket.GlobalApplication;
import java.util.Map;

/**
 *
 * @author zoe
 */
public class TestHandler {
    
    private static GlobalApplication globalApplication = null;
    
    public static void setGlobalApplication(GlobalApplication globalApplication) {
        TestHandler.globalApplication = globalApplication;
    }
    
    public static void execute(String act, Map<String, String> parameterMap) {
        globalApplication.executeService(act, parameterMap);
    }
}
