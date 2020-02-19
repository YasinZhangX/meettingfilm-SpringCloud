package com.yasin.meetingfilm.backend.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Yasin Zhang
 * @description 公共的业务逻辑错误
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CommonServiceException extends Exception{

    private Integer code;
    private String message;

    public CommonServiceException(int code, String message){
        this.code = code;
        this.message = message;
    }

}
