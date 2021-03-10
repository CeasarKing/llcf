package com.lin.llcf.client.refresh;

import java.lang.reflect.Field;
import java.util.Map;

public class RefreshValueMapHolder {

    private static final RefreshValueLocalCache LOCAL_CACHE = new RefreshValueLocalCache();

    public static void register(String key, Object currentValue, Object bean, Class type, Field targetField) {
        RefreshValueItem item = new RefreshValueItem(key, bean, type, targetField, currentValue);
        LOCAL_CACHE.append(key, item);
    }

    public static void refresh(Map<String, Object> properties) {
        LOCAL_CACHE.refreshProperty(properties);
    }

    public static Object getProperty(String key) {
        return LOCAL_CACHE.getProperty(key);
    }

}
