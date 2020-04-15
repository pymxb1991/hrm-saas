package com.ihrm.domain.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Department)实体类
 */
@Entity
@Table(name = "co_department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {
    private static final long serialVersionUID = -9084332495284489553L;
    //ID
    @Id
    @Column(name = "id")
    private String id;
    /**
     * 父级ID
     */
    @Column(name = "parent_id")
    private String pid;
    /**
     * 企业ID
     */
    @Column(name = "company_id")
    private String companyId;
    /**
     * 部门名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 部门编码，同级部门不可重复
     */
    @Column(name = "code")
    private String code;

    /**
     * 负责人ID
     */
    @Column(name = "manager_id")
    private String managerId;
    /**
	*  负责人名称
	*/
    @Column(name = "manager")
    private String manager;
    /**
     * 城市
     */
    @Column(name = "city")
    private String city;

    /**
     * 介绍
     */
    @Column(name = "introduce")
    private String introduce;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
