package com.lin.llcf.server.event.propertyevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OnPropertyUpdateCacheListener implements ApplicationListener<PropertyUpdateEvent> {

    @Async("taskExecutor")
    @Override
    public void onApplicationEvent(PropertyUpdateEvent enumUpdateEvent) {
        log.info("某一个字典表更新了，更新缓存操作:{}", enumUpdateEvent);
    }



}
