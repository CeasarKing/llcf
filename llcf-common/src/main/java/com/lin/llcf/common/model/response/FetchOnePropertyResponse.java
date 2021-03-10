package com.lin.llcf.common.model.response;

import com.lin.llcf.common.model.dtos.PropertyDTO;
import lombok.Data;
import java.io.Serializable;

@Data
public class FetchOnePropertyResponse implements Serializable {

    private PropertyDTO property;

}
