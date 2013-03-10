package com.team;

import com.sun.grizzly.util.ExtendedThreadPool;
import com.sun.grizzly.util.GrizzlyExecutorService;
import com.sun.grizzly.util.ThreadPoolConfig;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class GrizzlyThreadPoolJUnitTest {
    
    public GrizzlyThreadPoolJUnitTest() {
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
    //
     @Test
     public void hello() {
         ThreadPoolConfig config = new ThreadPoolConfig(
                 "test",
                 10,
                 1000,
                 null,
                 -1,
                 30000,
                 TimeUnit.MILLISECONDS,
                 null,
                 5,
                 null
                 );
         ExtendedThreadPool pool = GrizzlyExecutorService.createInstance(config);
         
     }
}
