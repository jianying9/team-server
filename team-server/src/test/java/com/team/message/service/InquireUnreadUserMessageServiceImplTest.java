package com.team.message.service;

import com.team.AbstractTeamTest;
import com.team.config.ActionNames;
import java.util.HashMap;
import org.junit.After;
import org.junit.Before;

/**
 *
 * @author aladdin
 */
public class InquireUnreadUserMessageServiceImplTest extends AbstractTeamTest {

    public InquireUnreadUserMessageServiceImplTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

//    @Test
    public void testInquireUnreadUserMessage() {
        String result = this.testHandler.execute(ActionNames.INQUIRE_UNREAD_USER_MESSAGE, new HashMap<String, String>(2, 1));
        System.out.println(result);
    }
}
