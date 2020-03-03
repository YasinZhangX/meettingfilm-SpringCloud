package com.yasin.meetingfilm.backend.test.hystrix.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author Yasin Zhang
 */
@Getter
@Setter
public class ObserveCommandDemo extends HystrixObservableCommand<String> {

    private String name;

    public ObserveCommandDemo(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ObserveCommand")));
        this.name = name;
    }

    @Override
    protected Observable<String> construct() {
        System.out.println("current TreadName: " + Thread.currentThread().getName());
        return Observable.create(new Observable.OnSubscribe<String>() {
            @SneakyThrows
            @Override
            public void call(Subscriber<? super String> subscriber) {
                // 业务处理
                subscriber.onNext("action 1, name=" + name);
                subscriber.onNext("action 2, name=" + name);
                subscriber.onNext("action 3, name=" + name);

                // 业务结束
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }
}
