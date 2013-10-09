package com.team.user.service;

import com.team.AbstractTeamTest;
import com.team.config.ActionNames;
import com.wolf.framework.utils.SecurityUtils;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class RegisterServiceImplTest extends AbstractTeamTest {

    public RegisterServiceImplTest() {
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
        parameterMap.put("userEmail", "23@91yong.com");
        parameterMap.put("nickName", "23");
        parameterMap.put("password", SecurityUtils.encryptByMd5("000000"));
        String result = this.testHandler.execute(ActionNames.REGISTER, parameterMap);
        System.out.println(result);
    }

//    @Test
    public void testRegisterBatch() {
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        parameterMap.put("password", SecurityUtils.encryptByMd5("000000"));
        String nickName;
        String userEmail;
        String result;
        for (int index = 10100; index < 20000; index++) {
            if (index % 100 == 0) {
                System.err.println(index);
            }
//            System.err.println(index);
            nickName = Integer.toString(index);
            userEmail = nickName.concat("@91yong.com");
            parameterMap.put("userEmail", userEmail);
            parameterMap.put("nickName", nickName);
            result = this.testHandler.execute(ActionNames.REGISTER, parameterMap);
            System.out.println(result);
        }
    }
}
