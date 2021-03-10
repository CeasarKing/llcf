package com.lin.llcf.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMsgCode {

    SUCCESS(200, "成功"),




    ;
    private final Integer code;
    private final String desc;
}
