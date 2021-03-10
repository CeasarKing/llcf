package com.lin.llcf.server.domain.dos;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Proxy(lazy = false)
@Entity
@Table(name = "llcf_cfg_dict")
@Data
public class CfgDictDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "definition_id")
    private Long definitionId;

    @Column(name = "dict_id")
    private Integer dictId;

    @Column(name = "dict_code")
    private String dictCode;

    @Column(name = "dict_desc")
    private String dictDesc;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
