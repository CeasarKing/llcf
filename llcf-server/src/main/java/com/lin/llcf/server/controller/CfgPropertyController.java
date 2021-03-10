package com.lin.llcf.server.web.controller;

import com.lin.llcf.server.domain.vos.CfgPropertyVO;
import com.lin.llcf.server.service.CfgPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cfg/property")
@RestController
public class CfgPropertyController{

    @Autowired
    private CfgPropertyService cfgPropertyService;

    @PostMapping("/update")
    public String update(@RequestBody CfgPropertyVO vo) {
        cfgPropertyService.update(vo);
        return "ok";
    }

}
