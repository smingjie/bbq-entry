package com.micro.bbqentry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动入口
 *
 * @author auto(system inital)
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.micro.bbqentry.repository"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
