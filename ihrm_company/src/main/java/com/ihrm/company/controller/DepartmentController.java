package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//1、解决跨域问题
//2、声明resController
//3、设置父路径
@CrossOrigin
@RestController
@RequestMapping(value = "/company")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    /**
     *
     * @param department
     * @return
     */
    @RequestMapping(value = "/department",method = RequestMethod.POST)
    public Result save(@RequestBody  Department department){
        //注意，保存的是一个部门，部门应属于某一个企业，所以需要考虑不同企业间的隔离性；
        //1、设置保存企业ID
        //2、调用service 完成保存企业工作
        //3、构造返回结果
        /**
         * 构造企业ID
         */
        // String  companyId = "1";
        department.setCompanyId(companyId); //使用继承过父类中的属性
        departmentService.save(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询企业的部门列表
     * 指定企业的ID
     */
    @RequestMapping(value = "/department",method = RequestMethod.GET)
    public Result findAll(){
        /**
         * 构造企业ID
         */
       // String  companyId = "1";

        Company company = companyService.findById(companyId);//使用继承过父类中的属性
        List<Department> deptList = departmentService.findAll(companyId);
        /**
         * 构造返回公司下的部门返回数据结构
         *      由于构造时，缺少一个公司参数，所以直接从数据库中查询获取一个；
         */
        DeptListResult deptListResult = new DeptListResult(company,deptList);
        Result result = new Result(ResultCode.SUCCESS,deptListResult);
        return result;
    }
    /**
     * 根据ID 查询 department
     */
    @RequestMapping(value = "/department/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id" ) String id){
        Department department = departmentService.findById(id);
        Result result = new Result(ResultCode.SUCCESS,department);
        return result;
    }
    /**
     * 更新 department
     */
    @RequestMapping(value = "/department/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id" ) String id, @RequestBody Department department){
        // 1、 设置修改部门ID
        department.setId(id);
        //2、调用service 更新；
        departmentService.update(department);
        //Result result = new Result(ResultCode.SUCCESS);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 删除 department
     */
    @RequestMapping(value = "/department/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable(value = "id" ) String id){
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
