package com.iceberg.rh.rhf.yqs.saas.shenyu;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo(scanBasePackages = "com.iceberg.rh.rhf.yqs.saas.shenyu.provider")
@SpringBootApplication
public class ShenyyDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShenyyDemoApplication.class, args);
    }

}
