package com.lin.llcf.server.dao;


import com.lin.llcf.server.domain.dos.CfgPropertyDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CfgPropertyDao extends JpaRepository<CfgPropertyDO, Long> {

    List<CfgPropertyDO> getByDefinitionId(Long definitionId);

    CfgPropertyDO getByDefinitionIdAndPropertyKey(Long definitionId, String propertyKey);

    @Modifying
    @Query(value = "UPDATE llcf_cfg_property SET " +
            "property_key = :#{#cfgproperty.propertyKey}, " +
            "property_value = :#{#cfgproperty.propertyValue}, " +
            "property_value_desc = :#{#cfgproperty.propertyValueDesc}, " +
            "update_time = now() " +
            "WHERE id = :#{#cfgproperty.id}",
            nativeQuery = true)
    int updateById(CfgPropertyDO cfgproperty);

}
