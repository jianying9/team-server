package com.team.message.service;

import com.team.AbstractTeamTest;
import com.team.config.ActionNames;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class InsertUserMessageServiceImplTest extends AbstractTeamTest {

    public InsertUserMessageServiceImplTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

    @Test
    public void testInsertUserMessage() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("receiveId", "9dee23d9-1fd9-4ac1-86c0-e9a027a623ed");
        parameterMap.put("message", "test2");
        String result = this.testHandler.execute(ActionNames.INSERT_USER_MESSAGE, parameterMap);
        System.out.println(result);
    }
}
