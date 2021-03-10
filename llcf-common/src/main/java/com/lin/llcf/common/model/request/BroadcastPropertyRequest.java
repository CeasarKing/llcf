package com.lin.llcf.common.model.request;

import com.lin.llcf.common.model.dtos.PropertyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BroadcastPropertyRequest {

    private List<PropertyDTO> propertyList;

    private long timestamp;

}
