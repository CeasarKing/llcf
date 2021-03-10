package com.lin.llcf.server.service;

import com.lin.llcf.server.dao.CfgModuleDao;
import com.lin.llcf.server.domain.dos.CfgModuleDO;
import com.lin.llcf.server.domain.vos.CfgModuleVO;
import com.lin.llcf.server.domain.vos.CfgProjectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.UUID;

@Service
public class CfgModuleService {

    @Autowired
    private CfgModuleDao cfgModuleDao;
    @Autowired
    private CfgProjectService cfgProjectService;

    @Transactional(rollbackFor = Throwable.class)
    public void save(CfgModuleVO moduleVo) {
        CfgProjectVO projectVo = cfgProjectService.getById(moduleVo.getProjectId());
        if (projectVo == null) {
            throw new RuntimeException("项目中不存在，项目id:" + moduleVo.getProjectId());
        }
        CfgModuleDO module = cfgModuleDao.getByProjectIdAndModuleKey(moduleVo.getProjectId() , moduleVo.getModuleKey());
        if (module != null) {
            throw new RuntimeException("项目中的模块已经存在，无法再插入，项目id" + moduleVo.getProjectId() + ",模块key:" + moduleVo.getModuleKey());
        }
        CfgModuleDO moduleDo = convertVoToDo(moduleVo);
        String uuid = UUID.randomUUID().toString();
        moduleDo.setAppKey(uuid);
        cfgModuleDao.save(moduleDo);
    }

    public CfgModuleVO getById(Long id) {
        CfgModuleDO moduleDo = cfgModuleDao.findById(id).orElse(null);
        return convertDoToVo(moduleDo);
    }

    public CfgModuleVO getByAppKey(String appKey) {
        CfgModuleDO cfgModule = cfgModuleDao.getByAppKey(appKey);
        return convertDoToVo(cfgModule);
    }

    private CfgModuleDO convertVoToDo(CfgModuleVO vo) {
        CfgModuleDO moduleDo = new CfgModuleDO();
        BeanUtils.copyProperties(vo, moduleDo);
        moduleDo.setCreateTime(new Date());
        moduleDo.setUpdateTime(new Date());
        if (!StringUtils.hasText(moduleDo.getModuleDesc())) {
           moduleDo.setModuleDesc("");
        }
        return moduleDo;
    }

    private CfgModuleVO convertDoToVo(CfgModuleDO module) {
        if (module == null) {
            return null;
        }
        CfgModuleVO vo = new CfgModuleVO();
        BeanUtils.copyProperties(module, vo);
        return vo;
    }

}
