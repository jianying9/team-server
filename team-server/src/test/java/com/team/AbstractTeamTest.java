package com.team;

import com.team.session.SessionImpl;
import com.team.websocket.TestTeamGlobalApplication;
import com.wolf.framework.context.ApplicationContextBuilder;
import com.wolf.framework.session.Session;
import com.wolf.framework.test.TestHandler;
import java.util.Properties;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author aladdin
 */
public abstract class AbstractTeamTest {

    private static TestTeamGlobalApplication testTeamGlobalApplication = null;
    private static ApplicationContextBuilder applicationContextBuilder = null;

    @BeforeClass
    public final static void setUpClass() {
        synchronized (AbstractTeamTest.class) {
            if (testTeamGlobalApplication == null) {
                Properties configProperties = new Properties();
                configProperties.setProperty("appPath", "/test");
                configProperties.setProperty("packageName", "com.team");
                configProperties.setProperty("hbaseZookeeperQuorum", "192.168.19.42");
                configProperties.setProperty("fsDefaultName", "hdfs://192.168.64.50:9000/");
                configProperties.setProperty("dataBaseType", "EMBEDDED");
                configProperties.setProperty("dataBaseName", "/data/derby/team");
                applicationContextBuilder = new ApplicationContextBuilder(configProperties);
                applicationContextBuilder.build();
                testTeamGlobalApplication = new TestTeamGlobalApplication(applicationContextBuilder.getServiceWorkerMap());
                TestHandler.setGlobalApplication(testTeamGlobalApplication);
                Session session = new SessionImpl("dfe1f92e-9b2c-4c2a-a1bd-d3be0195cc1c");
                testTeamGlobalApplication.setSession(session);
            }
        }
    }

    @AfterClass
    public final static void tearDownClass() {
        applicationContextBuilder.shutdownDatabase();
    }
}
