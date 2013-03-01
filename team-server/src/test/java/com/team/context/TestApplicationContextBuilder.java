package com.team.context;

import com.wolf.framework.config.FrameworkConfig;
import com.wolf.framework.context.AbstractApplicationContextBuilder;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.dictionary.DynamicDictionaryHandler;
import com.wolf.framework.hbase.HTableHandler;
import com.wolf.framework.hbase.HTableHandlerImpl;
import com.wolf.framework.service.Service;
import java.io.IOException;
import java.util.Properties;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hbase.HBaseConfiguration;

/**
 * 全局信息构造类
 *
 * @author zoe
 */
public final class TestApplicationContextBuilder<T extends Entity, K extends Service, E extends DynamicDictionaryHandler<T>> extends AbstractApplicationContextBuilder<T, K, E> {

    private final Properties properties;

    public TestApplicationContextBuilder(final Properties properties) {
        this.properties = properties;
    }

    @Override
    protected String getAppPath() {
        return this.properties.getProperty("appPath");
    }

    @Override
    public void build() {
        this.baseBuild();
    }

    @Override
    protected String getCompileModule() {
        return FrameworkConfig.UNIT_TEST;
    }

    @Override
    protected String[] getPackageNames() {
        String[] pack = {"com.team"};
        return pack;
    }

    @Override
    protected HTableHandler hTableHandlerBuild() {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "192.168.19.42");
        HTableHandler hTableHandler = new HTableHandlerImpl(config);
        return hTableHandler;
    }

    @Override
    protected FileSystem fileSystemBuild() {
        Configuration config = new Configuration();
        config.set("fs.default.name", "hdfs://192.168.64.50:9000/");
        FileSystem dfs;
        try {
            dfs = FileSystem.get(config);
        } catch (IOException ex) {
            this.logger.error("init hdfs file system error....see log");
            throw new RuntimeException(ex);
        }
        return dfs;
    }
}
