package com.ihrm.company;

import com.ihrm.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

//1、配置springboot 的包扫描
@SpringBootApplication(scanBasePackages = "com.ihrm.company")
//2、配置JPA 注解的扫描
@EntityScan(value = "com.ihrm.domain.company")
public class CompanyApplication {
    /**
     * 启动方法配置
     */
    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class,args);
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
