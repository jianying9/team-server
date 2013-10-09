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
public class LoginServiceImplTest extends AbstractTeamTest {

    public LoginServiceImplTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

//    @Test
    public void testLogin() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("userEmail", "1@91yong.com");
        parameterMap.put("password", "670b14728ad9902aecba32e22fa4f6bd");
        String result = this.testHandler.execute(ActionNames.LOGIN, parameterMap);
        System.out.println(result);
    }
}
