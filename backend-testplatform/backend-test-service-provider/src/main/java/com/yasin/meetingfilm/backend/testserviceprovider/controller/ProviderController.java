package com.yasin.meetingfilm.backend.testserviceprovider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yasin Zhang
 */
@RestController
@Slf4j
public class ProviderController {
    @Value("${server.port}")
    private int port;

    @RequestMapping(value = "/provider/sayhello")
    public String providerSayHello(String message) {
        log.info("provider sayHello port:{}, message:{}", port, message);

        return "provider sayHello port:" + port + ", message:" + message;
    }
}
