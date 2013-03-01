package com.team.message.service;

import com.team.AbstractTeamTest;
import com.team.config.ActionNames;
import com.wolf.framework.test.TestHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zoe
 */
public class ReadUserMessageServiceImplTest extends AbstractTeamTest {

    public ReadUserMessageServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

    @Test
    public void testReadUserMessage() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("messageId", "def0a55a-4ea1-449b-bc24-a1aa76006c30");
        TestHandler.execute(ActionNames.READ_USER_MESSAGE, parameterMap);
    }
}