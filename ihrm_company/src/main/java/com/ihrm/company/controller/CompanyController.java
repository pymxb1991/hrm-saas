package com.ihrm.company.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//解决跨域问题
@CrossOrigin
@RestController
@RequestMapping(value = "company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    //保存企业
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Result save(@RequestBody  Company company){
        companyService.add(company);
        return  new Result(ResultCode.SUCCESS);
    }

    //根据ID 更新企业
    /** 步骤
     *    1.方法
     *    2.请求参数
     *    3.响应
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,@RequestBody Company company){ //id 为地址的一部分，可以通过注解@PathVariable 从地址中获取id ,然后封装到一个属性上
        company.setId(id);
        companyService.update(company);
        return new Result(ResultCode.SUCCESS);
    }
    //根据ID 删除企业
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){//注解@PathVariable 也可指定获取的值
        companyService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
    //根据ID 查询企业
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value ="id") String id){
        Company company = companyService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(company);
        return result;

    }
    //查询全部企业
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result findAll(){
        List<Company> all = companyService.findAll();
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(all);
        return result;
    }
}
