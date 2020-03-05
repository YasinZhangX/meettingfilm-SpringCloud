package com.yasin.meetingfilm.backend.apis.film;

import com.yasin.meetingfilm.backend.apis.film.vo.DescribeFilmInfoRespVO;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.common.vo.BaseResponseVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Yasin Zhang
 */
public interface FilmFeignService {

    @RequestMapping(value = "/{filmId}", method = RequestMethod.GET)
    BaseResponseVO<DescribeFilmInfoRespVO> describeFilmById(@PathVariable("filmId") Integer filmId) throws CommonServiceException;

}
