package com.yasin.meetingfilm.backend.zuul.fallback;

import com.alibaba.fastjson.JSONObject;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.common.vo.BaseResponseVO;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Yasin Zhang
 */
@Component
public class MyFallback implements FallbackProvider {
    /**
     * 针对哪个路由进行降级，return可以写 *
     */
    @Override
    public String getRoute() {
        return "film-service";
    }

    /**
     * 降级时的处理方式
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            /**
             * 业务降级处理方式
             */
            @Override
            public InputStream getBody() throws IOException {
                BaseResponseVO responseVO = BaseResponseVO.serviceException(
                    new CommonServiceException(404, "请求出现错误")
                );
                String result = JSONObject.toJSONString(responseVO);
                return new ByteArrayInputStream(result.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
