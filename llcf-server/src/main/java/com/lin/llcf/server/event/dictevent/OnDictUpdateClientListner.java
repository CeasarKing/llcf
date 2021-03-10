package com.lin.llcf.server.event.dictevent;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OnDictUpdateClientListner implements ApplicationListener<DictUpdateEvent> {

    @Async("taskExecutor")
    @Override
    public void onApplicationEvent(DictUpdateEvent dictUpdateEvent) {

    }

}
