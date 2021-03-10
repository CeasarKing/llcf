package com.lin.llcf.server.dao;


import com.lin.llcf.server.domain.dos.CfgProjectDO;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CfgProjectDao extends JpaRepository<CfgProjectDO, Long> {

    Optional<CfgProjectDO> findByProjectKey(String projectKey);

}
