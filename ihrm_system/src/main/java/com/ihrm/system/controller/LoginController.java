package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/05/01
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/sys")
public class LoginController {

    @Autowired
    private UserService userService;


    @Autowired
    private JwtUtils jwtUtils;
    /**
     *  登陆
     * @param map
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,Object> map) throws Exception {
        String mobile = (String) map.get("mobile");
        String password = (String) map.get("password");
        User user = userService.findByMobile(mobile);
        if(user == null || !user.getPassword().equals(password)){
           // throw new CommonException(Result.FAIL());
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }else {
            //引用JWT 工具类生成token;需要先注入
            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
            return  new Result(ResultCode.SUCCESS,token); //Result.SUCCESS();//
        }

    }
}