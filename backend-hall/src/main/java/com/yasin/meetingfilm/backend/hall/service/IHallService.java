package com.yasin.meetingfilm.backend.hall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallSavedReqVO;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallsReqVO;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallsRespVO;

/**
 * @author Yasin Zhang
 */
public interface IHallService {

    IPage<HallsRespVO> describeHalls(HallsReqVO reqVO) throws CommonServiceException;

    void saveHall(HallSavedReqVO hallSavedReqVO) throws CommonServiceException;

}
