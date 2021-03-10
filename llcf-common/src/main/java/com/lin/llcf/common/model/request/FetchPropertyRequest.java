package com.lin.llcf.common.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class FetchPropertyRequest implements Serializable {

    private Long projectId;

    private Long moduleId;

    private String propertyLine;

    private String propertyKey;

}
