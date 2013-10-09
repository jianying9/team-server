package com.team.message.service;

import com.team.AbstractTeamTest;
import com.team.config.ActionNames;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;

/**
 *
 * @author aladdin
 */
public class ReadUserMessageServiceImplTest extends AbstractTeamTest {

    public ReadUserMessageServiceImplTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

//    @Test
    public void testReadUserMessage() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("messageId", "def0a55a-4ea1-449b-bc24-a1aa76006c30");
        String result = this.testHandler.execute(ActionNames.READ_USER_MESSAGE, parameterMap);
        System.out.println(result);
    }
}
