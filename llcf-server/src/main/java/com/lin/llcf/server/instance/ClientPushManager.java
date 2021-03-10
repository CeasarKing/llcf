package com.lin.llcf.server.instance;


import com.lin.llcf.common.constants.RequestPathConstants;
import com.lin.llcf.common.model.dtos.PropertyDTO;
import com.lin.llcf.common.model.request.BroadcastPropertyRequest;
import com.lin.llcf.common.utils.HttpUtils;
import com.lin.llcf.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ClientPushManager extends InstanceRegister {

    private static final ClientPushManager SINGLETON = new ClientPushManager();

    private ClientPushManager() {}

    public static ClientPushManager getSingleton() {
        return SINGLETON;
    }

    public void broadcastProperty(String appKey, List<PropertyDTO> propertyList) {
        List<ClientInstance> instanceList = getClients(appKey);
        for (ClientInstance instance : instanceList) {
            broadcastProperty(instance, propertyList);
            log.info("通知客户端属性变更成功,host:{}, port:{}, data:{}",
                    instance.getHost(), instance.getPort(), propertyList);
        }
    }

    private void broadcastProperty(ClientInstance instance, List<PropertyDTO> propertyList) {
        String url = "http://" +instance.getHost() + ":" + instance.getPort() + RequestPathConstants.BROADCAST_PROPERTY;
        BroadcastPropertyRequest request = new BroadcastPropertyRequest(propertyList, System.currentTimeMillis());
        String json = JsonUtils.writeToString(request);
        HttpUtils.post(url, json);
    }

}
