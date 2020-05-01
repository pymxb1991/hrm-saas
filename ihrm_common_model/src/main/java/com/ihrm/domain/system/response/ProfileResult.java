package com.ihrm.domain.system.response;

import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
public class ProfileResult {
  private String  mobile;
  private String  username;
  private String  company;

  private Map<String,Object> roles = new HashMap<>();

  /**
   * 构造方法，
   *    因为ProfileResult 成员变量中，所有数据在用户中都有，
   *    所以可以用用户来进行构造数据
   * @param user
   */
  public ProfileResult(User user) {
    this.mobile = user.getMobile();
    this.username = user.getUsername();
    this.company = user.getCompanyName();

    //构造返回集合
    Set<String> menus = new HashSet<>();
    Set<String> points = new HashSet<>();
    Set<String> apis = new HashSet<>();

    //直接构造用户权限相关内容
    //根据springdata jpa 特性，对象导航查询，查询出用户之后，直接从用户中获取即可；
    Set<Role> roles = user.getRoles();//用户所有角色信息
    roles.forEach(role -> {
      Set<Permission> permissions = role.getPermissions();//角色对应权限
      permissions.forEach(permission -> {
         //权限类型 1为菜单 2为功能 3为API
        String code = permission.getCode();
        if(permission.getType() == 1){
            menus.add(code);
          }else if(permission.getType() == 2){
            points.add(code);
          }else {
            apis.add(code);
          }
      });
    });

    this.roles.put("menus",menus);
    this.roles.put("points",points);
    this.roles.put("apis",apis);
  }
}
