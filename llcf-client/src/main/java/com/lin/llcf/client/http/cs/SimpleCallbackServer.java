package com.lin.llcf.client.http.cs;

import com.lin.llcf.client.http.handler.PropertyRefreshHandler;
import com.lin.llcf.common.constants.RequestPathConstants;
import com.lin.llcf.common.model.request.RegisterRequest;
import com.lin.llcf.common.utils.HttpUtils;
import com.lin.llcf.common.utils.JsonUtils;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class SimpleCallbackServer {

    @Value("${llcf.app.key}")
    private String appKey;
    @Value("${llcf.env}")
    private String env;

    public void init() throws IOException {
        registerSelf(6999);
        start(6999);
    }

    public void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext(RequestPathConstants.BROADCAST_PROPERTY, new PropertyRefreshHandler());

        server.setExecutor(null);

        server.start();
    }

    public void registerSelf(int port) throws UnknownHostException {
        InetAddress address = Inet4Address.getLocalHost();
        String host = address.getHostAddress();
        RegisterRequest request = new RegisterRequest();
        request.setAppKey(appKey);
        request.setHost(host);
        request.setPort(port);
        request.setTimestamp(System.currentTimeMillis());

        String serverHost = HttpUtils.getServerUrl(env);
        String url = serverHost + RequestPathConstants.REGISTER;
        String resp = HttpUtils.post(url, JsonUtils.writeToString(request));
        System.out.println("register resp:" + resp);
    }


}
