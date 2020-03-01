package com.yasin.meetingfilm.backend.cinema.controller.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Yasin Zhang
 */
@Getter
@Setter
public class DescribeCinemaRespVO {
    private Integer brandId;
    private Integer areaId;
    private String hallTypeIds;
    private String cinemaName;
    private String cinemaAddress;
    private String cinemaTele;
    private String cinemaImgAddress;
    private String cinemaPrice;
    private String cinemaId;
}
