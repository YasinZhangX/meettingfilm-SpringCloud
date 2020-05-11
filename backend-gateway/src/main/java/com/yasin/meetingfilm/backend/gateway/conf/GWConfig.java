package com.yasin.meetingfilm.backend.gateway.conf;

import com.yasin.meetingfilm.backend.gateway.predicates.MyAfterRoutePredicateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Gateway 配置文件
 * @author Yasin Zhang
 */
@Configuration
public class GWConfig {

    @Bean
    public MyAfterRoutePredicateFactory initAfterRoutePredicateFactory() {
        return new MyAfterRoutePredicateFactory();
    }

}
