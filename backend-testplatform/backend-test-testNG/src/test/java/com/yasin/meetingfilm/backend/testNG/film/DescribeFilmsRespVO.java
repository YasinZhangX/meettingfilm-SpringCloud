package com.yasin.meetingfilm.backend.testNG.film;

import lombok.Data;

/**
 * @author Yasin Zhang
 */
@Data
public class DescribeFilmsRespVO {
    private String filmId;
    private String filmStatus;
    private String filmName;
    private String filmEnName;
    private String filmScore;
    private String preSalNum;
    private String boxOffice;
    private String filmTime;
    private String filmLength;
    private String mainImg;
}
