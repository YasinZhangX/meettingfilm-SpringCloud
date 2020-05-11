package com.yasin.meetingfilm.backend.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Yasin Zhang
 */
@Slf4j
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long beginTime = System.currentTimeMillis();
        log.info("filter start ----> beginTime:{}", beginTime);
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            long endTime = System.currentTimeMillis();
            log.info("filter end ----> endTime{}, cost:{}",endTime , endTime-beginTime);
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
