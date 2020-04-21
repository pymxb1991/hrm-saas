package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    /**
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Result save(@RequestBody User user){

        /**
         * 构造企业ID
         */
        // String  companyId = "1";
        user.setCompanyId(companyId); //使用继承过父类中的属性
        user.setCompanyName(companyName);
        userService.save(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询企业的用户列表
     * 指定企业的ID
     * 页码
     * 页大小  每页几条数据
     */
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public Result findAll(int page,int size,@RequestParam Map map){
        /**
         * 构造企业ID
         */
        map.put("companyId",companyId);
        Company company = companyService.findById(companyId);//使用继承过父类中的属性
        Page pageUser = userService.findAll(map, page, size);

        /**
         *  注意：
         *    pageUser.getTotalElements()   springData JPA 封装的总条数
         *    pageUser.getContent()   springData JPA 封装的列表
         */
        PageResult<User> pageResult = new PageResult<>(pageUser.getTotalElements(),pageUser.getContent());

        return new Result(ResultCode.SUCCESS, pageResult);
    }
    /**
     * 根据ID 查询 user
     */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id" ) String id){
        User user = userService.findById(id);
        Result result = new Result(ResultCode.SUCCESS,user);
        return result;
    }
    /**
     * 更新 user
     */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id" ) String id, @RequestBody User user){
        // 1、 设置修改用户ID
        user.setId(id);
        //2、调用service 更新；
        userService.update(user);
        //Result result = new Result(ResultCode.SUCCESS);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 删除 user
     */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable(value = "id" ) String id){
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}