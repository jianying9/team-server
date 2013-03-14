package com.wolf.framework.dao.parser;

import com.wolf.framework.config.FrameworkLoggerEnum;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.EntityDao;
import com.wolf.framework.dao.EntityDaoBuilder;
import com.wolf.framework.dao.EntityDaoContext;
import com.wolf.framework.dao.annotation.DaoConfig;
import com.wolf.framework.dao.annotation.Key;
import com.wolf.framework.logger.LogFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

/**
 * 负责解析annotation EntityConfig
 *
 * @author aladdin
 */
public class EntityConfigDaoParser<T extends Entity> {

    private final Logger logger = LogFactory.getLogger(FrameworkLoggerEnum.FRAMEWORK);
    private final EntityDaoContext<T> entityDaoContext;

    public EntityConfigDaoParser(EntityDaoContext<T> entityDaoContext) {
        this.entityDaoContext = entityDaoContext;
    }

    /**
     * 解析方法
     *
     * @param <T>
     * @param clazz
     */
    public void parse(final Class<T> clazz) {
        this.logger.debug("--parsing entity DAO {}--", clazz.getName());
        if (clazz.isAnnotationPresent(DaoConfig.class)) {
            //1.获取注解DaoConfig
            final DaoConfig daoConfig = clazz.getAnnotation(DaoConfig.class);
            //3.获取实体标识
            final String tableName = daoConfig.tableName();
            //5获取该实体所有字段集合
            Field[] fieldTemp = clazz.getDeclaredFields();
            //key
            KeyHandler keyHandler = null;
            //column
            List<ColumnHandler> columnHandlerList = new ArrayList<ColumnHandler>(fieldTemp.length);
            ColumnHandler columnHandler;
            int modifier;
            String fieldName;
            String columnFamily;
            for (Field field : fieldTemp) {
                modifier = field.getModifiers();
                if (!Modifier.isStatic(modifier)) {
                    //非静态字段
                    fieldName = field.getName();
                    if (field.isAnnotationPresent(Key.class)) {
                        //解析key
                        keyHandler = new KeyHandlerImpl(fieldName);
                    } else {
                        columnHandler = new ColumnHandlerImpl(fieldName);
                        columnHandlerList.add(columnHandler);
                    }
                }
            }
            final boolean useCache = daoConfig.useCache();
            final int maxEntriesLocalHeap = daoConfig.maxEntriesLocalHeap();
            final int timeToIdleSeconds = daoConfig.timeToIdleSeconds();
            final int timeToLiveSeconds = daoConfig.timeToLiveSeconds();
            EntityDaoBuilder<T> entityDaoBuilder = new EntityDaoBuilder<T>(
                    tableName,
                    keyHandler,
                    columnHandlerList,
                    clazz,
                    useCache,
                    maxEntriesLocalHeap,
                    timeToIdleSeconds,
                    timeToLiveSeconds,
                    this.entityDaoContext);
            EntityDao<T> entityDao = entityDaoBuilder.build();
            entityDaoContext.putEntityDao(clazz, entityDao, tableName);
            this.logger.debug("--parse entity DAO {} finished--", clazz.getName());
        } else {
            this.logger.error("--parse entity DAO {} missing annotation EntityConfig--", clazz.getName());
        }
    }
}
