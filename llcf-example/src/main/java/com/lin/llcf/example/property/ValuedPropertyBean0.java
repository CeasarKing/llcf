package com.lin.llcf.example.property;

import com.lin.llcf.client.refresh.Refreshable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ValuedPropertyBean0 implements Refreshable {

    @Value("${worldwide.settlement.toporg}")
    private String toporg;

    @Value("${worldwide.settlement.version}")
    private Integer version;

    @PostConstruct
    public void init() {
        log.info("本地配置文件属性值:{}", toporg);
    }

    @Scheduled(fixedDelay = 1000)
    public void scan() {
        System.out.println("toporg字段取值:" + toporg);
        System.out.println("version字段取值:" + version);
    }

    @Override
    public void beforeRefresh() {
        log.info("刷新之前...");
    }

    @Override
    public void postRefresh() {
        log.info("刷新之后, toporg:{}, version:{}", toporg, version);
    }
}
