package com.team;

import com.team.context.TestApplicationContextBuilder;
import com.team.session.SessionImpl;
import com.team.websocket.TestTeamGlobalApplication;
import com.wolf.framework.session.Session;
import com.wolf.framework.test.TestHandler;
import java.util.Properties;

/**
 *
 * @author zoe
 */
public abstract class AbstractTeamTest {

    private static TestTeamGlobalApplication testTeamGlobalApplication = null;

    public AbstractTeamTest() {
        synchronized (AbstractTeamTest.class) {
            if (testTeamGlobalApplication == null) {
                Properties configProperties = new Properties();
                configProperties.setProperty("appPath", "/test");
                TestApplicationContextBuilder testApplicationContextBuilder = new TestApplicationContextBuilder(configProperties);
                testApplicationContextBuilder.build();
                testTeamGlobalApplication = new TestTeamGlobalApplication(testApplicationContextBuilder.getServiceWorkerMap());
                TestHandler.setGlobalApplication(testTeamGlobalApplication);
                Session session = new SessionImpl("dfe1f92e-9b2c-4c2a-a1bd-d3be0195cc1c");
                testTeamGlobalApplication.setSession(session);
            }
        }
    }
}
