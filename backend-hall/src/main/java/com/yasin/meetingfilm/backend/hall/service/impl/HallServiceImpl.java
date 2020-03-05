package com.yasin.meetingfilm.backend.hall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasin.meetingfilm.backend.apis.film.vo.DescribeFilmInfoRespVO;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.common.vo.BaseResponseVO;
import com.yasin.meetingfilm.backend.hall.api.FilmFeignClient;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallSavedReqVO;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallsReqVO;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallsRespVO;
import com.yasin.meetingfilm.backend.hall.dao.entity.MoocFieldT;
import com.yasin.meetingfilm.backend.hall.dao.entity.MoocHallFilmInfoT;
import com.yasin.meetingfilm.backend.hall.dao.mapper.MoocFieldTMapper;
import com.yasin.meetingfilm.backend.hall.dao.mapper.MoocHallFilmInfoTMapper;
import com.yasin.meetingfilm.backend.hall.service.IHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Yasin Zhang
 */
@Service("hallService")
public class HallServiceImpl implements IHallService {

    @Resource
    private MoocHallFilmInfoTMapper moocHallFilmInfoTMapper;

    @Resource
    private MoocFieldTMapper moocFieldTMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private FilmFeignClient filmFeignClient;

    @Override
    public IPage<HallsRespVO> describeHalls(HallsReqVO reqVO) throws CommonServiceException {
        Page<HallsRespVO> page = new Page<>(reqVO.getNowPage(), reqVO.getPageSize());

        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        if (reqVO.getCinemaId() != null) {
            queryWrapper.eq("cinema_id", reqVO.getCinemaId());
        }

        return moocFieldTMapper.describeHalls(page, queryWrapper);
    }

    @Override
    public void saveHall(HallSavedReqVO hallSavedReqVO) throws CommonServiceException {
        MoocFieldT field = new MoocFieldT();

        field.setCinemaId(hallSavedReqVO.getCinemaId());
        field.setFilmId(hallSavedReqVO.getFilmId());
        field.setBeginTime(hallSavedReqVO.getBeginTime());
        field.setEndTime(hallSavedReqVO.getEndTime());
        field.setHallId(hallSavedReqVO.getHallTypeId());
        field.setHallName(hallSavedReqVO.getHallName());
        field.setPrice(hallSavedReqVO.getFilmPrice());

        moocFieldTMapper.insert(field);

        // 播放厅对应数据，冗余数据，redis也需要缓存
        MoocHallFilmInfoT hallFilmInfo = describeFilmInfo(hallSavedReqVO.getFilmId());

        moocHallFilmInfoTMapper.insert(hallFilmInfo);
    }

    private MoocHallFilmInfoT describeFilmInfo(Integer filmId) throws CommonServiceException {
        BaseResponseVO<DescribeFilmInfoRespVO> baseResponseVO = filmFeignClient.describeFilmById(filmId);
        DescribeFilmInfoRespVO filmResult = baseResponseVO.getData();
        if (filmResult == null || filmResult.getFilmId() == null) {
            throw new CommonServiceException(404, "未能找到影片信息（filmId: " + filmId + " )");
        }

        MoocHallFilmInfoT hallFilmInfoT = new MoocHallFilmInfoT();
        hallFilmInfoT.setFilmId(filmResult.getFilmId());
        hallFilmInfoT.setFilmName(filmResult.getFilmName());
        hallFilmInfoT.setFilmLength(filmResult.getFilmLength());
        hallFilmInfoT.setFilmCats(filmResult.getFilmCats());
        hallFilmInfoT.setActors(filmResult.getActors());
        hallFilmInfoT.setImgAddress(filmResult.getImgAddress());

        return hallFilmInfoT;
    }
}
