package com.yasin.meetingfilm.backend.cinema.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.yasin.meetingfilm.backend.cinema.controller.vo.CinemaSavedReqVO;
import com.yasin.meetingfilm.backend.cinema.controller.vo.DescribeCinemaRespVO;
import com.yasin.meetingfilm.backend.cinema.service.ICinemaService;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.common.vo.BasePageVO;
import com.yasin.meetingfilm.backend.common.vo.BaseResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Yasin Zhang
 */
@RestController
@RequestMapping("/cinemas")
public class CinemaController {

    @Autowired
    private ICinemaService cinemaService;

    /**
     * 保存影院数据
     */
    @RequestMapping(value = "cinema:add", method = RequestMethod.POST)
    public BaseResponseVO<?> saveCinema(@RequestBody CinemaSavedReqVO reqVO) throws CommonServiceException {
        reqVO.checkParam();
        cinemaService.saveCinema(reqVO);
        return BaseResponseVO.success();
    }

    /**
     * 获取影院列表
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseResponseVO<?> describeCinemas(BasePageVO basePageVO) throws CommonServiceException {
        IPage<DescribeCinemaRespVO> page =
            cinemaService.describeCinemas(basePageVO.getNowPage(), basePageVO.getPageSize());
        if (page == null) {
            throw new CommonServiceException(404, "没有影院数据");
        }

        Map<String, Object> result = Maps.newHashMap();

        result.put("totalSize", page.getTotal());
        result.put("totalPages", page.getPages());
        result.put("PageSize", page.getSize());
        result.put("nowPage", page.getCurrent());

        result.put("cinemas", page.getRecords());

        return BaseResponseVO.success(result);
    }

}
