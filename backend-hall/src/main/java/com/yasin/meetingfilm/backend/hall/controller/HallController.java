package com.yasin.meetingfilm.backend.hall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.common.vo.BaseResponseVO;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallSavedReqVO;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallsReqVO;
import com.yasin.meetingfilm.backend.hall.controller.vo.HallsRespVO;
import com.yasin.meetingfilm.backend.hall.service.IHallService;
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
@RequestMapping("/halls")
public class HallController {

    @Autowired
    private IHallService hallService;

    /**
     * 影片播放厅列表查询
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseResponseVO<?> describeHalls(HallsReqVO hallsReqVO) throws CommonServiceException {
        hallsReqVO.checkParam();

        IPage<HallsRespVO> iPage = hallService.describeHalls(hallsReqVO);

        return BaseResponseVO.success(convertIPageToMap("halls", iPage));
    }

    /**
     * 新增影片播放厅信息
     */
    @RequestMapping(value = "/hall:add", method = RequestMethod.POST)
    public BaseResponseVO<?> saveHall(@RequestBody HallSavedReqVO savedReqVO) throws CommonServiceException{
        savedReqVO.checkParam();

        hallService.saveHall(savedReqVO);

        return BaseResponseVO.success();
    }

    private Map<String, Object> convertIPageToMap(String title, IPage<?> page) {
        Map<String, Object> result = Maps.newHashMap();

        result.put("totalSize", page.getTotal());
        result.put("totalPages", page.getPages());
        result.put("PageSize", page.getSize());
        result.put("nowPage", page.getCurrent());

        result.put(title, page.getRecords());

        return result;
    }

}
