package com.lin.llcf.server.service;

import com.lin.llcf.server.dao.CfgProjectDao;
import com.lin.llcf.server.domain.dos.CfgProjectDO;
import com.lin.llcf.server.domain.vos.CfgProjectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class CfgProjectService {

    @Autowired
    private CfgProjectDao cfgProjectDao;

    @Transactional(rollbackFor = Throwable.class)
    public void save(CfgProjectVO projectVo) {
        String projectKey = projectVo.getProjectKey();
        CfgProjectVO testExsit = getByKey(projectKey);
        if (testExsit != null) {
            throw new RuntimeException("项目已经存在，无法再插入" + projectKey);
        }
        CfgProjectDO projectDo = convertVoToDo(projectVo);
        cfgProjectDao.save(projectDo);
    }

    public CfgProjectVO getByKey(String key) {
        CfgProjectDO projectDo = cfgProjectDao.findByProjectKey(key).orElse(null);
        return convertDoToVo(projectDo);
    }

    public CfgProjectVO getById(Long projectId) {
        CfgProjectDO projectDo = cfgProjectDao.findById(projectId).orElse(null);
        return convertDoToVo(projectDo);
    }

    private CfgProjectDO convertVoToDo(CfgProjectVO projectVo) {
        if (projectVo == null) {
            return null;
        }
        CfgProjectDO projectDo = new CfgProjectDO();
        BeanUtils.copyProperties(projectVo, projectDo);
        projectDo.setCreateTime(new Date());
        projectDo.setUpdateTime(new Date());
        if (!StringUtils.hasText(projectDo.getProjectDesc())) {
            projectDo.setProjectDesc("");
        }
        return projectDo;
    }

    private CfgProjectVO convertDoToVo(CfgProjectDO projectDo) {
        if (projectDo == null) {
            return null;
        }
        CfgProjectVO projectVo = new CfgProjectVO();
        BeanUtils.copyProperties(projectDo, projectVo);
        return projectVo;
    }

}
