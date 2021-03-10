package com.lin.llcf.client.http.handler;

import com.lin.llcf.client.refresh.RefreshValueMapHolder;
import com.lin.llcf.common.model.dtos.PropertyDTO;
import com.lin.llcf.common.model.request.BroadcastPropertyRequest;
import com.lin.llcf.common.utils.JsonUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertyRefreshHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (!StringUtils.endsWithIgnoreCase(method, "POST")) {
            return;
        }

        byte[] requestBytes = httpExchange.getRequestBody().readAllBytes();
        httpExchange.getRequestBody().close();

        String requestBody = new String(requestBytes, StandardCharsets.UTF_8);
        BroadcastPropertyRequest refreshProperty = JsonUtils.read(requestBody, BroadcastPropertyRequest.class);
        if (refreshProperty == null || CollectionUtils.isEmpty(refreshProperty.getPropertyList())) {
            return;
        }

        Map<String, Object> properties = refreshProperty.getPropertyList()
                .stream().collect(Collectors.toMap(PropertyDTO::getKey, PropertyDTO::getValue));

        RefreshValueMapHolder.refresh(properties);

        writeOk(httpExchange, "");
    }

    private void writeOk(HttpExchange httpExchange, Object refreshValue) throws IOException {
        String response= JsonUtils.writeToString(refreshValue);
        response = StringUtils.isBlank(response) ? "ok" : response;
        httpExchange.sendResponseHeaders(200, response.length());
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json; charset=utf8");
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

}
