package com.lin.llcf.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum CfgPropertiesValueType  implements LlcfEnum{

    INTEGER(1, "数值"),
    DECIMAL(2, "小数"),
    STRING(3, "文本字符串"),
    STR_ARRAY(5, "字符数组"),
    INT_ARRAY(6, "数值数组"),
    ;
    private final Integer id;
    private final String desc;

    public static CfgPropertiesValueType getById(Integer id) {
        return Arrays.stream(values()).filter(o -> Objects.equals(o.id, id)).findFirst().orElse(null);
    }

}
