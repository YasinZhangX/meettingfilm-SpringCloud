package com.yasin.meetingfilm.backend.testNG.common;

import org.springframework.web.client.RestTemplate;

/**
 * @author Yasin Zhang
 */
public class RestUtils {

    private static RestTemplate restTemplate;

    public static RestTemplate restTemplate() {
        if (restTemplate != null) {
            return restTemplate;
        } else {
            restTemplate = new RestTemplate();
            return restTemplate;
        }
    }

}
