package com.yasin.meetingfilm.backend.test.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Yasin Zhang
 */
@Getter
@Setter
public class CommandDemo extends HystrixCommand<String> {

    private String name;

    public CommandDemo(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CommandHelloWorld"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("CommandDemoKey"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter()
                .withRequestCacheEnabled(false)  // 开启缓存
                // 线程池隔离还是信号量隔离
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD) // 请求缓存开关
//                .withExecutionIsolationSemaphoreMaxConcurrentRequests(2)  // 最大同时处理的信号量个数
//                .withFallbackIsolationSemaphoreMaxConcurrentRequests(3)   // 最大的同时失败个数
//                .withCircuitBreakerForceOpen(true) // 熔断器强制开启
                .withCircuitBreakerRequestVolumeThreshold(2)  // 熔断器请求阈值
                .withCircuitBreakerErrorThresholdPercentage(50) // 熔断器请求失败百分比
            )
//            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("MyThreadPool"))  // 线程池名字
//            .andThreadPoolPropertiesDefaults(
//                HystrixThreadPoolProperties.defaultSetter()
//                    .withCoreSize(2)  // 线程池核心大小
//                    .withMaximumSize(3)  // 线程池最大大小
//                    .withAllowMaximumSizeToDivergeFromCoreSize(true)  // 允许线程池从核心大小放大
//                    .withMaxQueueSize(2))
        );
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        String result = "CommandHelloWorld name: " + name;
        // Thread.sleep(800);
        if (name.startsWith("break")) {
            int i = 6 / 0;
        }
        System.out.println(result + ", currentThreadName: " + Thread.currentThread().getName());
        return result;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(name);
    }

    @Override
    protected String getFallback() {
        String result = "Fallback name:" + name;
        System.out.println(result + ", currentThreadName: " + Thread.currentThread().getName());
        return result;
    }
}
