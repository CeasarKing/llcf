package com.lin.llcf.server.dao;


import com.lin.llcf.server.domain.dos.CfgModuleDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfgModuleDao extends JpaRepository<CfgModuleDO, Long> {

    CfgModuleDO getByProjectIdAndModuleKey(Long projectId, String moduleKey);

    CfgModuleDO getByAppKey(String appKey);

}
