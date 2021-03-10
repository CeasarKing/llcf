package com.lin.llcf.server.dao;

import com.lin.llcf.server.domain.dos.CfgDictDefinitionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CfgDictDefinitionDao extends JpaRepository<CfgDictDefinitionDO, Long> {

    List<CfgDictDefinitionDO> getByProjectIdAndModuleId(Long projectId, Long moduleId);

    CfgDictDefinitionDO getByProjectIdAndModuleIdAndDictKey(Long projectId, Long moduleId, String dictKey);

    @Query(value = "select * from llcf_cfg_dict_definition where project_id = ? and module_id = ? and dict_key = ? for update", nativeQuery = true)
    CfgDictDefinitionDO getAndLockByPidAndMid(Long projectId, Long moduleId, String dictKey);

    @Modifying
    @Query(value = "update llcf_cfg_dict_definition set " +
            "dict_key = :#{#cfgdict.dictKey}, " +
            "dict_name = :#{#cfgdict.dictName}, " +
            "dict_desc = :#{#cfgdict.dictDesc}, " +
            "update_time = now() " +
            "where id = :#{#cfgdict.id}", nativeQuery = true)
    int updateById(CfgDictDefinitionDO cfgdict);

}
