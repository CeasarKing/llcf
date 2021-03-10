package com.lin.llcf.common.model.response;

import com.lin.llcf.common.model.dtos.PropertyDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FetchAllPropertyResponse implements Serializable {

    private List<PropertyDTO> propertyList;

}
