package com.yasin.meetingfilm.backend.common.exception;

import com.yasin.meetingfilm.backend.common.vo.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yasin Zhang
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler({CommonServiceException.class})
    @ResponseBody
    public BaseResponseVO serviceExceptionHandler(HttpServletRequest request,
                                                  CommonServiceException e) {
        log.error("CommonServiceException, code:{}, message:{}", e.getCode(), e.getMessage(), e);

        return BaseResponseVO.serviceException(e);
    }
}
