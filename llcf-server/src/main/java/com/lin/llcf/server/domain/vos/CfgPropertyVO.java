package com.lin.llcf.server.domain.vos;

import com.lin.llcf.common.enums.CfgPropertiesValueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CfgPropertyVO {

    @NotNull
    private Long projectId;

    @NotNull
    private Long moduleId;

    @NotEmpty
    private String propertyKey;

    @NotEmpty
    private String propertyName;

    private String propertyDesc;

    @Valid
    private List<CfgPropertyItemVO> itemList;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CfgPropertyItemVO {

        @NotNull
        private CfgPropertiesValueType cfgType;

        @NotEmpty
        private String propertyKey;

        @NotEmpty
        private String propertyValue;

        @NotEmpty
        private String propertyValueDesc;

    }

}
