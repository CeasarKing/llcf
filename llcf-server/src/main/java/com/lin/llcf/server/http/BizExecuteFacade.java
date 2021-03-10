package com.lin.llcf.server.http;

import com.lin.llcf.common.model.dtos.PropertyDTO;
import com.lin.llcf.common.model.request.FetchAllPropertyRequest;
import com.lin.llcf.common.model.request.FetchDictRequest;
import com.lin.llcf.common.model.request.FetchPropertyRequest;
import com.lin.llcf.common.model.request.RegisterRequest;
import com.lin.llcf.common.model.response.FetchAllPropertyResponse;
import com.lin.llcf.common.model.response.FetchDictResponse;
import com.lin.llcf.common.model.response.FetchOnePropertyResponse;
import com.lin.llcf.common.model.response.RegisterResponse;
import com.lin.llcf.server.instance.ClientPushManager;
import com.lin.llcf.server.service.CfgDictService;
import com.lin.llcf.server.service.CfgPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BizExecuteFacade {

    @Autowired
    private CfgDictService cfgDictService;
    @Autowired
    private CfgPropertyService cfgPropertyService;

    public String beat() {
        return "beat";
    }

    public FetchDictResponse fetchDict(FetchDictRequest request) {
        Map<String, Map<String, String>> dictMap = cfgDictService.fetchDictMap(request.getProjectId(), request.getModuleId());
        FetchDictResponse response = new FetchDictResponse();
        response.setDictMap(dictMap);
        return response;
    }

    public FetchOnePropertyResponse fetchProperty(FetchPropertyRequest request) {
        PropertyDTO property =  cfgPropertyService.getProperty(request.getProjectId(), request.getModuleId(), request.getPropertyLine(), request.getPropertyKey());
        FetchOnePropertyResponse response = new FetchOnePropertyResponse();
        response.setProperty(property);
        return response;
    }

    public FetchAllPropertyResponse fetchProperty(FetchAllPropertyRequest request) {
        List<PropertyDTO> property =  cfgPropertyService.getPropertyList(request.getAppKey());
        FetchAllPropertyResponse response = new FetchAllPropertyResponse();
        response.setPropertyList(property);
        return response;
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        ClientPushManager.getSingleton().register(registerRequest.getAppKey(),
                registerRequest.getHost(), registerRequest.getPort());
        return new RegisterResponse();
    }


}
