package com.scu.ztz.yierschedulerexecutor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.scu.ztz.yierschedulerutils")
public class YierSchedulerExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(YierSchedulerExecutorApplication.class, args);
	}
}
