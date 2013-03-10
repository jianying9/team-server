package com.team.dictionary.entity;

import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.Column;
import com.wolf.framework.dao.annotation.DaoConfig;
import com.wolf.framework.dao.annotation.Key;
import com.wolf.framework.service.parameter.FieldConfig;
import com.wolf.framework.service.parameter.ParametersConfig;
import com.wolf.framework.service.parameter.type.FieldTypeEnum;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典
 *
 * @author aladdin
 */
@DaoConfig(
tableName = "Dictionary",
useCache = true)
@ParametersConfig()
public class DictionaryEntity extends Entity {

    @Key()
    @FieldConfig(type = FieldTypeEnum.UUID, fieldDesc = "字典ID")
    private String dicId;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.CHAR24, fieldDesc = "字典名称")
    private String dicName;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.CHAR24, fieldDesc = "缺省值")
    private String dicDefault;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.TINY_INT_SIGNED, fieldDesc = "字典类型:0列表类型,1树类型")
    private byte dicType;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.INT_SIGNED, fieldDesc = "当前eleValue最大值")
    private int dicIndex;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.CHAR24, fieldDesc = "字典描述")
    private String dicDesc;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.TINY_INT_SIGNED, fieldDesc = "是否启用:0未启用,1启用")
    private byte inUse;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.DATETIME, fieldDesc = "注册时间")
    private String createTime;

    public String getDicId() {
        return dicId;
    }

    public String getDicName() {
        return dicName;
    }

    public String getDicDefault() {
        return dicDefault;
    }

    public byte getDicType() {
        return dicType;
    }

    public int getDicIndex() {
        return dicIndex;
    }

    public String getDicDesc() {
        return dicDesc;
    }

    public byte getInUse() {
        return inUse;
    }

    public String getCreateTime() {
        return createTime;
    }

    @Override
    public String getKeyValue() {
        return this.dicId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(8, 1);
        map.put("dicId", this.dicId);
        map.put("dicName", this.dicName);
        map.put("dicDefault", this.dicDefault);
        map.put("dicType", Byte.toString(this.dicType));
        map.put("dicIndex", Integer.toString(this.dicIndex));
        map.put("dicDesc", this.dicDesc);
        map.put("inUse", Byte.toString(this.inUse));
        map.put("createTime", this.createTime);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.dicId = entityMap.get("dicId");
        this.dicName = entityMap.get("dicName");
        this.dicDefault = entityMap.get("dicDefault");
        this.dicType = Byte.parseByte(entityMap.get("dicType"));
        this.dicIndex = Integer.parseInt(entityMap.get("dicType"));
        this.dicDesc = entityMap.get("dicDesc");
        this.inUse = Byte.parseByte(entityMap.get("inUse"));
        this.createTime = entityMap.get("createTime");
    }
}
