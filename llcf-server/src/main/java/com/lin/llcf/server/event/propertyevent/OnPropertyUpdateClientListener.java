package com.lin.llcf.server.event.propertyevent;

import com.lin.llcf.common.model.dtos.PropertyDTO;
import com.lin.llcf.server.domain.vos.CfgModuleVO;
import com.lin.llcf.server.domain.vos.CfgPropertyVO;
import com.lin.llcf.server.instance.ClientPushManager;
import com.lin.llcf.server.service.CfgModuleService;
import com.lin.llcf.server.service.CfgPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OnPropertyUpdateClientListener implements ApplicationListener<PropertyUpdateEvent> {

    @Autowired
    private CfgModuleService cfgModuleService;
    @Autowired
    private CfgPropertyService cfgPropertyService;

    @Async("taskExecutor")
    @Override
    public void onApplicationEvent(PropertyUpdateEvent propertyUpdateEvent) {
        log.info("某一个字典表更新了，更新客户端操作:{}", propertyUpdateEvent);
        CfgPropertyVO propertyVo = propertyUpdateEvent.getCfgPropertyVo();
        if (CollectionUtils.isEmpty(propertyVo.getItemList())) {
            return;
        }

        Long moduleId = propertyVo.getModuleId();
        CfgModuleVO moduleVo = cfgModuleService.getById(moduleId);
        if (moduleVo == null) {
            return;
        }

        String appKey = moduleVo.getAppKey();
        String parentKey = propertyVo.getPropertyKey();
        List<PropertyDTO> propertyList = new ArrayList<>();

        propertyVo.getItemList().forEach(itemVo -> {
            PropertyDTO property = cfgPropertyService.getProperty(propertyVo.getProjectId(), propertyVo.getModuleId(),
                    parentKey, itemVo.getPropertyKey());
            propertyList.add(property);
        });

        if (!CollectionUtils.isEmpty(propertyList)) {
            ClientPushManager.getSingleton().broadcastProperty(appKey, propertyList);
        }
    }

}
