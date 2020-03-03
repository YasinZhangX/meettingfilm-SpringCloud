package com.yasin.meetingfilm.backend.test.hystrix.command;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCommand;

import java.util.Collection;
import java.util.List;

/**
 * @author Yasin Zhang
 */
public class CommandCollapser extends HystrixCollapser<List<String>, String, Integer> {

    private Integer id;

    public CommandCollapser(int id) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("commandCollapser")));
        this.id = id;
    }

    /**
     * 获取请求参数
     */
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    /**
     * 批量业务处理
     */
    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> collection) {
        return new BatchCommand(collection);
    }

    /**
     * 批量处理结果与请求业务之间映射关系处理
     */
    @Override
    protected void mapResponseToRequests(List<String> strings, Collection<CollapsedRequest<String, Integer>> collection) {
        int count = 0;
        for (HystrixCollapser.CollapsedRequest<String, Integer> response : collection) {
            String result = strings.get(count++);
            response.setResponse(result);
        }
    }
}
