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
public class SearchUserByNickNameServiceImplTest extends AbstractTeamTest {

    public SearchUserByNickNameServiceImplTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

//    @Test
    public void testSearchNickName() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("nickName", "1");
        parameterMap.put("pageIndex", "31_1.0");
        parameterMap.put("pageSize", "10");
        String result = this.testHandler.execute(ActionNames.SEARCH_USER_BY_NICKNAME, parameterMap);
        System.out.println(result);
    }
}
