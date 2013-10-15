package com.team;

import com.team.session.SessionImpl;
import com.wolf.framework.config.FrameworkConfig;
import com.wolf.framework.context.ApplicationContext;
import com.wolf.framework.session.Session;
import com.wolf.framework.test.TestHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;

/**
 *
 * @author aladdin
 */
public abstract class AbstractTeamTest {

    protected final TestHandler testHandler;

    public AbstractTeamTest() {
        Map<String, String> parameterMap = new HashMap<String, String>(8, 1);
        parameterMap.put(FrameworkConfig.ANNOTATION_SCAN_PACKAGES, "com.team");
        parameterMap.put(FrameworkConfig.TASK_CORE_POOL_SIZE, "10");
        parameterMap.put(FrameworkConfig.TASK_MAX_POOL_SIZE, "20");
        //
        parameterMap.put(FrameworkConfig.REDIS_SERVER_HOST, "192.168.59.49");
        parameterMap.put(FrameworkConfig.REDIS_SERVER_PORT, "6379");
        parameterMap.put(FrameworkConfig.REDIS_MAX_POOL_SIZE, "20");
        parameterMap.put(FrameworkConfig.REDIS_MIN_POOL_SIZE, "10");
        this.testHandler = new TestHandler(parameterMap);
        Session session = new SessionImpl("d191379e-cc9e-4a6b-a0c8-57aea33bacad");
        this.testHandler.setSession(session);
    }

    @AfterClass
    public static void tearDownClass() {
        ApplicationContext.CONTEXT.contextDestroyed();
    }
}
