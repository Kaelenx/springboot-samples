package com.cookie.springbootstudyweek05.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cookie.springbootstudyweek05.mapper.UserMPMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cookie.springbootstudyweek05.entity.User;


/**
 * MP版用户业务层
 * 核心：分页查询、条件构造、基础CRUD
 */
@Service
@RequiredArgsConstructor // 构造器注入（PDF推荐，替代@Autowired）
public class UserMPService {

    private final UserMPMapper userMPMapper;

    /**
     * 条件分页查询（PDF核心实现）
     * @param username 用户名（模糊查询）
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页条数
     * @return 分页结果
     */
    public Page<User> page(String username, Integer pageNum, Integer pageSize) {
        // 1. 构建分页参数
        Page<User> page = Page.of(pageNum, pageSize);
        // 2. 构建Lambda条件构造器（防字段拼写错误，PDF推荐）
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        // 用户名不为空时，才拼接模糊查询条件
        wrapper.like(username != null && !username.isEmpty(), User::getUsername, username);
        // 3. 执行分页查询，MP自动生成分页SQL
        return userMPMapper.selectPage(page, wrapper);
    }

    /**
     * 新增用户（无SQL，直接调用MP内置方法）
     */
    public int add(User user) {
        return userMPMapper.insert(user);
    }

    /**
     * 根据ID删除用户
     */
    public int delete(Long id) {
        return userMPMapper.deleteById(id);
    }

    /**
     * 根据ID更新用户
     */
    public int update(User user) {
        return userMPMapper.updateById(user);
    }

    /**
     * 根据ID查询用户
     */
    public User getById(Long id) {
        return userMPMapper.selectById(id);
    }

    /**
     * 查询所有用户
     */
    public java.util.List<User> list() {
        return userMPMapper.selectList(null);
    }
}
