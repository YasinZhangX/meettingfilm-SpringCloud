package com.yasin.meetingfilm.backend.zuul.config;

import com.yasin.meetingfilm.backend.zuul.filters.MyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yasin Zhang
 */
@Configuration
public class ZuulConfig {

    @Bean
    public MyFilter initMyFilter() {
        return new MyFilter();
    }

}
