package com.yasin.meetingfilm.backend.gateway.predicates;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @description 日期路由自定义
 * @author Yasin Zhang
 */
public class MyAfterRoutePredicateFactory extends AbstractRoutePredicateFactory<MyAfterRoutePredicateFactory.Config> {
    public static final String AFTER_KEY = "datetime";

    /**
     * 传入配置信息
     */
    public MyAfterRoutePredicateFactory() {
        super(MyAfterRoutePredicateFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(AFTER_KEY);
    }

    /**
     * 具体的路由匹配逻辑
     */
    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        System.out.println("AfterRoutePredicateFactory -> config : "+config);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long nowTime = System.currentTimeMillis();
        return (exchange) -> {
            // 解析出配置定义的时间
            long afterTime = 0;
            try {
                afterTime = format.parse(config.getDatetime()).getTime();
            } catch (ParseException e) {
                return false;
            }
            // 如果当前时间大于配置定义时间，则返回true
            return nowTime > afterTime;
        };
    }

    /**
     * 用于承载断言所需的参数
     * 我们这里是判断输入的时间与真实时间之间的先后关系
     */
    public static class Config {
        @NotEmpty
        private String datetime;

        public Config() {
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }
}
