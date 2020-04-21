package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.system.Permission;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/04/21
 */
//1、解决跨域问题
//2、声明resController
//3、设置父路径
@CrossOrigin
@RestController
@RequestMapping(value = "/sys")
public class PermissionController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private CompanyService companyService;
    /**
     *  添加权限
     * @param permission
     * @return
     */
    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public Result save(@RequestBody Permission permission){
        return  Result.SUCCESS();//new Result(ResultCode.SUCCESS);
    }
    /**
     * 更新 permission
     */
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id" ) String id, @RequestBody Permission permission){
        return Result.SUCCESS();//new Result(ResultCode.SUCCESS);
    }
    /**
     * 删除 permission
     */
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable(value = "id" ) String id){
        return  Result.SUCCESS();//new Result(ResultCode.SUCCESS);
    }
    /**
     * 根据ID 查询 permission
     */
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id" ) String id){

        return new Result(ResultCode.SUCCESS);
    }
    /**
     * 查询企业的权限列表
     */
    @RequestMapping(value = "/permission/list",method = RequestMethod.GET)
    public Result findAll(){

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 分页查询企业的权限列表
     * 指定企业的ID
     * 页码
     * 页大小  每页几条数据
     */
    @RequestMapping(value = "/permission",method = RequestMethod.GET)
    public Result findAll(int page,int pagesize,Permission permission){

        return new Result(ResultCode.SUCCESS);
    }


}