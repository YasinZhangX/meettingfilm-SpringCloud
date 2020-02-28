package com.yasin.meetingfilm.backend.film.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasin.meetingfilm.backend.film.controller.vo.DescribeActorsRespVO;
import com.yasin.meetingfilm.backend.film.dao.entity.MoocActorT;

/**
 * <p>
 * 演员表 Mapper 接口
 * </p>
 *
 * @author yasin
 * @since 2020-02-27
 */
public interface MoocActorTMapper extends BaseMapper<MoocActorT> {

    IPage<DescribeActorsRespVO> describeActors(Page<DescribeActorsRespVO> page);

}
