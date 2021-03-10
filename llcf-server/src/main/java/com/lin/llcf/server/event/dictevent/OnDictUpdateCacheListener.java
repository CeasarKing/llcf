package com.lin.llcf.server.event.dictevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OnDictUpdateCacheListener implements ApplicationListener<DictUpdateEvent> {

    @Async("taskExecutor")
    @Override
    public void onApplicationEvent(DictUpdateEvent dictUpdateEvent) {
        log.info("某一个枚举表更新了，请做相关操作:{}", dictUpdateEvent);
    }

}
