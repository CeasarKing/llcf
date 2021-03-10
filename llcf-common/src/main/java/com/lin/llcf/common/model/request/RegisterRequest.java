package com.lin.llcf.common.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest implements Serializable {

    private String appKey;

    private String host;

    private int port;

    private long timestamp;

}
