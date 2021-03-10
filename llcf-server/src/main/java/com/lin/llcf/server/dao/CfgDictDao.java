package com.lin.llcf.server.dao;


import com.lin.llcf.server.domain.dos.CfgDictDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CfgDictDao extends JpaRepository<CfgDictDO, Long> {

    List<CfgDictDO> getByDefinitionId(Long definitionId);

    @Query(value = "select * from llcf_cfg_dict where definition_id in (?1)", nativeQuery = true)
    List<CfgDictDO> getByDefinitionIds(List<Long> definitionIds);

    @Modifying
    @Query(value = "UPDATE llcf_cfg_dict SET " +
            "dict_id = :#{#cfgdict.dictId}, " +
            "dict_code = :#{#cfgdict.dictCode}, " +
            "dict_desc = :#{#cfgdict.dictDesc}, " +
            "update_time = now() " +
            "WHERE id = :#{#cfgdict.id}",
            nativeQuery = true)
    int updateById(CfgDictDO cfgdict);
}
