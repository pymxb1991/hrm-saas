package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.system.Role;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/04/19
 */
//1、解决跨域问题
//2、声明resController
//3、设置父路径
@CrossOrigin
@RestController
@RequestMapping(value = "/sys")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private CompanyService companyService;
    /**
     *  添加角色
     * @param role
     * @return
     */
    @RequestMapping(value = "/role",method = RequestMethod.POST)
    public Result add(@RequestBody Role role){
        role.setCompanyId(companyId); //使用继承过父类中的属性
        roleService.save(role);
        return  Result.SUCCESS();//new Result(ResultCode.SUCCESS);
    }
    /**
     * 更新 role
     */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id" ) String id, @RequestBody Role role){
        role.setId(id);
        roleService.update(role);
        return Result.SUCCESS();//new Result(ResultCode.SUCCESS);
    }
    /**
     * 删除 role
     */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable(value = "id" ) String id){
        roleService.deleteById(id);
        return  Result.SUCCESS();//new Result(ResultCode.SUCCESS);
    }
    /**
     * 根据ID 查询 role
     */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id" ) String id){
        Role role = roleService.findById(id);
        Result result = new Result(ResultCode.SUCCESS,role);
        return result;
    }
    /**
     * 查询企业的角色列表
     */
    @RequestMapping(value = "/role/list",method = RequestMethod.GET)
    public Result findAll(){
        List<Role> roleList = roleService.findAll(companyId);
        return new Result(ResultCode.SUCCESS,roleList);
    }

    /**
     * 分页查询企业的角色列表
     * 指定企业的ID
     * 页码
     * 页大小  每页几条数据
     */
    @RequestMapping(value = "/role",method = RequestMethod.GET)
    public Result findAll(int page,int pagesize,Role role){

        Page<Role> pageRole = roleService.findByPage(companyId, page, pagesize);


        /**
         *  注意：
         *    pageUser.getTotalElements()   springData JPA 封装的总条数
         *    pageUser.getContent()   springData JPA 封装的列表
         */
        PageResult<Role> pageResult = new PageResult<>(pageRole.getTotalElements(),pageRole.getContent());

        return new Result(ResultCode.SUCCESS, pageResult);
    }



}