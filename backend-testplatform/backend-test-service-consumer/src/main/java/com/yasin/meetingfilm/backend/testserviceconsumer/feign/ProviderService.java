package com.yasin.meetingfilm.backend.testserviceconsumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yasin Zhang
 */
//@FeignClient(name = "providerTest", url = "http://localhost:7658", path = "/provider",
//             primary = true, configuration = FeignHelloConfig.class)
@FeignClient(name = "hello-service-provider", path = "/provider", primary = true,
    fallback = ProviderFallback.class,
    fallbackFactory = FallbackFactory.class
)
@Primary  // 为了IDEA不报错而添加
public interface ProviderService {

    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    String invokeProvider(@RequestParam("message") String message);

    @RequestMapping(value = "/{providerId}/sayHello", method = RequestMethod.POST)
    public String annotationTest(@RequestHeader("author") String author,
                                 @PathVariable String providerId,
                                 @RequestBody UserModel user);

}
