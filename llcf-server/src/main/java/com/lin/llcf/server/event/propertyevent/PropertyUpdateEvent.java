package com.lin.llcf.server.event.propertyevent;

import com.lin.llcf.server.domain.vos.CfgPropertyVO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PropertyUpdateEvent extends ApplicationEvent {

    private final CfgPropertyVO cfgPropertyVo;

    public PropertyUpdateEvent(CfgPropertyVO source) {
        super(source);
        this.cfgPropertyVo = source;
    }
}
