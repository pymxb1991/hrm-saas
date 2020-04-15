package com.ihrm.company.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService extends BaseService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private IdWorker idWorker;
    /**
     * 保存部门
     */
    public void save(Department department){
        //设置主键值
        department.setId(idWorker.nextId() + "");
        //调用Dao保存部门
        departmentDao.save(department);
    }
    /**
     * 更新部门
     */
    public void update(Department department ){
        //1、根据ID 查询 部门
        Department dept = departmentDao.findById(department.getId()).get();
        //2、设置部门属性
        dept.setCode(department.getCode());
        dept.setIntroduce(department.getIntroduce());
        dept.setName(department.getName());
        //3、更新部门
        departmentDao.save(department);
    }
    /**
     * 根据ID查询部门
     */
    public Department findById(String id){
          return   departmentDao.findById(id).get();
    }
    /**
     * 查询全部列表
     */
    public List<Department> findAll(String companyId){
        /**
         * 用于构造查询条件
         *       1、只查询companyId下的部门;
         *       2、很多的地方都需要根据companyId 查询
         *       3、很多的对象中都具有companyId
         *        抽取出去
         *
         * 构造一个  Specification 查询条件
         *
         *      root : 包含了所有的对象属性，此处是根据对象属性的形式，来进行SQL语句的生成；
         *       cq  ：用来更加高级的查询 ，一般不用
         *       cb  ：用来构造查询条件，   =    like   in  not in  >= <=
         *                              equal  like  in   not in
         *
         *        这些语句跟SQL  很像，只不过是以面向对象的形式来构造的；

         */
       /* Specification<Department> spec = new Specification<Department>() {
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //根据企业ID 查询
                *//**
                 * equal  相当于构造一个等于或比较SQL语句
                 *    equal 参数：1、根对象的哪个属性比较(get),这个属性的类型是什么(as)；
                 *               2、值 叫什么，也就是传递过来的值
                 *    root ： 所有的对象属性
                 *//*
                return cb.equal(root.get("companyId").as(String.class),companyId);
            }
        };*/

        return departmentDao.findAll(getSpec(companyId));
    }
    /**
     * 根据ID 删除部门
     */
    public void deleteById(String id){
        departmentDao.deleteById(id);
    }

}
