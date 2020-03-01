package com.yasin.meetingfilm.backend.testserviceconsumer.service.impl;

import com.yasin.meetingfilm.backend.testserviceconsumer.service.IConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Yasin Zhang
 */
@Service("consumerService")
public class ConsumerServiceImpl implements IConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String sayHello(String message) {
        String uri = "/provider/sayhello?message="+message;

        String url = "http://hello-service-provider" + uri;

        return restTemplate.getForObject(url, String.class);
    }
}
