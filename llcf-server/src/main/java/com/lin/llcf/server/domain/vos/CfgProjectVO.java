package com.lin.llcf.server.domain.vos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CfgProjectVO {

    private Long id;
    @NotEmpty(message = "项目键值不能为空")
    private String projectKey;
    @NotEmpty(message = "项目名称不能为空")
    private String projectName;

    private String projectDesc;

}
