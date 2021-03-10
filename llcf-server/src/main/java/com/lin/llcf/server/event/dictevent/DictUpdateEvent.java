package com.lin.llcf.server.event.dictevent;

import com.lin.llcf.server.domain.vos.CfgDictVO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DictUpdateEvent extends ApplicationEvent {

    private final CfgDictVO cfgDictVo;

    public DictUpdateEvent(CfgDictVO source) {
        super(source);
        this.cfgDictVo = source;
    }
}
