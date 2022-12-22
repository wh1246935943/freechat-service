package com.doudou.freechat;

import org.springframework.boot.SpringApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.doudou.freechat.dao")
public class FreeChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeChatApplication.class, args);
    }

}
