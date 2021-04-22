package com.scu.ztz.yierschedulerutils;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 一个用于测试的类,不会实际启动
@MapperScan("com.scu.ztz.yierschedulerutils")
public class YierSchedulerUtilsApplication {
	public static void main(String[] args) {
		SpringApplication.run(YierSchedulerUtilsApplication.class, args);
	}
}