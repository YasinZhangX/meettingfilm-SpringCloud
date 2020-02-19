package com.yasin.meetingfilm.backend.user.service;

import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;

/**
 * @author Yasin Zhang
 */
public interface IUserService {

    String checkUserLogin(String username, String password) throws CommonServiceException;

}
