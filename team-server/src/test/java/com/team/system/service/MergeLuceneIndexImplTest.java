package com.team.system.service;

import com.team.AbstractTeamTest;
import com.team.config.ActionNames;
import com.wolf.framework.test.TestHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void testLogin() {
        TestHandler.execute(ActionNames.MERGE_LUCENE_INDEX, null);
    }
}
