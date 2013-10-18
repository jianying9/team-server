package com.team.message.parameter;

import com.wolf.framework.data.BasicTypeEnum;
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

    @ParameterConfig(basicTypeEnum = BasicTypeEnum.INT, desc = "x坐标")
    private int x;
    @ParameterConfig(basicTypeEnum = BasicTypeEnum.INT, desc = "y坐标")
    private int y;
}
