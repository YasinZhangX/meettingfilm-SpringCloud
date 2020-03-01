package com.yasin.meetingfilm.backend.hall.controller.vo;

import lombok.Data;

/**
 * @author Yasin Zhang
 */
@Data
public class HallsRespVO {
    private String cinemaName;
    private String hallName;
    private String filmName;
    private String hallTypeName;
    private String beginTime;
    private String endTime;
    private String filmPrice;
}
