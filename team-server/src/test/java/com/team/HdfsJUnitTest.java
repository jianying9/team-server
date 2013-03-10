package com.team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aladdin
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

//    @Test
    public void test() throws IOException {
        Configuration config = new Configuration();
        config.set("fs.default.name", "hdfs://192.168.64.50:9000/");
        FileSystem dfs = FileSystem.get(config);
        String dic = "/test";
        Path src = new Path(dic);
        dfs.mkdirs(src);
    }

    @Test
    public void testWriteObject() throws IOException {
        Configuration config = new Configuration();
        config.set("fs.default.name", "hdfs://192.168.64.50:9000/");
        FileSystem dfs = FileSystem.get(config);
        String dic = "/test";
        Path root = new Path(dic);
        String uuid = UUID.randomUUID().toString();
        String tempFile = uuid.concat(".dat");
        Path tempPath = new Path(root, tempFile);
        dfs.createNewFile(tempPath);
        //write
        FSDataOutputStream FSDataOutputStream = dfs.create(tempPath);
        for (int i = 0; i < 10; i++) {
            uuid = UUID.randomUUID().toString();
            FSDataOutputStream.writeBytes(uuid);
            FSDataOutputStream.writeBytes("\r");
        }
        FSDataOutputStream.close();
        //read
        FSDataInputStream FSDataInputStream = dfs.open(tempPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(FSDataInputStream));
        String line;
        line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
    }
}
