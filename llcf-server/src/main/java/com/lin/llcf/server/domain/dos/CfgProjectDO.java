package com.lin.llcf.server.domain.dos;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Proxy(lazy = false)
@Entity
@Table(name = "llcf_project")
@Data
public class CfgProjectDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_key")
    private String projectKey;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_desc")
    private String projectDesc;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
