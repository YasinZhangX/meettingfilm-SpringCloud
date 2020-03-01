package com.yasin.meetingfilm.backend.cinema.controller.vo;

import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.common.vo.BaseRequestVO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Yasin Zhang
 */
@Getter
@Setter
public class CinemaSavedReqVO extends BaseRequestVO {

    private Integer brandId;
    private Integer areaId;
    private String hallTypeIds;
    private String cinemaName;
    private String cinemaAddress;
    private String cinemaTele;
    private String cinemaImgAddress;
    private Integer cinemaPrice;

    @Override
    public void checkParam() throws CommonServiceException {

    }
}
