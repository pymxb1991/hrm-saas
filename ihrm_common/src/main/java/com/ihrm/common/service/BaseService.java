package com.ihrm.common.service;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BaseService<T> {

    /**
     *  用户构造查询条件
     *
     * @param companyId
     * @return
     */
    protected Specification<T> getSpec(String companyId){

        Specification<T> specification = new Specification() {
            /**
             *   用户构造查询条件
             * @param root   包含了所有的对象数据，属性 此处是根据对象属性的形式，来进行SQL语句的生成
             * @param query  一般不用
             * @param criteriaBuilder  构造查询条件
             *              注意：构造查询条件一般有两个方面：
             *                         1、根据对象中哪个属性去比较： 如：root.get("companyId").as(String.class)
             *                         2、这个属性叫什么呢？也就是比较的内容
             *                         也就是这种形式，比较的属性是什么呢？ companyId ；companyId在数据库中写法company_id
             *                         company_id = companyId
             * @return
             */
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                //根据企业ID 查询       equql 构造一个等于的语句
                //criteriaBuilder.like()
                //criteriaBuilder.not()
                return criteriaBuilder.equal(root.get("companyId").as(String.class),companyId);
            }
        };
        return  specification ;
    }
}
