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
 * 数据元素
 *
 * @author aladdin
 */
@DaoConfig(
tableName = "Element",
useCache = true)
@ParametersConfig()
public class ElementEntity extends Entity {

    @Key()
    @FieldConfig(type = FieldTypeEnum.UUID, fieldDesc = "元素ID")
    private String eleId;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.INT_SIGNED, fieldDesc = "元素值")
    private int eleValue;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.CHAR32, fieldDesc = "元素名称")
    private String eleName;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.BIG_INT_SIGNED, fieldDesc = "父元素ID")
    private long parentEleId;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.BIG_INT_SIGNED, fieldDesc = "字典ID")
    private long dicId;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.BIG_INT_SIGNED, fieldDesc = "排序号")
    private long sortId;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.TINY_INT_SIGNED, fieldDesc = "是否启用:0未启用,1启用")
    private byte inUse;
    //
    @Column()
    @FieldConfig(type = FieldTypeEnum.DATETIME, fieldDesc = "注册时间")
    private String createTime;

    public String getEleId() {
        return eleId;
    }

    public int getEleValue() {
        return eleValue;
    }

    public String getEleName() {
        return eleName;
    }

    public long getParentEleId() {
        return parentEleId;
    }

    public long getSortId() {
        return sortId;
    }

    public long getDicId() {
        return dicId;
    }

    public byte getInUse() {
        return inUse;
    }

    public String getCreateTime() {
        return createTime;
    }

    @Override
    public String getKeyValue() {
        return this.eleId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(8, 1);
        map.put("eleId", this.eleId);
        map.put("eleValue", Integer.toString(this.eleValue));
        map.put("eleName", this.eleName);
        map.put("parentEleId", Long.toString(this.parentEleId));
        map.put("dicId", Long.toString(this.dicId));
        map.put("sortId", Long.toString(this.sortId));
        map.put("inUse", Byte.toString(this.inUse));
        map.put("createTime", this.createTime);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.eleId = entityMap.get("eleId");
        this.eleValue = Integer.parseInt(entityMap.get("eleValue"));
        this.eleName = entityMap.get("eleName");
        this.parentEleId = Long.parseLong(entityMap.get("parentEleId"));
        this.dicId = Long.parseLong(entityMap.get("dicId"));
        this.sortId = Long.parseLong(entityMap.get("sortId"));
        this.inUse = Byte.parseByte(entityMap.get("inUse"));
        this.createTime = entityMap.get("createTime");
    }
}
