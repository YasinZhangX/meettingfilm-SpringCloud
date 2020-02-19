package com.yasin.meetingfilm.backend.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.user.dao.entity.MoocBackendUserT;
import com.yasin.meetingfilm.backend.user.dao.mapper.MoocBackendUserTMapper;
import com.yasin.meetingfilm.backend.user.service.IUserService;
import com.yasin.meetingfilm.backend.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yasin Zhang
 */
@Service(value = "userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private MoocBackendUserTMapper userMapper;

    @Override
    public String checkUserLogin(String username, String password) throws CommonServiceException {
        // 根据用户名获取用户信息
        QueryWrapper<MoocBackendUserT> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        List<MoocBackendUserT> userList = userMapper.selectList(queryWrapper);
        MoocBackendUserT user = null;
        if (userList != null && userList.size() > 0) {
            user = userList.stream().findFirst().get();
        } else {
            throw new CommonServiceException(404, "用户名不存在");
        }

        // 验证密码是否正确
        String encryptPassword = MD5Util.encrypt(password);
        if (!encryptPassword.equals(user.getUserPwd())) {
            throw new CommonServiceException(500, "密码输入错误");
        } else {
            return user.getUuid() + "";
        }
    }

}
