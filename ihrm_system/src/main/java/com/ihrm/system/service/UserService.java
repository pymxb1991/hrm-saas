package com.ihrm.system.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/04/18
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;
    /**
     * 保存用户
     */
    public void save(User user){
        //设置主键值
        user.setId(idWorker.nextId() + "");
        user.setCreateTime(new Date());

        user.setPassword("123456");//初始密码
        user.setEnableState(1);//默认启用
        //调用Dao保存用户
        userDao.save(user);
    }
    /**
     * 更新用户
     */
    public void update(User user ){
        //1、根据ID 查询 用户
        User targer = userDao.findById(user.getId()).get();
        //2、设置用户属性
        targer.setUsername(user.getUsername());
        targer.setPassword(user.getPassword());
        targer.setDepartmentId(user.getDepartmentId());
        targer.setDepartmentName(user.getDepartmentName());
        //3、更新用户
        userDao.save(user);
    }
    /**
     * 根据ID查询用户
     */
    public User findById(String id){
        return   userDao.findById(id).get();
    }
    /**
     * 查询全部列表 带条件，分页
     *   参数：map 集合形式
     *     1、hasDept : 是否分配部门 0/1 未分配/分配
     *     2、departmentId： 部门ID
     *     3、companyId ： 公司ID
     *
     *     根据传过来的内容动态拼接Specification，同时构造分页
     */
    public Page  findAll(Map<String,Object> map,int page,int size){
        //1、查询条件


        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                //根据departmentId： 部门ID 构造查询条件
                if(!StringUtils.isEmpty(map.get("departmentId"))){
                    predicates.add(criteriaBuilder.equal(root.get("departmentId").as(String.class),map.get("departmentId")));
                }
                //根据 companyId：  构造查询条件
                if(!StringUtils.isEmpty(map.get("companyId"))){
                    predicates.add(criteriaBuilder.equal(root.get("companyId").as(String.class),map.get("companyId")));
                }
               if(!StringUtils.isEmpty(map.get("hasDept")) ){
                   //根据请求的hasDept判断 是否分配部门 0 未分配 departmentId = null  1 已分配  departmentId != null
                   if("0".equals((String) map.get("hasDept"))){
                       predicates.add(criteriaBuilder.isNull(root.get("departmentId")));
                   }else {
                       predicates.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                   }
               }

                //注意三个条件之间的关系是 与的关系

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        //  2、分页
        // 注意springdata jpa 中原生的便是支持分页查询的
       //1,查询条件，2，分页的相关设置
        Page pageUser = userDao.findAll(spec, new PageRequest(page-1,size));


        return pageUser;
    }
    /**
     * 根据ID 删除用户
     */
    public void deleteById(String id){
        userDao.deleteById(id);
    }

    /**
     * 分配角色
     */
    public void assignRoles(String userId,List<String> roleIds) {
        //1.根据id查询用户
        User user = userDao.findById(userId).get();
        //2.设置用户的角色集合
        Set<Role> roles = new HashSet<>();
        roleIds.forEach(role->{
            Role rolePo = roleDao.findById(role).get();
            roles.add(rolePo);
        });
        //设置用户和角色集合的关系
        user.setRoles(roles);
        //3.更新用户
        userDao.save(user);
    }
    /**
     * 根据mobile 查询用户
     */
    public User findByMobile(String mobile ){
        return userDao.findByMobile(mobile);
    }

}