package com.team.user.service;

import com.team.AbstractTeamTest;
import com.team.config.ActionNames;
import com.wolf.framework.test.TestHandler;
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
public class InquireFriendServiceImplTest extends AbstractTeamTest {

    public InquireFriendServiceImplTest() {
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
    public void testInquireFriend() {
        TestHandler.execute(ActionNames.INQUIRE_FRIEND, null);
    }
}
