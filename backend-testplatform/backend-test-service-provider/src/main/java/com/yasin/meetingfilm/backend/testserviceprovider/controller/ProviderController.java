package com.yasin.meetingfilm.backend.testserviceprovider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yasin Zhang
 */
@RestController
@Slf4j
public class ProviderController {
    @Value("${server.port}")
    private int port;

    @RequestMapping(value = "/provider/sayHello", method = RequestMethod.GET)
    public String providerSayHello(@RequestParam("message") String message) {
        log.info("provider sayHello port:{}, message:{}", port, message);

        return "provider sayHello port:" + port + ", message:" + message;
    }

    @RequestMapping(value = "/provider/{providerId}/sayHello", method = RequestMethod.POST)
    public String annotationTest(@RequestHeader("author") String author,
                                 @PathVariable String providerId,
                                 @RequestBody String json) {
        log.info("provider postTest port:{}, author:{}, providerId:{}, message:{}",
            port, author, providerId, json);

        return "provider sayHello port:" + port + ", message:" + json;
    }
}
