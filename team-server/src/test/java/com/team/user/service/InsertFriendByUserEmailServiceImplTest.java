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
public class InsertFriendByUserEmailServiceImplTest extends AbstractTeamTest {

    public InsertFriendByUserEmailServiceImplTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

    @Test
    public void testInsertFriend() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("userEmail", "10020@91yong.com");
        String result = this.testHandler.execute(ActionNames.INSERT_FRIEND_BY_USER_EMAIL, parameterMap);
        System.out.println(result);
    }
}
