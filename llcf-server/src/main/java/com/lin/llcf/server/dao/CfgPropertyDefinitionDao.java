package com.lin.llcf.server.dao;

import com.lin.llcf.server.domain.dos.CfgPropertyDefinitionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CfgPropertyDefinitionDao extends JpaRepository<CfgPropertyDefinitionDO, Long> {

    CfgPropertyDefinitionDO getByProjectIdAndModuleIdAndPropertyKey(Long projectId, Long moduleId, String propertyKey);

    List<CfgPropertyDefinitionDO> getByProjectIdAndModuleId(Long projectId, Long moduleId);

    @Query(value = "select * from llcf_cfg_property_definition where project_id = ? and module_id = ? and property_key = ? for update", nativeQuery = true)
    CfgPropertyDefinitionDO getAndLockByPidAndMid(Long projectId, Long moduleId, String propertyKey);

    @Modifying
    @Query(value = "update llcf_cfg_property_definition set " +
            "property_key = :#{#cfgproperty.propertyKey}, " +
            "property_name = :#{#cfgproperty.propertyName}, " +
            "property_desc = :#{#cfgproperty.propertyDesc}, " +
            "update_time = now() " +
            "where id = :#{#cfgproperty.id}", nativeQuery = true)
    int updateById(CfgPropertyDefinitionDO cfgproperty);

}
