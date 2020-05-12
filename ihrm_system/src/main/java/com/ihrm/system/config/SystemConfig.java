package com.ihrm.system.config;

import com.ihrm.common.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * This is Description
 *    继承WebMvcConfigurationSupport  主要目的是 通过重写 addInterceptors 达到实现拦截目的；
 *
 * @author Mr.Mao
 * @date 2020/05/06
 */
@Configuration
public class SystemConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;


    /**
     * 添加拦截器的配置
     *
     * @param registry 拦截器注册对象
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //1.添加自定义拦截器
        registry.addInterceptor(jwtInterceptor).
                addPathPatterns("/**").//2.指定拦截器的url地址
                excludePathPatterns("/sys/login","/frame/register/**");//3.指定不拦截的url地址
    }
}
