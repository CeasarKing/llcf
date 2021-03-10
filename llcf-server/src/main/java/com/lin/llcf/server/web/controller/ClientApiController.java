package com.lin.llcf.server.web.controller;

import com.lin.llcf.common.model.request.FetchAllPropertyRequest;
import com.lin.llcf.common.model.request.FetchDictRequest;
import com.lin.llcf.common.model.request.FetchPropertyRequest;
import com.lin.llcf.common.model.request.RegisterRequest;
import com.lin.llcf.common.model.response.FetchAllPropertyResponse;
import com.lin.llcf.common.model.response.FetchDictResponse;
import com.lin.llcf.common.model.response.FetchOnePropertyResponse;
import com.lin.llcf.server.http.BizExecuteFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class ClientApiController {

    @Autowired
    private BizExecuteFacade bizExecuteFacade;

    @GetMapping("/beat")
    public String beat(){
        return "beat";
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        bizExecuteFacade.register(request);
        return "ok";
    }

    @PostMapping("/dict/fetch")
    public FetchDictResponse fetchDict(@RequestBody FetchDictRequest request) {
        return bizExecuteFacade.fetchDict(request);
    }

    @PostMapping("/property/fetch/one")
    public FetchOnePropertyResponse fetchProperty(@RequestBody FetchPropertyRequest request) {
        return bizExecuteFacade.fetchProperty(request);
    }

    @PostMapping("/property/fetch/all")
    public FetchAllPropertyResponse getPropertyList(@RequestBody FetchAllPropertyRequest request) {
        return bizExecuteFacade.fetchProperty(request);
    }

}
