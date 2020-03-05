package com.yasin.meetingfilm.backend.testserviceconsumer.feign;

import org.springframework.stereotype.Service;

/**
 * @author Yasin Zhang
 */
@Service("providerFallback")
public class ProviderFallback implements ProviderService {
    @Override
    public String invokeProvider(String message) {
        return "invokeProvider fallback message=" + message;
    }

    @Override
    public String annotationTest(String author, String providerId, UserModel user) {
        return "annotationTest fallback message=" + providerId;
    }
}
