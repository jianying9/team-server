package com.team.user.service;

import com.team.AbstractTeamTest;
import com.team.config.ActionNames;
import com.wolf.framework.test.TestHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class AssertFriendOnlineServiceImplTest extends AbstractTeamTest {

    public AssertFriendOnlineServiceImplTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

    @Test
    public void testAssertFriendOnline() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("userId", "7b88158d-1d81-40b6-8f59-bfb8252971af");
        TestHandler.execute(ActionNames.ASSERT_FRIEND_ONLINE, parameterMap);
    }
}
