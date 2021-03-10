package com.lin.llcf.server.domain.dos;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Proxy(lazy = false)
@Entity
@Table(name = "llcf_cfg_definition_obj_field")
@Data
public class CfgDefinitionObjFieldDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "obj_id")
    private Long objId;

    @Column(name = "field_key")
    private Long fieldKey;

    @Column(name = "field_name")
    private Long fieldName;

    @Column(name = "field_desc")
    private Long fieldDesc;

    @Column(name = "field_type")
    private Integer fieldType;

    @Column(name = "value_range_rel_type")
    private Integer valueRangeRelType;

    @Column(name = "value_range_definition_id")
    private Long valueRangeDefinitionId;

    @Column(name = "value_range_values")
    private String valueRangeValues;

    @Column(name = "input_type")
    private Integer inputType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
