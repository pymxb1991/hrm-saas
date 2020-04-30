package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/04/21
 */
@Service
public class RoleService extends BaseService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private IdWorker idWorker;
    /**
     * 保存角色
     */
    public void save(Role role){
        //设置主键值
        role.setId(idWorker.nextId() + "");
        //调用Dao保存角色
        roleDao.save(role);
    }
    /**
     * 更新角色
     */
    public void update(Role role ){
        //1、根据ID 查询 角色
       Role targer = roleDao.findById(role.getId()).get();
       /*  Role targer = roleDao.getOne(role.getId());*/
        //2、设置角色属性
        targer.setName(role.getName());
        targer.setDescription(role.getDescription());
        //3、更新角色
        roleDao.save(targer);
    }
    /**
     * 根据ID查询角色
     */
    public Role findById(String id){
        return   roleDao.findById(id).get();
    }
    /**
     * 查询全部列表
     */
    public List<Role> findAll(String companyId){
        return roleDao.findAll(getSpec(companyId));
    }
    /**
     * 分页查询
     */
    public Page<Role> findByPage(String companyId,int page,int size){
        int pageNow = page-1;
        return  roleDao.findAll(getSpec(companyId), PageRequest.of(pageNow,size));
    }
    /**
     * 根据ID 删除角色
     */
    public void deleteById(String id){
        roleDao.deleteById(id);
    }

    /**
     *   分配权限
     * @param roleId
     * @param permIds
     */
    public void assignPrem(String roleId, List<String> permIds) {
        //根据roleId 首先获取被分配的角色对象
        Role role = roleDao.findById(roleId).get();
        //构造权限集合
        Set<Permission> perms = new HashSet<>();
        permIds.forEach(permId->{
            Permission permission = permissionDao.findById(permId).get();
            /**
             *    注意：前端传递的只有菜单和按钮 （而菜单按钮的背后肯定会有API，所以需要考虑自动来划分API）
             *      example: 员工管理页面  --》 有一个按钮 -- 》 一个按钮对应了一个API  (这是一个父子关系)
             *      所以可以通过，当前按钮的ID作为父ID，从数据库中查询，父ID也就是API的父节点
             *     最终此处要查的就是父ID，下面的权限
             */
            //需要根据父id和类型查询API权限列表
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getId());
            perms.addAll(apiList);//自定赋予API权限
            perms.add(permission);//当前菜单或按钮的权限

        });
        //3.设置角色和权限的关系
        role.setPermissions(perms);
        roleDao.save(role);
    }
}