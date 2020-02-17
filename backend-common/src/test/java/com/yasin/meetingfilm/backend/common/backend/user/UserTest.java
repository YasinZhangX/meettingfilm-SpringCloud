package com.yasin.meetingfilm.backend.common.backend.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yasin.meetingfilm.backend.common.BackendCommonApplicationTest;
import com.yasin.meetingfilm.backend.common.dao.entity.MoocBackendUserT;
import com.yasin.meetingfilm.backend.common.dao.mapper.MoocBackendUserTMapper;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yasin Zhang
 */
public class UserTest extends BackendCommonApplicationTest {

    @Resource
    private MoocBackendUserTMapper backendUser;

    @Test
    public void add() {
        for (int i = 1; i < 5; i++) {
            MoocBackendUserT user = new MoocBackendUserT();
            user.setUserName("test"+i);
            user.setUserPwd("test"+i);
            user.setUserPhone("123000000"+i);

            backendUser.insert(user);
        }
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
    public void delete() {
        backendUser.deleteById(8);
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
    public void selectByUserName() {
        MoocBackendUserT user = backendUser.selectUserByUserName("test1");
        System.out.println("user: " + user);
    }
}
