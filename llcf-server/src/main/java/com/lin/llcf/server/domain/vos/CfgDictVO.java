package com.lin.llcf.server.domain.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CfgDictVO {

    private Long id;

    @NotNull
    private Long projectId;

    @NotNull
    private Long moduleId;

    @NotEmpty
    private String dictKey;

    @NotEmpty
    private String dictName;

    private String dictDesc;

    @Valid
    private List<CfgDictItemVO> itemList;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CfgDictItemVO {

        @NotNull
        private Integer dictId;

        @NotEmpty
        private String dictCode;

        @NotEmpty
        private String dictDesc;

    }
}
