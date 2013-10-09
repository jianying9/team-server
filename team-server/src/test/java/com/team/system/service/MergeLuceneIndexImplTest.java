package com.team.system.service;

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
public class MergeLuceneIndexImplTest extends AbstractTeamTest {

    public MergeLuceneIndexImplTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

//    @Test
    public void testLogin() {
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        String result = this.testHandler.execute(ActionNames.MERGE_LUCENE_INDEX, parameterMap);
        System.out.println(result);
    }
}
