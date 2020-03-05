package com.yasin.meetingfilm.backend.feignconf;

import feign.Contract;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yasin Zhang
 */
@Configuration
public class FeignHelloConfig {

    @Bean
    public Contract contract() {
        return new SpringMvcContract();
    }

}
