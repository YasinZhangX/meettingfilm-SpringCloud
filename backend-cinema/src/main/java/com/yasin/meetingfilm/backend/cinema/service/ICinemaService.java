package com.yasin.meetingfilm.backend.cinema.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yasin.meetingfilm.backend.cinema.controller.vo.CinemaSavedReqVO;
import com.yasin.meetingfilm.backend.cinema.controller.vo.DescribeCinemaRespVO;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;

/**
 * @author Yasin Zhang
 */
public interface ICinemaService {

    // 保存影院
    void saveCinema(CinemaSavedReqVO reqVO) throws CommonServiceException;

    // 获取影院数据
    IPage<DescribeCinemaRespVO> describeCinemas(int nowPage, int pageSize) throws CommonServiceException;

}
