package com.yasin.meetingfilm.backend.film;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "com.yasin.meetingfilm")
@MapperScan(basePackages = {"com.yasin.meetingfilm.backend.film.dao.mapper"})
public class BackendFilmApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendFilmApplication.class, args);
    }

}
