package com.yasin.meetingfilm.backend.film.services;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yasin.meetingfilm.backend.apis.film.vo.DescribeFilmInfoRespVO;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.film.controller.vo.DescribeActorsRespVO;
import com.yasin.meetingfilm.backend.film.controller.vo.DescribeFilmsRespVO;
import com.yasin.meetingfilm.backend.film.controller.vo.FilmSaveReqVO;

/**
 * @author Yasin Zhang
 */
public interface IFilmService {

    // 获取演员列表
    IPage<DescribeActorsRespVO> describeActors(int nowPages, int pageSize);

    // 获取影片列表
    IPage<DescribeFilmsRespVO> describeFilms(int nowPages, int pageSize);

    // 通过主键获取影片信息
    DescribeFilmInfoRespVO describeFilmInfoById(int filmId) throws CommonServiceException;

    // 保存电影信息
    void saveFilm(FilmSaveReqVO filmSaveReqVO) throws CommonServiceException;

}
