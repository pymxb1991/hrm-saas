package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.system.Permission;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private PermissionService permissionService;

    /**
     *  添加权限
     * @param map
     * @return
     */
    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> map) throws Exception {
        permissionService.save(map);
        return  Result.SUCCESS();//new Result(ResultCode.SUCCESS);
    }
    /**
     * 更新 permission
     */
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id" ) String id, @RequestBody Map<String,Object> map) throws Exception{
        map.put("id",id);
        permissionService.update(map);
        return Result.SUCCESS();//new Result(ResultCode.SUCCESS);
    }
    /**
     * 删除 permission
     */
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable(value = "id" ) String id) throws CommonException {
        permissionService.deleteById(id);
        return  Result.SUCCESS();//new Result(ResultCode.SUCCESS);
    }
    /**
     * 根据ID 查询 permission
     */
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id" ) String id) throws CommonException {
        Map map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS,map);
    }
    /**
     * 查询企业的权限列表
     */
    @RequestMapping(value = "/permission/list",method = RequestMethod.GET)
    public Result findAll(@RequestParam Map<String,Object> map){
        List<Permission> list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS,list);
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