package com.team.user.service;

import com.team.AbstractTeamTest;
import com.team.config.ActionNames;
import com.wolf.framework.test.TestHandler;
import com.wolf.framework.utils.SecurityUtils;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author zoe
 */
public class RegisterServiceImplTest extends AbstractTeamTest {

    public RegisterServiceImplTest() {
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

    @Test
    public void testRegister() {
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        parameterMap.put("userEmail", "3@91yong.com");
        parameterMap.put("nickName", "3");
        parameterMap.put("password", SecurityUtils.encryptByMd5("000000"));
        TestHandler.execute(ActionNames.REGISTER, parameterMap);
    }

//    @Test
    public void testRegisterBatch() {
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        parameterMap.put("password", SecurityUtils.encryptByMd5("000000"));
        String nickName;
        String userEmail;
        for (int index = 10000; index < 10100; index++) {
            if (index % 100 == 0) {
                System.err.println(index);
            }
//            System.err.println(index);
            nickName = Integer.toString(index);
            userEmail = nickName.concat("@91yong.com");
            parameterMap.put("userEmail", userEmail);
            parameterMap.put("nickName", nickName);
            TestHandler.execute(ActionNames.REGISTER, parameterMap);
        }
    }
}
