package com.lin.llcf.client.autoconfig.configration;


import com.lin.llcf.client.http.cs.SimpleCallbackServer;
import org.springframework.context.annotation.Bean;

public class LlcfCallbackerServerConfiguration {

    @Bean(value = "callbackServer", initMethod = "init")
    public SimpleCallbackServer callbackServer() {
        return new SimpleCallbackServer();
    }

}
