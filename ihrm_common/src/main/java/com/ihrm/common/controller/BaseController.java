package com.ihrm.common.controller;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected String  companyId;

    protected String  companyName;

    protected Claims claims;

    @ModelAttribute// Spring MVC 中的注解，支持执行所有方法之前执行这部分代码
    public void setResAnReq(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;

        Object obj = request.getAttribute("user_claims");
        if (obj != null){
            this.claims = (Claims)obj;
            this.companyId = (String)claims.get("companyId");
            this.companyName = (String)claims.get("companyName");
/*            this.companyId = claims.getId();
            this.companyName = claims.getSubject();*/
        }else{
            this.companyId = "1";
            this.companyName = "传智播客";
        }

        //以后解决companyId
        /**
         * 目前使用 companyId = 1
         *         companyName = "传智播客"
         */
       /* this.companyId = "1";
        this.companyName = "传智播客";*/
    }
}
