package com.team.user.service;

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
public class InquireFriendServiceImplTest extends AbstractTeamTest {

    public InquireFriendServiceImplTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

//    @Test
    public void testInquireFriend() {
        TestHandler.execute(ActionNames.INQUIRE_FRIEND, null);
    }
}
