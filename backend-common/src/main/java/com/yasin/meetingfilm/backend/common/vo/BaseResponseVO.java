package com.yasin.meetingfilm.backend.common.vo;

import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import lombok.Data;

/**
 * @author Yasin Zhang
 * @description 表现层公共返回
 */
@Data
public class BaseResponseVO<M> {

    private Integer code;
    private String message;
    private M data;

    private BaseResponseVO(){}

    // 成功无参数
    public static BaseResponseVO<Object> success() {
        BaseResponseVO<Object> response = new BaseResponseVO<>();
        response.setCode(200);
        response.setMessage("");
        return response;
    }

    // 成功有参数
    public static<M> BaseResponseVO<M> success(M data) {
        BaseResponseVO<M> response = new BaseResponseVO<>();
        response.setCode(200);
        response.setMessage("");
        response.setData(data);
        return response;
    }

    // 出现异常
    public static BaseResponseVO<Object> serviceException(CommonServiceException e) {
        BaseResponseVO<Object> response = new BaseResponseVO<>();
        response.setCode(e.getCode());
        response.setMessage(e.getMessage());
        return response;
    }

}
