package com.team.user.service;

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

//    @Test
    public void testAssertFriendOnline() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("userId", "7b88158d-1d81-40b6-8f59-bfb8252971af");
        String result = this.testHandler.execute(ActionNames.ASSERT_FRIEND_ONLINE, parameterMap);
        System.out.println(result);
    }
}
