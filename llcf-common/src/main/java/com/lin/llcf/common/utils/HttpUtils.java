package com.lin.llcf.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author
 */
public class HttpUtils {

    public static String post(String url, String json) {
        CloseableHttpResponse response = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()){
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json;charset=utf8");
            post.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
            response = httpClient.execute(post);
            byte[] repBytes = response.getEntity().getContent().readAllBytes();
            return new String(repBytes, StandardCharsets.UTF_8);
        }catch (Exception e) {
            e.printStackTrace();;
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String getServerUrl(String env) {
        if (StringUtils.endsWithIgnoreCase(env, "prod")) {
            return "";
        }else {
            return "http://localhost:8080";
        }
    }

}
