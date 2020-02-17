package com.yasin.meetingfilm.backend.common.dao.mapper;

import com.yasin.meetingfilm.backend.common.dao.entity.MoocBackendUserT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author yasin
 * @since 2020-02-17
 */
public interface MoocBackendUserTMapper extends BaseMapper<MoocBackendUserT> {

    // test function
    MoocBackendUserT selectUserByUserName(@Param("username") String userName);

}
