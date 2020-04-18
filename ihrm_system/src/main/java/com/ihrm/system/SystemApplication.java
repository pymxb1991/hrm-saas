package com.ihrm.system;

import com.ihrm.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * This is Description
 *
 * @author Mr.Mao
 * @date 2020/04/18
 */
//1、配置springboot 的包扫描
@SpringBootApplication(scanBasePackages = "com.ihrm")
//2、配置JPA 注解的扫描
@EntityScan(value = "com.ihrm.domain.system")
public class SystemApplication {
    /**
     * 启动方法配置
     */
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }

    /**
     * （初始化idwork ,用来自动生成ID）
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return  new IdWorker();
    }
}