package com.team;

import com.team.session.SessionImpl;
import com.wolf.framework.context.ApplicationContext;
import com.wolf.framework.session.Session;
import com.wolf.framework.test.TestHandler;
import java.util.Properties;
import org.junit.AfterClass;

/**
 *
 * @author aladdin
 */
public abstract class AbstractTeamTest {

    protected final TestHandler testHandler;

    public AbstractTeamTest() {
        Properties configProperties = new Properties();
        configProperties.setProperty("appPath", "/test");
        configProperties.setProperty("packageName", "com.team");
        configProperties.setProperty("hbaseZookeeperQuorum", "192.168.19.42");
        configProperties.setProperty("fsDefaultName", "hdfs://192.168.64.50:9000/");
        configProperties.setProperty("dataBaseType", "EMBEDDED");
        configProperties.setProperty("dataBaseName", "/data/derby/team");
        this.testHandler = new TestHandler(configProperties);
        Session session = new SessionImpl("dfe1f92e-9b2c-4c2a-a1bd-d3be0195cc1c");
        this.testHandler.setSession(session);
    }

    @AfterClass
    public static void tearDownClass() {
        ApplicationContext.CONTEXT.shutdownDatabase();
    }
}
