package com.cs.comment0719;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.cs.comment0719.mapper")
@SpringBootApplication
public class Comment0719Application {

    public static void main(String[] args) {
        SpringApplication.run(Comment0719Application.class, args);
    }

}
