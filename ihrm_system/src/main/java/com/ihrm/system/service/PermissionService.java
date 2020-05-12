package com.ihrm.system.service;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.PermissionApi;
import com.ihrm.domain.system.PermissionMenu;
import com.ihrm.domain.system.PermissionPoint;
import com.ihrm.system.dao.PermissionApiDao;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.PermissionMenuDao;
import com.ihrm.system.dao.PermissionPointDao;
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
import javax.transaction.Transactional;
import java.util.*;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/04/21
 */
@Service
@Transactional
public class PermissionService extends BaseService {
    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionMenuDao permissionMenuDao;

    @Autowired
    private PermissionPointDao permissionPointDao;

    @Autowired
    private PermissionApiDao permissionApiDao;

    @Autowired
    private IdWorker idWorker;
    /**
     * 保存权限
     */
    public void save(Map<String,Object> map) throws Exception {
        //设置主键值
        String id = idWorker.nextId() + "";
        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);
        perm.setId(id);
        //根据不同类型type 构造不同的资源对象（菜单，按钮，api
        switch (perm.getType()){
            case PermissionConstants.PY_MENU:
                PermissionMenu permissionMenu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                permissionMenu.setId(id);
                permissionMenuDao.save(permissionMenu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint permissionPoint = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                permissionPoint.setId(id);
                permissionPointDao.save(permissionPoint);
                break;
            case PermissionConstants.PY_API:
                PermissionApi permissionApi = BeanMapUtils.mapToBean(map, PermissionApi.class);
                permissionApi.setId(id);
                permissionApiDao.save(permissionApi);
                break;
            default:
                //当异常抛出，返回错误代码
                throw  new CommonException(ResultCode.FAIL);
        }
        //调用Dao保存权限
        permissionDao.save(perm);
    }
    /**
     * 更新权限
     */
    public void update(Map<String,Object> map ) throws Exception {
        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);
        //1、根据ID 查询 权限
        Permission targer = permissionDao.findById(perm.getId()).get();
        targer.setName(perm.getName());
        targer.setCode(perm.getCode());
        targer.setDescription(perm.getDescription());
        targer.setEnVisible(perm.getEnVisible());
        /*  Permission targer = permissionDao.getOne(permission.getId());*/
        //根据不同类型type 构造不同的资源对象（菜单，按钮，api
        switch (perm.getType()){
            case PermissionConstants.PY_MENU:
                PermissionMenu permissionMenu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                permissionMenuDao.save(permissionMenu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint permissionPoint = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                permissionPointDao.save(permissionPoint);
                break;
            case PermissionConstants.PY_API:
                PermissionApi permissionApi = BeanMapUtils.mapToBean(map, PermissionApi.class);
                permissionApiDao.save(permissionApi);
                break;
            default:
                //当异常抛出，返回错误代码
                throw  new CommonException(ResultCode.FAIL);
        }
        //3、更新权限
        permissionDao.save(targer);
    }
    /**
     * 根据id查询
     *      //1.查询权限
     *      //2.根据权限的类型查询资源
     *      //3.构造map集合
     */
    public Map<String, Object> findById(String id) throws CommonException {
        Permission perm = permissionDao.findById(id).get();
        Integer type = perm.getType();

        Object object = null;
        if(type == PermissionConstants.PY_MENU){
            object = permissionMenuDao.findById(id).get();
        }else if(type == PermissionConstants.PY_POINT){
            object = permissionPointDao.findById(id);
                  //  .get();
        }else if(type == PermissionConstants.PY_API){
            object = permissionApiDao.findById(id).get();
        }else{
            throw new CommonException(ResultCode.FAIL);
        }
        Map<String, Object> map = BeanMapUtils.beanToMap(object);
        map.put("name",perm.getName());
        map.put("type",perm.getType());
        map.put("code",perm.getCode());
        map.put("description",perm.getDescription());
        map.put("pid",perm.getPid());
        map.put("enVisible",perm.getEnVisible());

        return map ;
    }
    /**
     * 查询全部
     * type      : 查询全部权限列表type：0：菜单 + 按钮（权限点） 1：菜单2：按钮（权限点）3：API接口
     * enVisible : 0：查询所有saas平台的最高权限，1：查询企业的权限
     * pid ：父id
     */
    public List<Permission> findAll( Map<String ,Object> map){

        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                //根据enVisible查询
                if (!StringUtils.isEmpty(map.get("enVisible"))){
                    predicates.add(criteriaBuilder.equal(root.get("enVisible").as(String.class),(String)map.get("enVisible")));
                }
                //根据父id查询
                if (!StringUtils.isEmpty(map.get("pid"))){
                    predicates.add(criteriaBuilder.equal(root.get("pid").as(String.class),(String)map.get("pid")));
                }
                //根据类型 type
                if(!StringUtils.isEmpty(map.get("type"))){
                    String type = (String) map.get("type");
                    //构造in 条件
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                    if("0".equals(type)){
                        in.value(1).value(2);
                    }else {
                        in.value(Integer.parseInt(type));
                    }
                }
                //and 条件关联起来  观注拼接形式
                int size = predicates.size();
                Predicate[] predicates1 = new Predicate[size];
                Predicate[] predicates2 = predicates.toArray(predicates1);


                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

            return permissionDao.findAll(spec);
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
     *       1.删除权限
     *       2.删除权限对应的资源
     */
    public void deleteById(String id) throws CommonException {
        //1.通过传递的权限id查询权限
        Permission permission = permissionDao.findById(id).get();
        Integer type = permission.getType();
        //2.根据类型构造不同的资源
        switch (type){
            case PermissionConstants.PY_MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.PY_POINT:
                permissionPointDao.deleteById(id);
                break;
            case PermissionConstants.PY_API:
                permissionApiDao.deleteById(id);
                break;
            default:
                //当异常抛出，返回错误代码
                throw  new CommonException(ResultCode.FAIL);
        }
    }
}