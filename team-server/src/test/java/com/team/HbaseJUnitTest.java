package com.team;

import com.wolf.framework.context.ApplicationContext;
import com.wolf.framework.context.ApplicationContextBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author aladdin
 */
public class HbaseJUnitTest {

    public HbaseJUnitTest() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

//    @Test
    public void test() {
        Properties configProperties = new Properties();
        configProperties.setProperty("appPath", "/test");
        configProperties.setProperty("packageName", "com.team");
        configProperties.setProperty("hbaseZookeeperQuorum", "192.168.19.42");
        configProperties.setProperty("fsDefaultName", "hdfs://192.168.64.50:9000/");
        configProperties.setProperty("dataBaseType", "EMBEDDED");
        configProperties.setProperty("dataBaseName", "/data/derby/team");
        ApplicationContextBuilder applicationContextBuilder = new ApplicationContextBuilder(configProperties);
        applicationContextBuilder.build();
        ApplicationContext.CONTEXT.shutdownDatabase();
    }

//    @Test
    public void testHadoop() throws IOException {
        Configuration config = new Configuration();
        String rootPath = "hdfs://192.168.64.50:9000/";
        config.set("fs.default.name", rootPath);
        FileSystem dfs = FileSystem.get(config);
        String dirName = "user";
        Path src = new Path(rootPath.concat(dirName));
//        dfs.mkdirs(src);
        dfs.deleteOnExit(src);
//        String subDirName = "subDirectory";
//        src = new Path(dfs.getWorkingDirectory() + "/TestDirectory/" + subDirName);
//        dfs.mkdirs(src);
//        src = new Path(dfs.getWorkingDirectory() + "/TestDirectory/subDirectory/" + System.currentTimeMillis() + ".txt");
//        dfs.createNewFile(src);
//        FSDataOutputStream fs = dfs.create(src);
//        fs.writeBytes("111111111111111111111111");
//        fs.close();
        dfs.close();
    }

//    @Test
    public void testHadoopFile() throws IOException {
        Configuration config = new Configuration();
        config.set("fs.default.name", "hdfs://192.168.64.50:9000/");
        FileSystem dfs = FileSystem.get(config);
        String dic = dfs.getWorkingDirectory() + "/TestDirectory/subDirectory/";
        String fileSrc;
        String value;
        FSDataOutputStream fs;
        Path src;
        for (int i = 0; i < 1000000; i++) {
            if (i % 1000 == 0) {
                System.out.println("file num:" + i);
            }
            value = Long.toString(System.currentTimeMillis());
            fileSrc = dic + value + ".txt";
            src = new Path(fileSrc);
            dfs.createNewFile(src);
            fs = dfs.create(src);
            fs.writeBytes(value);
            fs.close();
        }
    }

//    @Test
    public void testHbaseInsert() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.19.42");
        HTable table = new HTable(config, "User");
        String uuid = UUID.randomUUID().toString();
        final byte[] rowKey = Bytes.toBytes(uuid);
        final byte[] columnFamily = Bytes.toBytes("user");
        final byte[] nickName = Bytes.toBytes("nickName");
        final byte[] password = Bytes.toBytes("password");
        final byte[] userEmail = Bytes.toBytes("userEmail");
        Put put = new Put(rowKey);
        put.add(columnFamily, nickName, Bytes.toBytes("aladdin"));
        put.add(columnFamily, password, Bytes.toBytes("000000"));
        put.add(columnFamily, userEmail, Bytes.toBytes("aladdin@91.com"));
        table.put(put);
    }

//    @Test
    public void testHbaseBatchInsert() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.19.42");
        HTable table = new HTable(config, "User");
        final byte[] columnFamily = Bytes.toBytes("user");
        final byte[] nickName = Bytes.toBytes("nickName");
        final byte[] password = Bytes.toBytes("password");
        final byte[] userEmail = Bytes.toBytes("userEmail");
        String uuid;
        byte[] rowKey;
        String text;
        Put put;
        long start = System.currentTimeMillis();
        List<Put> putList = new ArrayList<Put>(100);
        for (int index = 10000000; index < 40000000; index++) {
            uuid = UUID.randomUUID().toString();
            rowKey = Bytes.toBytes(uuid);
            put = new Put(rowKey);
            text = Integer.toString(index);
            put.add(columnFamily, nickName, Bytes.toBytes(text));
            put.add(columnFamily, password, Bytes.toBytes(text));
            put.add(columnFamily, userEmail, Bytes.toBytes(text + "@91.com"));
            putList.add(put);
            if (putList.size() >= 2000) {
                System.out.println("index:" + index);
                table.put(putList);
                putList.clear();
            }
        }
        if (!putList.isEmpty()) {
            table.put(putList);
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("time:" + time);
    }

//    @Test
    public void testHbaseUpdate() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.19.42");
        HTable table = new HTable(config, "User");
        String uuid = "2c68f04a-8b41-43e1-aff9-6ae042e225a5";
        final byte[] rowKey = Bytes.toBytes(uuid);
        final byte[] columnFamily = Bytes.toBytes("user");
        final byte[] nickName = Bytes.toBytes("nickName");
        final byte[] password = Bytes.toBytes("password");
        final byte[] userEmail = Bytes.toBytes("userEmail");
        Put put = new Put(rowKey);
        put.add(columnFamily, nickName, Bytes.toBytes("zoe1"));
        put.add(columnFamily, password, Bytes.toBytes("111111"));
        put.add(columnFamily, userEmail, Bytes.toBytes("aladdin@91.com"));
        table.put(put);
    }

//    @Test
    public void testHbaseDelete() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.19.42");
        HTable table = new HTable(config, "User");
        String uuid = "2c68f04a-8b41-43e1-aff9-6ae042e225a5";
        final byte[] rowKey = Bytes.toBytes(uuid);
        Delete delete = new Delete(rowKey);
        table.delete(delete);
    }

//    @Test
    public void testHbaseGet() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.19.42");
        HTable table = new HTable(config, "User");
        String uuid = "2c68f04a-8b41-43e1-aff9-6ae042e225a5";
        final byte[] rowKey = Bytes.toBytes(uuid);
        final byte[] columnFamily = Bytes.toBytes("user");
        final byte[] nickName = Bytes.toBytes("nickName");
        Get get = new Get(rowKey);
        Result result = table.get(get);
        byte[] value = result.getValue(columnFamily, nickName);
        System.out.println(Bytes.toString(value));
    }

//    @Test
    public void testHbaseGetList() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.19.42");
        HTable table = new HTable(config, "User");
        String uuid = "2c68f04a-8b41-43e1-aff9-6ae042e225a5";
        byte[] rowKey = Bytes.toBytes(uuid);
        final byte[] columnFamily = Bytes.toBytes("user");
        final byte[] nickName = Bytes.toBytes("nickName");
        List<Get> getList = new ArrayList<Get>(2);
        Get get = new Get(rowKey);
        getList.add(get);
        uuid = "a4428910-14da-4b39-bb99-b900d724d9c1";
        rowKey = Bytes.toBytes(uuid);
        get = new Get(rowKey);
        getList.add(get);
        byte[] value;
        Result[] result = table.get(getList);
        for (int index = 0; index < result.length; index++) {
            System.out.println(Bytes.toString(result[index].getRow()));
            value = result[index].getValue(columnFamily, nickName);
            System.out.println(Bytes.toString(value));
        }
    }

//    @Test
    public void testHbaseScan() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.19.42");
        HTable table = new HTable(config, "User");
        Scan scan = new Scan();
        scan.setBatch(100);
        scan.setMaxVersions();
        String[] uuids = {
            "00000023-0a39-41a9-8907-85cf85239d0c",
            "0000008d-026b-4df9-9079-da8646b77b76",
            "00000124-0355-40c7-9dcc-514d92410149",
            "00000151-0e07-4d7e-b7fb-97c8599e97a6",
            "000001b6-b966-4f8d-bf2a-f6b586878a41",
            "000001d7-3e41-4165-91f6-64b43f411332",
            "000002a1-8c56-414b-b8aa-f772e5449fe3",
            "0000034d-76a4-4d45-b6e0-c9b20d32aace",
            "00000372-affa-425c-8590-71b139d05b41",
            "00000439-eb41-4038-8e0f-86163db0a0bb",
            "000004ee-7861-42f8-b218-f173c732afd7",
            "000005bc-3152-442f-ad1b-cee9ffbe4467",
            "000006d1-7bb0-4cba-8289-fdd7aa0072d6",
            "00000702-c420-431b-a595-55257e36a71e",
            "000008f0-8136-41fb-9716-78850d265415",
            "0000095a-1968-4e9c-a121-9424e300cda1",
            "0000097a-ab28-4595-b0cb-89f0719205f6",
            "00000a0d-4a02-4ef9-a967-36e0c16d50c3",
            "00000a3a-6761-41fa-b714-b501cb835b01",
            "00000b38-216c-470c-bc01-269d1d6906f4"};
        byte[] columnFamily = Bytes.toBytes("user");
        byte[] nickName = Bytes.toBytes("nickName");
        FilterList idFilterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        Filter filter;
        for (String uuid : uuids) {
//            filter = new RowFilter(CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(uuid)));
//            filter = new WhileMatchFilter(filter);
            filter = new PrefixFilter(Bytes.toBytes(uuid));
            idFilterList.addFilter(filter);
        }
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(idFilterList);
        SubstringComparator substringComparator = new SubstringComparator("88");
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(columnFamily, nickName, CompareOp.EQUAL, substringComparator);
        filterList.addFilter(singleColumnValueFilter);
        scan.setFilter(filterList);
//        Filter filter = new SingleColumnValueFilter(columnFamily, nickName, CompareOp.EQUAL, Bytes.toBytes("1"));
//        scan.setFilter(filter);
//        scan.setStartRow(Bytes.toBytes("00000023-0a39-41a9-8907-85cf85239d0c"));
//        scan.setStopRow(Bytes.toBytes("00000439-eb41-4038-8e0f-86163db0a0bb"));
        long start = System.currentTimeMillis();
        ResultScanner rs = table.getScanner(scan);
        int num = 0;
        Result result = rs.next();
        byte[] value;
        while (result != null) {
            num++;
            value = result.getValue(columnFamily, nickName);
            System.out.println("nickName:" + Bytes.toString(value));
            result = rs.next();
        }
        System.out.println("num:" + num);
//        Result[] results = rs.next(100);
        rs.close();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
//        KeyValue[] keyValues = result.raw();
//        KeyValue keyValue;
//        for (int i = 0; i < keyValues.length; i++) {
//            keyValue = keyValues[i];
//            System.out.println(Bytes.toString(keyValue.getValue()));
//        }
    }

//    @Test
    public void testHbaseSearch() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.19.42");
        HTable table = new HTable(config, "_Index");
        Scan scan = new Scan();
        scan.setBatch(100);
        scan.setMaxVersions();
        scan.setStartRow(Bytes.toBytes("User_nickName_100000"));
        scan.setStopRow(Bytes.toBytes("User_nickName_100000~"));
        long start = System.currentTimeMillis();
        ResultScanner rs = table.getScanner(scan);
        int num = 0;
        Result result = rs.next();
        byte[] value;
        while (result != null) {
            num++;
            value = result.getRow();
            System.out.println("row:" + Bytes.toString(value));
            result = rs.next();
        }
        System.out.println("num:" + num);
        rs.close();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

//    @Test
    public void testHbaseCreateTable() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.19.42");
        HBaseAdmin hbase = new HBaseAdmin(config);
        String tableName = "LuceneTemp";
        byte[] tableNameByte = Bytes.toBytes(tableName);
        byte[] familyNameByte = Bytes.toBytes(tableName.toLowerCase());
        boolean flag = hbase.tableExists(tableNameByte);
        System.out.println(flag);
        if (flag) {
            HTableDescriptor hTableDescriptor = hbase.getTableDescriptor(tableNameByte);
            HColumnDescriptor hColumnDescriptor = hTableDescriptor.getFamily(familyNameByte);
            if (hColumnDescriptor == null) {
                throw new RuntimeException("ddddddddd");
            } else {
                byte[] result = hColumnDescriptor.getName();
                System.out.println(Bytes.toString(result));
            }
        }
        HTableDescriptor hTableDescriptor = new HTableDescriptor("Test");
        byte[] columnFamily = Bytes.toBytes("test");
        HColumnDescriptor columnFamilyDescriptor = new HColumnDescriptor(columnFamily);
        hTableDescriptor.addFamily(columnFamilyDescriptor);
        hbase.createTable(hTableDescriptor);
        HTable table = new HTable(config, "Test");
        Put put = new Put(Bytes.toBytes("reeeeee"));
        table.put(put);
    }

    public void testHbaseCount() {
    }
}
