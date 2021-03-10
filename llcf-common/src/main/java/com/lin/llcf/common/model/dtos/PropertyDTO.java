package com.lin.llcf.common.model.dtos;

import com.lin.llcf.common.enums.CfgPropertiesValueType;
import lombok.Data;

@Data
public class PropertyDTO {

    private CfgPropertiesValueType valueType;

    private String key;

    private Object value;

    private String originValue;

}
