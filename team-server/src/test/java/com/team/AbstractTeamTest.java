package com.team;

import com.team.context.TestApplicationContextBuilder;
import com.team.session.SessionImpl;
import com.team.websocket.TestTeamGlobalApplication;
import com.wolf.framework.dao.EntityDaoBuilder;
import com.wolf.framework.lucene.HdfsLucene;
import com.wolf.framework.session.Session;
import com.wolf.framework.test.TestHandler;
import java.util.List;
import java.util.Properties;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author aladdin
 */
public abstract class AbstractTeamTest {

    private static TestTeamGlobalApplication testTeamGlobalApplication = null;
    
    @BeforeClass
    public final static void setUpClass() {
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
    
    @AfterClass
    public final static void tearDownClass() {
        List<HdfsLucene> hdfsLuceneList = EntityDaoBuilder.ALL_HDFS_LUCENE;
        if (hdfsLuceneList.isEmpty() == false) {
            for (HdfsLucene hdfsLucene : hdfsLuceneList) {
                hdfsLucene.tryToRotate();
                hdfsLucene.tryToMerge();
            }
        }
    }
}
