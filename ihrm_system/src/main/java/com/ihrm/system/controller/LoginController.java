package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.system.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
     *  利用jwt 进行登陆验证；
     * @param map
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,Object> map) throws Exception {
        String mobile = (String) map.get("mobile");
        String password = (String) map.get("password");
        User user = userService.findByMobile(mobile);
        //未找到用户
        if(user == null || !user.getPassword().equals(password)){
           // throw new CommonException(Result.FAIL());
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }else {
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("companyId",user.getCompanyId());
            paramMap.put("companyName",user.getCompanyName());
            //引用JWT 工具类生成token;需要先注入
            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), paramMap);
            return  new Result(ResultCode.SUCCESS,token); //Result.SUCCESS();//
        }

    }
    /**
     *  用户登陆成功之后，获取用户信息
     *   1、获取用户ID
     *   2、根据用户ID查询用户
     *   3、构建返回值对象
     *   4、响应
     *
     *
     *   前后端约定：前端请求微服务时需要添加头信息Authorization ,内容为Bearer+空格+token
     * @return
     */
    @RequestMapping(value = "/profille",method = RequestMethod.POST)
    public Result profille(HttpServletRequest request) throws Exception {
        //首先搞一个固定的；
       // String userId = "1063705482939731968";
        /**
         * 从请求头中获取 token 数据
         *      1、 获取请求头信息：  名称：Authorization
         *      2、 替换Bearer+空格
         *      3、 解析token
         *      4、 获取clamis
         *
         */
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)){
            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }
        String token = authorization.replace("Bearer ","");
        //解析token
        Claims claims = jwtUtils.parseJwt(token);
        //注意此处报错，跟踪发现Claims 中没有userId ,此原因是开始工具类生成token 时 直接.setClaims(map) 把userId给覆盖了，
        //所以此处需要对工具类，生成token进行修改，不能直接把map，直接set进去；需要遍历进行设置

        String userId = claims.getId();

        User user = userService.findById(userId);
        //通过构造方法，把用户信息直接构造返回；
        ProfileResult profileResult = new ProfileResult(user);
        return new Result(ResultCode.SUCCESS,profileResult);
    }
}