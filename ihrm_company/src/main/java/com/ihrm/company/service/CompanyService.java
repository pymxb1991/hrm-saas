package com.ihrm.company.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.CompanyDao;
import com.ihrm.domain.company.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyDao companyDao;

    /**
     * 保存企业
     *  1.配置idwork到工程   需要在启动的过程中以Bean方法.配置到工程中；（初始化idwork）
     *  2.在service中注入idwork
     *  3.通过idwork生成id
     *  4.保存企业
     */
    @Autowired
    private IdWorker idWorker;

    public void add(Company company){
        // company  前端传来时可能没有ID，所以需要进行基本配置

        //生成ID   配置idworker
         String id =  idWorker.nextId()+ "";
        company.setId(id);

        //默认状态设置  0 未审核；1：已经审核
        company.setAuditState("0");
        //默认激活状态  0 未激活；1：已经激活
        company.setState(0);
        company.setCreateTime(new Date());
        companyDao.save(company);
    }

    /**
     * 更新企业
     *  1.参数：Company
     *  2.根据id查询企业对象
     *  3.设置修改的属性
     *  4.调用dao完成更新
     */
    public void update(Company company){

        Company companyPo = companyDao.findById(company.getId()).get();
        BeanUtils.copyProperties(company,companyPo);
        companyPo.setAuditState("1");
        companyPo.setState(1);
        companyDao.save(companyPo);
    }

    //删除企业
    public void deleteById(String id){
        companyDao.deleteById(id);
    }
    //根据ID 查询企业
    public Company findById(String id){
        return companyDao.findById(id).get();
    }
    //查询全部企业列表
    public List<Company> findAll(){
        return companyDao.findAll();
    }
}
