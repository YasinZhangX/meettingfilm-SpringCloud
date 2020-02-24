package com.yasin.meetingfilm.backend.testserviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BackendTestServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendTestServiceConsumerApplication.class, args);
    }

}
