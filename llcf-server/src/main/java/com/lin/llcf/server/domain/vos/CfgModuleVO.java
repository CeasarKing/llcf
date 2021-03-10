package com.lin.llcf.server.domain.vos;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CfgModuleVO {

    private Long id;
    @NotNull
    private Long projectId;
    @NotEmpty
    private String moduleKey;
    @NotEmpty
    private String moduleName;
    private String moduleDesc;
    private String appKey;

}
