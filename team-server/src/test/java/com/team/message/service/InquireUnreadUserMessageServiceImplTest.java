package com.team.message.service;

import com.team.AbstractTeamTest;
import com.team.config.ActionNames;
import com.wolf.framework.test.TestHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void testInquireUnreadUserMessage() {
        TestHandler.execute(ActionNames.INQUIRE_UNREAD_USER_MESSAGE, null);
    }
}
