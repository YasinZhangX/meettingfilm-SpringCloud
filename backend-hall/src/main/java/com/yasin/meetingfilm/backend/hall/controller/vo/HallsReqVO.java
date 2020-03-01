package com.yasin.meetingfilm.backend.hall.controller.vo;

import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.common.vo.BasePageVO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Yasin Zhang
 */
@Setter
@Getter
public class HallsReqVO extends BasePageVO {
    private Integer cinemaId;

    @Override
    public void checkParam() throws CommonServiceException {
        super.checkParam();
        if (cinemaId == null || cinemaId <= 0) {
            throw new CommonServiceException(404, "unsupported cinemaId");
        }
    }
}
