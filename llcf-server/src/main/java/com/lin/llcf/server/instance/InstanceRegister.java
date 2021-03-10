package com.lin.llcf.server.instance;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class InstanceRegister implements ClientRegistry {

    protected ConcurrentHashMap<String, CopyOnWriteArrayList<ClientInstance>> instanceMap = new ConcurrentHashMap<>();

    @Override
    public void register(String appKey, String host, int post) {
        ClientInstance instance = new ClientInstance();
        instance.setAppKey(appKey);
        instance.setHost(host);
        instance.setPort(post);
        instance.setExpired(false);
        instance.setValidatedTimestamp(System.currentTimeMillis());

        initInstanceListIf(appKey).add(instance);
    }

    protected List<ClientInstance> getClients(String appKey) {
        return instanceMap.getOrDefault(appKey, new CopyOnWriteArrayList<>());
    }

    private CopyOnWriteArrayList<ClientInstance> initInstanceListIf(String appKey) {
        CopyOnWriteArrayList<ClientInstance> instanceList = instanceMap.get(appKey);
        if (instanceList == null) {
            synchronized (appKey){
                instanceList = instanceMap.get(appKey);
                if (instanceList == null) {
                    instanceList = new CopyOnWriteArrayList<>();
                    instanceMap.put(appKey, instanceList);
                }
            }
        }
        return instanceList;
    }

}
