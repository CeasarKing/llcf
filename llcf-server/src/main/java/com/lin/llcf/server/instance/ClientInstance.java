package com.lin.llcf.server.instance;

import lombok.Data;

@Data
public class ClientInstance {

    private String appKey;

    private String host;

    private int port;

    private long validatedTimestamp;

    private boolean expired;

}
