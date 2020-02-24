package com.yasin.meetingfilm.backend.testserviceconsumer.controller;

import com.yasin.meetingfilm.backend.testserviceconsumer.service.IConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yasin Zhang
 */
@RestController
@Slf4j
public class ConsumerController {

    private final IConsumerService consumerService;

    public ConsumerController(IConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    public String sayHello(String message) {
        return consumerService.sayHello(message);
    }

}
