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
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        String result = this.testHandler.execute(ActionNames.INQUIRE_FRIEND, parameterMap);
        System.out.println(result);
    }
}
