package com.yasin.meetingfilm.backend.cinema.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

    public BaseResponseVO<?> fallbackMethod(BasePageVO basePageVO) throws CommonServiceException {
        Map<String, Object> result = Maps.newHashMap();
        result.put("code", "500");
        result.put("message", "请求处理降级返回");
        return BaseResponseVO.success(result);
    }

    /**
     * 获取影院列表
     */
    @HystrixCommand(fallbackMethod = "fallbackMethod",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value= "1000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
        },
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "1"),
            @HystrixProperty(name = "maxQueueSize", value = "10"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "1000"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "8"),
            @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1500")
        },
        ignoreExceptions = CommonServiceException.class
    )
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseResponseVO<?> describeCinemas(BasePageVO basePageVO) throws CommonServiceException {
        if (basePageVO.getNowPage() > 10000) {
            throw new CommonServiceException(400, "不支持");
        }

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
