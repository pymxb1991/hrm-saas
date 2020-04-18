package com.ihrm.domain.system;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IDEA
 * Author:maoxb
 * Date:2020/4/18 10:24
 * Description: 权限实体类
 *
 * 需要注意的就是权限 跟按钮、菜单、API 都是一对一关系
 */
@Entity
@Table(name = "pe_permission")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert(true)
@DynamicUpdate(true)
public class Permission implements Serializable {
    private static final long serialVersionUID = -4990810027542971546L;
    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型 1为菜单 2为功能 3为API
     */
    private Integer type;
    /**
     * 权限标识
     */
    private String code;

    /**
     * 权限描述
     */
    private String description;
    /**
     * 一个权限
     *     可能是一个菜单   同时菜单下面可能有多个按钮
     *             菜单    同时菜单下面可能有多个API权限
     *             按钮    同时按钮下面可能有多个API权限
     *
     *   PID 则表示 属于哪个按钮，或是哪个菜单下面的API
     */
    private String pid;

    private Integer enVisible;

    public Permission(String name, Integer type, String code, String description) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.description = description;
    }


}