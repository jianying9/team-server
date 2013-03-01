package com.team;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author zoe
 */
public class HdfsJUnitTest {

    public HdfsJUnitTest() {
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
    public void test() throws IOException {
        Configuration config = new Configuration();
        config.set("fs.default.name", "hdfs://192.168.64.50:9000/");
        FileSystem dfs = FileSystem.get(config);
        String dic = "/lucene";
        Path src = new Path(dic);
        dfs.mkdirs(src);
    }
}
