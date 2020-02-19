package com.yasin.meetingfilm.backend.user.controller.vo;

import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.common.vo.BaseRequestVO;
import com.yasin.meetingfilm.backend.utils.ToolUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录请求对象
 * @author Yasin Zhang
 */
@Getter
@Setter
public class LoginReqVO extends BaseRequestVO {
    private String username;
    private String password;

    @Override
    public void checkParam() throws CommonServiceException {
        if (ToolUtils.strIsNull(username) || ToolUtils.strIsNull(password)) {
            throw new CommonServiceException(404, "username or password should be null");
        }
    }
}
