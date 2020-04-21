package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Permission;
import com.ihrm.system.dao.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/04/21
 */
@Service
public class PermissionService extends BaseService {
    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private IdWorker idWorker;
    /**
     * 保存权限
     */
    public void save(Permission permission){
        //设置主键值
        permission.setId(idWorker.nextId() + "");
        //调用Dao保存权限
        permissionDao.save(permission);
    }
    /**
     * 更新权限
     */
    public void update(Permission permission ){
        //1、根据ID 查询 权限
        Permission targer = permissionDao.findById(permission.getId()).get();
        /*  Permission targer = permissionDao.getOne(permission.getId());*/
        //2、设置权限属性
        targer.setName(permission.getName());
        targer.setDescription(permission.getDescription());
        //3、更新权限
        permissionDao.save(targer);
    }
    /**
     * 根据ID查询权限
     */
    public Permission findById(String id){
        return   permissionDao.findById(id).get();
    }
    /**
     * 查询全部列表
     */
    public List<Permission> findAll(String companyId){
        return permissionDao.findAll(getSpec(companyId));
    }
    /**
     * 分页查询
     */
    public Page<Permission> findByPage(String companyId, int page, int size){
        int pageNow = page-1;
        return  permissionDao.findAll(getSpec(companyId), PageRequest.of(pageNow,size));
    }
    /**
     * 根据ID 删除权限
     */
    public void deleteById(String id){
        permissionDao.deleteById(id);
    }
}