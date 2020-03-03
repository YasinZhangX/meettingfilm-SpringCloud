package com.yasin.meetingfilm.hystrix.command;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.yasin.meetingfilm.backend.test.hystrix.command.CommandDemo;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Yasin Zhang
 */
public class CommandTest {

    @Test
    public void executeTest() {
        long beginTime = System.currentTimeMillis();

        CommandDemo commandDemo = new CommandDemo("execute");
        // 同步执行Command
        String result = commandDemo.execute();

        System.out.println("result:" + result);

        long endTime = System.currentTimeMillis();
        System.out.println("spend time: " + (endTime - beginTime));
    }

    @Test
    public void queueTest() throws ExecutionException, InterruptedException {
        long beginTime = System.currentTimeMillis();

        CommandDemo commandDemo = new CommandDemo("queue");
        Future<String> quque = commandDemo.queue();

        System.out.println("future get");
        long endTime = System.currentTimeMillis();
        System.out.println("spend time: " + (endTime - beginTime));

        System.out.println("result:" + quque.get());
        long endTime2 = System.currentTimeMillis();
        System.out.println("spend time: " + (endTime2 - beginTime));
    }

    @Test
    public void observeTest() {
        long beginTime = System.currentTimeMillis();

        CommandDemo commandDemo = new CommandDemo("observe");

        Observable<String> observable = commandDemo.observe();
        // 阻塞式调用
        String result = observable.toBlocking().single();
        System.out.println("result:" + result);

        long endTime = System.currentTimeMillis();
        System.out.println("spend time: " + (endTime - beginTime));

        //非阻塞式调用
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("observable onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("observable onError, throw " + throwable);
            }

            @Override
            public void onNext(String s) {
                System.out.println("observable onNext result: " + s);
                long endTime = System.currentTimeMillis();
                System.out.println("spend time: " + (endTime - beginTime));
            }
        });
    }

    @Test
    public void toObserveTest() {
        long beginTime = System.currentTimeMillis();

        CommandDemo commandDemo1 = new CommandDemo("toObserve1");
        Observable<String> toObservable1 = commandDemo1.toObservable();
        // 阻塞式调用
        String result = toObservable1.toBlocking().single();
        System.out.println("result:" + result);

        long endTime = System.currentTimeMillis();
        System.out.println("spend time: " + (endTime - beginTime));

        //非阻塞式调用
        CommandDemo commandDemo2 = new CommandDemo("toObserve2");
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

    @Test
    public void requestCacheTest() {
        // 开启请求上下文
        HystrixRequestContext requestContext = HystrixRequestContext.initializeContext();

        long beginTime = System.currentTimeMillis();

        CommandDemo commandDemo1 = new CommandDemo("c1");
        CommandDemo commandDemo2 = new CommandDemo("c2");
        CommandDemo commandDemo3 = new CommandDemo("c1");

        // 执行第一次
        String result1 = commandDemo1.execute();
        System.out.println("result:" + result1 + ", spend time: " + (System.currentTimeMillis() - beginTime));
        // 执行第二次
        String result2 = commandDemo2.execute();
        System.out.println("result:" + result2 + ", spend time: " + (System.currentTimeMillis() - beginTime));
        // 执行第三次
        String result3 = commandDemo3.execute();
        System.out.println("result:" + result3 + ", spend time: " + (System.currentTimeMillis() - beginTime));


        // 关闭请求上下文
        requestContext.close();
    }

    /**
     * 线程池演示
     */
    @Test
    public void threadPoolTest() throws ExecutionException, InterruptedException {
        CommandDemo c1 = new CommandDemo("c1");
        CommandDemo c2 = new CommandDemo("c2");
        CommandDemo c3 = new CommandDemo("c3");
        CommandDemo c4 = new CommandDemo("c4");
        CommandDemo c5 = new CommandDemo("c5");

        Future<String> q1 = c1.queue();
        Future<String> q2 = c2.queue();
        Future<String> q3 = c3.queue();
        Future<String> q4 = c4.queue();
        Future<String> q5 = c5.queue();

        String r1 = q1.get();
        String r2 = q2.get();
        String r3 = q3.get();
        String r4 = q4.get();
        String r5 = q5.get();

        System.out.println(r1 + ", " + r2 + ", " + r3 + ", " + r4 + ", " + r5);
    }

    @Test
    public void semaphoreTest() throws InterruptedException {
        MyThread t1 = new MyThread("t1");
        MyThread t2 = new MyThread("t2");
        MyThread t3 = new MyThread("t3");
        MyThread t4 = new MyThread("t4");
        MyThread t5 = new MyThread("t5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        Thread.sleep(2000);
    }

    /**
     * 熔断器
     */
    @Test
    public void CBTest() throws InterruptedException {
        // 正常
        CommandDemo c1 = new CommandDemo("test1");
        System.out.println(c1.execute());

        // 错误
        CommandDemo c2 = new CommandDemo("break1");
        System.out.println(c2.execute());

        // 延迟使熔断
        Thread.sleep(1000);

        // 正常，但熔断
        CommandDemo c3 = new CommandDemo("test2");
        System.out.println(c3.execute());

        // 延时使半开启
        Thread.sleep(5000);
//        // 错误
//        CommandDemo c4 = new CommandDemo("break2");
//        System.out.println(c4.execute());

        // 正常
        CommandDemo c5 = new CommandDemo("test3");
        System.out.println(c5.execute());

        // 正常
        CommandDemo c6 = new CommandDemo("test4");
        System.out.println(c6.execute());

    }


    static class MyThread extends Thread {
        private String name;
        public MyThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            CommandDemo commandDemo = new CommandDemo(name);
            System.out.println("commandDemo thread, result=" + commandDemo.execute());
        }
    }

}
