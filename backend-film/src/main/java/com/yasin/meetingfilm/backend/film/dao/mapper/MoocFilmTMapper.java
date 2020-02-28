package com.yasin.meetingfilm.backend.film.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasin.meetingfilm.backend.film.controller.vo.DescribeFilmInfoRespVO;
import com.yasin.meetingfilm.backend.film.controller.vo.DescribeFilmsRespVO;
import com.yasin.meetingfilm.backend.film.dao.entity.MoocFilmT;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author yasin
 * @since 2020-02-27
 */
public interface MoocFilmTMapper extends BaseMapper<MoocFilmT> {

    IPage<DescribeFilmsRespVO> describeFilms(Page<DescribeFilmsRespVO> page);

    DescribeFilmInfoRespVO describeFilmById(@Param("filmId") int filmId);

}
