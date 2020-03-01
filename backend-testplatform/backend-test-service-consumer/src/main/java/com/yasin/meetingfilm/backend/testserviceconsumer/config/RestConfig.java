package com.yasin.meetingfilm.backend.testserviceconsumer.config;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Yasin Zhang
 */
@Configuration
public class RestConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 负载均衡规则
     * RoundRobinRule - 轮询规则
     * RandomRule - 随机规则
     * AvailabilityFilteringRule - 可用过滤规则
     * WeightedResponseTimeRule - 根据平均响应时间计算所有服务的权重
     * RetryRule - 遵循RoundRobin规则处理，但是会对失败的服务进行重试
     * BestAvailableRule - 结合了可用过滤规则和响应时长规则
     * ZoneAvoidanceRule - 复合判断server所在区域性能和可用性选择服务器
     */
    @Bean
    public IRule iRule() {
        return new RoundRobinRule();
    }

    /**
     * IPing算法
     */
    @Bean
    public IPing iPing() {
        // return new PingUrl(false, "/abc");
        return new NIWSDiscoveryPing();
    }
}
