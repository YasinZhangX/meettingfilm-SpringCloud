package com.yasin.meetingfilm.backend.film.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.common.vo.BasePageVO;
import com.yasin.meetingfilm.backend.common.vo.BaseResponseVO;
import com.yasin.meetingfilm.backend.film.controller.vo.DescribeActorsRespVO;
import com.yasin.meetingfilm.backend.film.controller.vo.DescribeFilmInfoRespVO;
import com.yasin.meetingfilm.backend.film.controller.vo.DescribeFilmsRespVO;
import com.yasin.meetingfilm.backend.film.controller.vo.FilmSaveReqVO;
import com.yasin.meetingfilm.backend.film.services.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Yasin Zhang
 */
@RestController
@RequestMapping(value = "/films")
public class FilmController {

    @Autowired
    private IFilmService filmService;

    /**
     * 获取演员列表
     */
    @RequestMapping(value = "/actors", method = RequestMethod.GET)
    public BaseResponseVO<Object> describeActors(BasePageVO basePageVO) throws CommonServiceException {
        basePageVO.checkParam();

        IPage<DescribeActorsRespVO> results =
            filmService.describeActors(basePageVO.getNowPage(), basePageVO.getPageSize());
        if (results == null) {
            throw new CommonServiceException(404, "没有演员数据");
        }

        return BaseResponseVO.success(describePageResult(results, "actors"));
    }

    /**
     * 获取电影列表
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseResponseVO<Object> describeFilms(BasePageVO basePageVO) throws CommonServiceException {
        basePageVO.checkParam();

        IPage<DescribeFilmsRespVO> results =
            filmService.describeFilms(basePageVO.getNowPage(), basePageVO.getPageSize());
        if (results == null) {
            throw new CommonServiceException(404, "没有影片数据");
        }

        return BaseResponseVO.success(describePageResult(results, "films"));
    }

    /**
     * 根据电影编号查询电影信息接口
     */
    @RequestMapping(value = "/{filmId}", method = RequestMethod.GET)
    public BaseResponseVO<Object> describeFilms(@PathVariable("filmId") Integer filmId) throws CommonServiceException {
        DescribeFilmInfoRespVO describeFilmInfoRespVO = filmService.describeFilmInfoById(filmId);
        if (describeFilmInfoRespVO == null) {
            return BaseResponseVO.success();
        } else {
            return BaseResponseVO.success(describeFilmInfoRespVO);
        }
    }

    @RequestMapping(value = "/film:add", method = RequestMethod.POST)
    public BaseResponseVO<Object> saveFilmInfo(@RequestBody FilmSaveReqVO filmSaveReqVO) throws CommonServiceException {
        filmService.saveFilm(filmSaveReqVO);

        return BaseResponseVO.success();
    }

    private Map<String, Object> describePageResult(IPage<?> page, String title) {
        Map<String, Object> result = Maps.newHashMap();

        result.put("totalSize", page.getTotal());
        result.put("totalPages", page.getPages());
        result.put("PageSize", page.getSize());
        result.put("nowPage", page.getCurrent());

        result.put(title, page.getRecords());

        return result;
    }

}
