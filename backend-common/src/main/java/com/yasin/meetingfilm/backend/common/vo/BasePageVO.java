package com.yasin.meetingfilm.backend.common.vo;

import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Yasin Zhang
 */
@Getter
@Setter
public class BasePageVO extends BaseRequestVO {
    private Integer nowPage = 1;
    private Integer pageSize = 10;

    @Override
    public void checkParam() throws CommonServiceException {
        if (nowPage == null || pageSize == null) {
            throw new CommonServiceException(404, "nowPage or pageSize should not be null");
        }

        if (nowPage <= 0 || pageSize < 0) {
            throw new CommonServiceException(404, "nowPage should be positive and pageSize should be non-negative");
        }
    }
}
