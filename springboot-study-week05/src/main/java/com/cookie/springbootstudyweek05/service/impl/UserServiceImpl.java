package com.cookie.springbootstudyweek05.service.impl;


import com.cookie.springbootstudyweek05.entity.User;
import com.cookie.springbootstudyweek05.mapper.UserMapper;
import com.cookie.springbootstudyweek05.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> list() {
        return userMapper.selectAll();
    }

    @Override
    public boolean save(User user) {
        return userMapper.insert(user) > 0;
    }

    @Override
    public boolean update(User user) {
        return userMapper.update(user) > 0;
    }

    @Override
    public boolean removeById(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    // 新增：条件查询实现
    @Override
    public List<User> search(String username, Integer age) {
        return userMapper.selectByCondition(username, age);
    }
}