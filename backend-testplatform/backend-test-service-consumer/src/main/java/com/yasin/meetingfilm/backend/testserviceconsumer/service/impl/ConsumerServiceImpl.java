package com.yasin.meetingfilm.backend.testserviceconsumer.service.impl;

import com.yasin.meetingfilm.backend.testserviceconsumer.service.IConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Yasin Zhang
 */
@Service("consumerService")
public class ConsumerServiceImpl implements IConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient eurekaClient;

    @Override
    public String sayHello(String message) {
        // invoke provider
        ServiceInstance service = eurekaClient.choose("hello-service-provider");

        String hostname = service.getHost();
        int port = service.getPort();
        String uri = "/provider/sayhello?message="+message;

        String url = "http://" + hostname + ":" + port + uri;

        return restTemplate.getForObject(url, String.class);
    }
}
