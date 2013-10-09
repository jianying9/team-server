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
public class InsertFriendServiceImplTest extends AbstractTeamTest {

    public InsertFriendServiceImplTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

//    @Test
    public void testInsertFriend() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("userId", "4a483823-1e07-4a30-8073-eaa265a28790");
        String result = this.testHandler.execute(ActionNames.INSERT_FRIEND, parameterMap);
        System.out.println(result);
    }
}
