package com.lin.llcf.example.property;

import com.lin.llcf.client.refresh.Refreshable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class ValuedPropertyBean implements Refreshable {

    @Value("${worldwide.settlement.toporg}")
    private String toporg;

    @Value("${worldwide.settlement.test.strlist}")
    private List<String> strlist;

    @Value("${worldwide.settlement.test.intlist}")
    private List<Integer> intlist;

    @PostConstruct
    public void init() {
        log.info("本地配置文件属性值:{}", toporg);
    }

    @Scheduled(fixedDelay = 1000)
    public void scan() {
        System.out.println("toporg字段取值:" + toporg);
        System.out.println("strlist字段取值:" + strlist);
        System.out.println("intlist字段取值:" + intlist);
    }

    @Override
    public void beforeRefresh() {
        log.info("刷新之前...");
    }

    @Override
    public void postRefresh() {
        log.info("刷新之后...");
    }
}
