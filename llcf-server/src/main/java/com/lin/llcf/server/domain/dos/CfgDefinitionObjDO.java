package com.lin.llcf.server.domain.dos;

import lombok.Data;
import org.hibernate.annotations.Proxy;
import javax.persistence.*;
import java.util.Date;

@Proxy(lazy = false)
@Entity
@Table(name = "llcf_cfg_definition_obj")
@Data
public class CfgDefinitionObjDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "obj_key")
    private String objKey;

    @Column(name = "obj_name")
    private String objName;

    @Column(name = "obj_desc")
    private String objDesc;

    @Column(name = "parent_definition_id")
    private Long parentDefinitionId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
