package com.lin.llcf.server.domain.dos;

import lombok.Data;
import org.hibernate.annotations.Proxy;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Proxy(lazy = false)
@Entity
@Table(name = "llcf_cfg_property_definition")
@Data
public class CfgPropertyDefinitionDO implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "property_key")
    private String propertyKey;

    @Column(name = "property_name")
    private String propertyName;

    @Column(name = "property_desc")
    private String propertyDesc;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
