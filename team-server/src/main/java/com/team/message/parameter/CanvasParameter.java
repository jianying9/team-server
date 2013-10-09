package com.team.message.parameter;

import com.wolf.framework.data.DataTypeEnum;
import com.wolf.framework.service.parameter.Parameter;
import com.wolf.framework.service.parameter.ParameterConfig;
import com.wolf.framework.service.parameter.ParametersConfig;

/**
 * 画板指令
 *
 * @author aladdin
 */
@ParametersConfig()
public class CanvasParameter implements Parameter{

    @ParameterConfig(dateTypeEnum = DataTypeEnum.INT, desc = "x坐标")
    private int x;
    @ParameterConfig(dateTypeEnum = DataTypeEnum.INT, desc = "y坐标")
    private int y;
}
