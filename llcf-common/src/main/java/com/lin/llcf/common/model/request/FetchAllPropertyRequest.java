package com.lin.llcf.common.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FetchAllPropertyRequest implements Serializable {

    private String appKey;

}
