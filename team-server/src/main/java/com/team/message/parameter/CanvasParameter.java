package com.team.message.parameter;

import com.wolf.framework.service.parameter.FieldConfig;
import com.wolf.framework.service.parameter.ParametersConfig;
import com.wolf.framework.service.parameter.type.FieldTypeEnum;

/**
 * 画板指令
 *
 * @author zoe
 */
@ParametersConfig()
public class CanvasParameter {

    @FieldConfig(type = FieldTypeEnum.INT_SIGNED, fieldDesc = "x坐标")
    private int x;
    @FieldConfig(type = FieldTypeEnum.INT_SIGNED, fieldDesc = "y坐标")
    private int y;
}
