package com.yasin.meetingfilm.backend.common.vo;


import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;

/**
 * @author Yasin Zhang
 */
public abstract class BaseRequestVO {

    public abstract void checkParam() throws CommonServiceException;

}
