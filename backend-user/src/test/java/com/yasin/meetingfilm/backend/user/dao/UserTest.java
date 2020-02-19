package com.yasin.meetingfilm.backend.user.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasin.meetingfilm.backend.user.BackendUserApplicationTests;
import com.yasin.meetingfilm.backend.user.dao.entity.MoocBackendUserT;
import com.yasin.meetingfilm.backend.user.dao.mapper.MoocBackendUserTMapper;
import com.yasin.meetingfilm.backend.utils.MD5Util;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yasin Zhang
 */
public class UserTest extends BackendUserApplicationTests {

    @Resource
    private MoocBackendUserTMapper backendUser;

    @Test
    public void add() {
        MoocBackendUserT user = new MoocBackendUserT();
        user.setUserName("admin");
        user.setUserPwd(MD5Util.encrypt("admin"));
        user.setUserPhone("123000000");

        backendUser.insert(user);
    }

    @Test
    public void select() {
//        // 根据主键查询
//        MoocBackendUserT user = backendUser.selectById(7);
//        System.out.println("user: " + user);
//
//        // 根据列表查询
//        List<MoocBackendUserT> users = backendUser.selectList(null);
//        users.forEach(
//                System.out::println
//        );

        // 查询列表带条件
        QueryWrapper<MoocBackendUserT> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", "test1");
        List<MoocBackendUserT> users = backendUser.selectList(queryWrapper);
        users.forEach(
            System.out::println
        );
    }

    @Test
    public void update() {
        MoocBackendUserT user = new MoocBackendUserT();
        user.setUuid(7);
        user.setUserName("123");
        user.setUserPwd("456");

        backendUser.updateById(user);
    }

    @Test
    public void selectByPage() {
        // 分页
        Page<MoocBackendUserT> page = new Page<>(1, 3);
        // 配置排序 page.setOrders();

        // 条件说明
        QueryWrapper<MoocBackendUserT> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", "test");
        IPage<MoocBackendUserT> iPage = backendUser.selectPage(page, queryWrapper);
        iPage.getRecords().forEach(
            System.out::println
        );
    }

    @Test
    public void delete() {
        QueryWrapper<MoocBackendUserT> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", "test");
        int result = backendUser.delete(queryWrapper);
        System.out.println("delete line: " + result);
    }
}
