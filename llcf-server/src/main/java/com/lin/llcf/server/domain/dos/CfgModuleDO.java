package com.lin.llcf.server.domain.dos;


import lombok.Data;
import org.hibernate.annotations.Proxy;
import javax.persistence.*;
import java.util.Date;


@Proxy(lazy = false)
@Entity
@Table(name = "llcf_module")
@Data
public class CfgModuleDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "module_key")
    private String moduleKey;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "module_desc")
    private String moduleDesc;

    @Column(name = "app_key")
    private String appKey;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
