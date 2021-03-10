package com.lin.llcf.client.refresh;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RefreshValueLocalCache {

    private final ConcurrentHashMap<String, List<RefreshValueItem>> cacheMap = new ConcurrentHashMap<>();

    public void append(String key, RefreshValueItem item) {
        synchronized (key) {
            List<RefreshValueItem> itemList = cacheMap.computeIfAbsent(key, k -> new ArrayList<>());
            itemList.add(item);
        }
    }

    public void refreshProperty(Map<String, Object> properties) {
        Multimap<Object, RefreshValueItem> multimap = ArrayListMultimap.create();
        properties.forEach((key, newValue) -> {
            List<RefreshValueItem> itemList = cacheMap.get(key);
            if (CollectionUtils.isEmpty(itemList)) {
                return;
            }
            for (RefreshValueItem item : itemList) {
                multimap.put(item.getBean(), item);
            }
        });

        multimap.asMap().forEach((bean, itemList) -> {
            synchronized (bean) {
                if (bean instanceof Refreshable) {
                    ((Refreshable) bean).beforeRefresh();
                }
                for (RefreshValueItem valueItem : itemList) {
                    Object value = properties.get(valueItem.getKey());
                    if (value == null || Objects.equals(valueItem.getCurrentValue(), value)) {
                        continue;
                    }
                    if (valueItem.getCurrentValue() != null) {
                        if (value.getClass() != valueItem.getCurrentValue().getClass()) {
                            // error
                            continue;
                        }
                    }

                    refreshItem(value, valueItem);
                }
                if (bean instanceof Refreshable) {
                    ((Refreshable) bean).postRefresh();
                }
            }
        });
    }

    private void refreshItem(Object value, RefreshValueItem valueItem) {
        value = tryConvertValue(value, valueItem);
        try {
            Object bean = valueItem.getBean();
            valueItem.getTargetField().set(bean, value);
            valueItem.setCurrentValue(value);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object tryConvertValue(Object value, RefreshValueItem valueItem) {
        Class targetType = valueItem.getTargetField().getType();
        try {
            if (value.getClass() != targetType) {
                PropertyEditor pe = PropertyEditorManager.findEditor(targetType);
                if (value instanceof String && pe != null) {
                    pe.setAsText((String) value);
                    value = pe.getValue();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public Object getProperty(String key) {
        Object ret = null;
        synchronized (key) {
            List<RefreshValueItem> itemList = cacheMap.get(key);
            for (RefreshValueItem item : itemList) {
                Object thisObj = null;
                Object bean = item.getBean();
                item.getTargetField().setAccessible(true);
                try {
                    thisObj = item.getTargetField().get(bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (ret == null) {
                    ret = thisObj;
                }else if (!Objects.equals(ret, thisObj)){
                    throw new RuntimeException("刷新本地属性失败...");
                }
            }
        }
        return ret;
    }

}
