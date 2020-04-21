package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.domain.system.Role;
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
public class RoleService extends BaseService {
    @Autowired
    private RoleDao roleDao;

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
}