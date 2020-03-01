package com.yasin.meetingfilm.backend.cinema.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasin.meetingfilm.backend.cinema.controller.vo.DescribeCinemaRespVO;
import com.yasin.meetingfilm.backend.cinema.dao.entity.MoocCinemaT;

/**
 * <p>
 * 影院信息表 Mapper 接口
 * </p>
 *
 * @author yasin
 * @since 2020-02-29
 */
public interface MoocCinemaTMapper extends BaseMapper<MoocCinemaT> {

    IPage<DescribeCinemaRespVO> describeCinemas(Page<DescribeCinemaRespVO> page);

}
