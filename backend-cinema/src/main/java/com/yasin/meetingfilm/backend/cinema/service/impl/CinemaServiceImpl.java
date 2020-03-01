package com.yasin.meetingfilm.backend.cinema.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasin.meetingfilm.backend.cinema.controller.vo.CinemaSavedReqVO;
import com.yasin.meetingfilm.backend.cinema.controller.vo.DescribeCinemaRespVO;
import com.yasin.meetingfilm.backend.cinema.dao.entity.MoocCinemaT;
import com.yasin.meetingfilm.backend.cinema.dao.mapper.MoocCinemaTMapper;
import com.yasin.meetingfilm.backend.cinema.service.ICinemaService;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Yasin Zhang
 */
@Service(value = "cinemaService")
public class CinemaServiceImpl implements ICinemaService {

    @Resource
    private MoocCinemaTMapper cinemaTMapper;

    @Override
    public void saveCinema(CinemaSavedReqVO reqVO) throws CommonServiceException {
        MoocCinemaT cinema = new MoocCinemaT();

        cinema.setAreaId(reqVO.getAreaId());
        cinema.setBrandId(reqVO.getBrandId());
        cinema.setHallIds(reqVO.getHallTypeIds());
        cinema.setCinemaName(reqVO.getCinemaName());
        cinema.setCinemaAddress(reqVO.getCinemaAddress());
        cinema.setCinemaPhone(reqVO.getCinemaTele());
        cinema.setImgAddress(reqVO.getCinemaImgAddress());
        cinema.setMinimumPrice(reqVO.getCinemaPrice());

        int result = cinemaTMapper.insert(cinema);
        if (result != 1) {
            throw new CommonServiceException(500, "影院数据插入失败");
        }
    }

    @Override
    public IPage<DescribeCinemaRespVO> describeCinemas(int nowPage, int pageSize) throws CommonServiceException {
        return cinemaTMapper.describeCinemas(new Page<>(nowPage, pageSize));
    }

}
