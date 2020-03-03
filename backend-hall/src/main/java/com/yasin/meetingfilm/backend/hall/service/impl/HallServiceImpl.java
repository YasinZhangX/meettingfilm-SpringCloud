package com.yasin.meetingfilm.backend.hall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallSavedReqVO;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallsReqVO;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallsRespVO;
import com.yasin.meetingfilm.backend.hall.dao.entity.MoocFieldT;
import com.yasin.meetingfilm.backend.hall.dao.entity.MoocHallFilmInfoT;
import com.yasin.meetingfilm.backend.hall.dao.mapper.MoocFieldTMapper;
import com.yasin.meetingfilm.backend.hall.dao.mapper.MoocHallFilmInfoTMapper;
import com.yasin.meetingfilm.backend.hall.service.IHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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

    @Autowired
    private LoadBalancerClient eurekaClient;

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
        // 组织调用参数
        String uri = "/films/" + filmId;
        String url = "http://film-service" + uri;

        // 调用影片服务
        JSONObject baseRespVO = restTemplate.getForObject(url, JSONObject.class);

        // 解析返回值
        JSONObject dataJson = baseRespVO.getJSONObject("data");
        if (dataJson == null) {
            throw new CommonServiceException(500, "can not get film info");
        }

        MoocHallFilmInfoT hallFilmInfoT = new MoocHallFilmInfoT();
        hallFilmInfoT.setFilmId(dataJson.getInteger("filmId"));
        hallFilmInfoT.setFilmName(dataJson.getString("filmName"));
        hallFilmInfoT.setFilmLength(dataJson.getString("filmLength"));
        hallFilmInfoT.setFilmCats(dataJson.getString("filmCats"));
        hallFilmInfoT.setActors(dataJson.getString("actors"));
        hallFilmInfoT.setImgAddress(dataJson.getString("imgAddress"));

        return hallFilmInfoT;
    }
}
