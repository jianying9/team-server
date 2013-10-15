package com.team.user.service;

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
        parameterMap.put("userId", "d191379e-cc9e-4a6b-a0c8-57aea33bacad");
        String result = this.testHandler.execute(ActionNames.ASSERT_FRIEND_ONLINE, parameterMap);
        System.out.println(result);
    }
}
