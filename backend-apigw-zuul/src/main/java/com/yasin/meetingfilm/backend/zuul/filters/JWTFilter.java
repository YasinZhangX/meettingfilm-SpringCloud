package com.yasin.meetingfilm.backend.zuul.filters;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.yasin.meetingfilm.backend.common.vo.BaseResponseVO;
import com.yasin.meetingfilm.backend.utils.JwtTokenUtil;
import com.yasin.meetingfilm.backend.utils.properties.JwtProperties;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yasin Zhang
 */
public class JWTFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // JWT工具类
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        JwtProperties jwtProperties = JwtProperties.getJwtProperties();

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        // 提前设置请求状态
        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(HttpStatus.OK.value());

        // 判断是否为登录
        if (request.getServletPath().endsWith("/" + jwtProperties.getAuthPath())) {
            return null;
        }

        // 验证token有效性
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            // 验证token是否过期
            try {
                if (jwtTokenUtil.isTokenExpired(authToken)) {
                    renderJson(ctx, response, BaseResponseVO.noLogin());
                } else {
                    // 解析出JWT中的payload
                    String randomKey = jwtTokenUtil.getMd5KeyFromToken(authToken);
                    String userName = jwtTokenUtil.getUsernameFromToken(authToken);
                    System.out.println("randomKey: " + randomKey + ", userName: " + userName);
                    // 是否需要验签

                    // 判断userid是否有效
                }
            } catch (JwtException e) {
                // 异常表示解析失败
                renderJson(ctx, response, BaseResponseVO.noLogin());
            }
        } else {
            //header没有带Bearer字段
            renderJson(ctx ,response, BaseResponseVO.noLogin());
        }

        return null;
    }

    /**
     * 渲染json对象
     */
    public static void renderJson(RequestContext ctx, HttpServletResponse response, Object jsonObject) {
        // 设置终止请求
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        ctx.setSendZuulResponse(false);
        ctx.setResponseBody(JSONObject.toJSONString(jsonObject));
    }

}
