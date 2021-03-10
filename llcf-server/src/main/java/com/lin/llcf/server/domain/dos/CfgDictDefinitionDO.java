package com.lin.llcf.server.domain.dos;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Proxy(lazy = false)
@Entity
@Table(name = "llcf_cfg_dict_definition")
@Data
public class CfgDictDefinitionDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "dict_key")
    private String dictKey;

    @Column(name = "dict_name")
    private String dictName;

    @Column(name = "dict_desc")
    private String dictDesc;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
