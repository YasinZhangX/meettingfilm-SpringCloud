package com.yasin.meetingfilm.backend.user.controller;

import com.yasin.meetingfilm.backend.common.exception.CommonServiceException;
import com.yasin.meetingfilm.backend.common.vo.BaseResponseVO;
import com.yasin.meetingfilm.backend.user.controller.vo.LoginReqVO;
import com.yasin.meetingfilm.backend.user.service.IUserService;
import com.yasin.meetingfilm.backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yasin Zhang
 */
@RestController
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponseVO<Map<String, String>> login(@RequestBody LoginReqVO reqVO) throws CommonServiceException {
        // 数据验证
        reqVO.checkParam();

        // 验证用户名和密码
        String userId = userService.checkUserLogin(reqVO.getUsername(), reqVO.getPassword());

        // 生成数据
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String randomKey = jwtTokenUtil.getRandomKey();
        String token = jwtTokenUtil.generateToken(reqVO.getUsername(), randomKey);
        Map<String, String> result = new HashMap<>();
        result.put("randomKey", randomKey);
        result.put("token", token);

        return BaseResponseVO.success(result);
    }

}
