package com.yasin.meetingfilm.backend.hall.controller.vo;

import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.common.vo.BaseRequestVO;
import com.yasin.meetingfilm.backend.utils.ToolUtils;
import lombok.Data;

/**
 * @author Yasin Zhang
 */
@Data
public class HallSavedReqVO extends BaseRequestVO {
    private Integer cinemaId;
    private Integer filmId;
    private Integer hallTypeId;
    private String beginTime;
    private String endTime;
    private Integer filmPrice;
    private String hallName;

    @Override
    public void checkParam() throws CommonServiceException {
        if (cinemaId == null || filmId == null
            || hallTypeId == null || filmPrice == null
            || ToolUtils.strIsNull(beginTime) || ToolUtils.strIsNull(endTime)
            || ToolUtils.strIsNull(hallName)) {
            throw new CommonServiceException(404, "Unsupported arguments");
        }
    }
}
