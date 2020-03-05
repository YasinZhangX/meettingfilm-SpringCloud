package com.yasin.meetingfilm.backend.testserviceconsumer.controller;

import com.yasin.meetingfilm.backend.testserviceconsumer.feign.ProviderService;
import com.yasin.meetingfilm.backend.testserviceconsumer.feign.UserModel;
import com.yasin.meetingfilm.backend.testserviceconsumer.service.IConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yasin Zhang
 */
@RestController
@Slf4j
public class ConsumerController {

    @Autowired
    private IConsumerService consumerService;

    @Autowired
    private ProviderService providerService;

    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    public String sayHello(String message) {
        return consumerService.sayHello(message);
    }

    @RequestMapping(value = "/sayHello/feign", method = RequestMethod.GET)
    public String sayHelloFeign(String message) {
        System.out.println("feign message=" + message);
        return providerService.invokeProvider(message);
    }

    @RequestMapping(value = "/sayHello/feign/post", method = RequestMethod.GET)
    public String sayHelloFeignPost(String author, String providerId, UserModel user) {
        log.info("author:{}, providerId:{}, message:{}", author, providerId, user);
        return providerService.annotationTest(author, providerId, user);
    }

}
