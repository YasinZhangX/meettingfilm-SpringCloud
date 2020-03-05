package com.yasin.meetingfilm.backend.apis.film.vo;

import lombok.Data;

/**
 * @author Yasin Zhang
 */
@Data
public class DescribeFilmInfoRespVO {
    private Integer filmId;
    private String filmName;
    private String filmLength;
    private String filmCats;
    private String actors;
    private String imgAddress;
    private String subAddress;
}
