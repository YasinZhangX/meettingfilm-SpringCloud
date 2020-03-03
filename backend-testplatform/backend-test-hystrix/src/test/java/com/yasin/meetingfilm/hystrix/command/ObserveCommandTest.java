package com.yasin.meetingfilm.hystrix.command;

import com.yasin.meetingfilm.backend.test.hystrix.command.ObserveCommandDemo;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Yasin Zhang
 */
public class ObserveCommandTest {

    @Test
    public void observeTest() throws InterruptedException {
        long beginTime = System.currentTimeMillis();

        ObserveCommandDemo commandDemo = new ObserveCommandDemo("ObserveCommandDemo-observe");

        Observable<String> observable = commandDemo.observe();
        //非阻塞式调用
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("ObserveCommandDemo onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("ObserveCommandDemo onError, throw " + throwable);
            }

            @Override
            public void onNext(String s) {
                System.out.println("ObserveCommandDemo onNext result: " + s);
                long endTime = System.currentTimeMillis();
                System.out.println("spend time: " + (endTime - beginTime));
            }
        });

        Thread.sleep(1000);
    }

    @Test
    public void toObserveTest() {
        long beginTime = System.currentTimeMillis();

        //非阻塞式调用
        ObserveCommandDemo commandDemo2 = new ObserveCommandDemo("toObserve2");
        Observable<String> toObservable2 = commandDemo2.toObservable();
        toObservable2.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("toObservable onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("toObservable onError, throw " + throwable);
            }

            @Override
            public void onNext(String s) {
                System.out.println("toObservable onNext result: " + s);
                long endTime = System.currentTimeMillis();
                System.out.println("spend time: " + (endTime - beginTime));
            }
        });
    }

}
