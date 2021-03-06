package com.ihrm.common.interceptor;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 自定义拦截器
 *      继承HandlerInterceptorAdapter
 *
 *      preHandle:进入到控制器方法之前执行的内容
 *          boolean：
 *              true：可以继续执行控制器方法
 *              false：拦截
 *      posthandler：执行控制器方法之后执行的内容
 *      afterCompletion：响应结束之前执行的内容
 *
 * 1.简化获取token数据的代码编写
 *      统一的用户权限校验（是否登录）
 * 2.判断用户是否具有当前访问接口的权限
 *
 * @author Mr.Mao
 * @date 2020/05/02
 */
@Component
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;
    /**
     *  1、请求经过会先执行preHandle中方法；如果这个方法返回true,放行请求；如果为false ，则中断请求
     *
     * 简化获取token数据的代码编写（判断是否登录）
     *  1.通过request获取请求token信息
     *  2.从token中解析获取claims
     *  3.将claims绑定到request域中
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("====================前置拦截器====================");
        //1、通过拦截器获取到token 数据
        String authorization = request.getHeader("Authorization");
        //判断请求头信息是否为空，或者是否已Bearer开头
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")){
            //获取token数据；
            String token = authorization.replace("Bearer ","");
            //2、从token中解析获取claims
            //解析token
            Claims claims = jwtUtils.parseJwt(token);
            //3.将claims绑定到request域中
            if (claims != null) {
                //通过claims 获取到当前用户的可访问的API权限字符串
                String apis = (String)claims.get("apis"); //api-user-delete   point-user-delete
                //比对接口名称，也就是通过@RequestMapping  的 name 属性，指定的名称，此处验证需要用到，所以如果是此种方式接口必须都进行指定 name;

                //通过handle 获取方法注解，然后获取注解上的名称
                HandlerMethod  h = (HandlerMethod)handler;
                //获取接口上的reqeustmapping注解
                RequestMapping methodAnnotation = h.getMethodAnnotation(RequestMapping.class);//当前方法上是什么注解，此处用什么
                //获取当前请求接口中的name属性
                String name = methodAnnotation.name(); //通过注解，获取注解上的name 名称；
                //判断当前用户是否具有响应的请求权限
                if(apis.contains(name)){
                    request.setAttribute("user_claims",claims);
                    return  true;
                }else{
                    throw new CommonException(ResultCode.UNAUTHORISE);
                }
            }
        }
        throw new CommonException(ResultCode.UNAUTHENTICATED);
    }

    /**
     * 2、当controller 执行完成之后会进入这个方法执行postHandler 中内容；
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("====================POST拦截器====================");
    }

    /**
     * 3、响应完成之后最后执行方法
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("====================后置拦截器====================");
    }
}