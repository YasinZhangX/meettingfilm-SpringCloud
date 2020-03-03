package com.yasin.meetingfilm.backend.test.hystrix.command;

import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.Collection;
import java.util.List;

/**
 * @author Yasin Zhang
 */
class BatchCommand extends HystrixCommand<List<String>> {

    private Collection<HystrixCollapser.CollapsedRequest<String, Integer>> collection;

    public BatchCommand(Collection<HystrixCollapser.CollapsedRequest<String, Integer>> collection) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchCommand")));
        this.collection = collection;
    }

    @Override
    protected List<String> run() throws Exception {
        System.out.println("currentThreadName: " + Thread.currentThread().getName());
        List<String> results = Lists.newArrayList();
        for (HystrixCollapser.CollapsedRequest<String, Integer> request : collection) {
            Integer reqParam = request.getArgument();
            results.add("Req:" + reqParam);
        }
        return results;
    }
}
