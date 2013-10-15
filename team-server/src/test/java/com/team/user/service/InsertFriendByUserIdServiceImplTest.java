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
public class InsertFriendByUserIdServiceImplTest extends AbstractTeamTest {

    public InsertFriendByUserIdServiceImplTest() {
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
        parameterMap.put("userId", "9dee23d9-1fd9-4ac1-86c0-e9a027a623ed");
        String result = this.testHandler.execute(ActionNames.INSERT_FRIEND_BY_USER_ID, parameterMap);
        System.out.println(result);
    }
}
