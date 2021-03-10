package com.lin.llcf.common.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class FetchDictResponse implements Serializable {

    private Map<String, Map<String, String>> dictMap;


}
