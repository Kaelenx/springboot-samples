package com.cookie.springbootstudyweek05.service;


import com.cookie.springbootstudyweek05.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    // 根据ID查询
    User getById(Long id);

    // 查询所有
    List<User> list();

    // 新增
    boolean save(User user);

    // 更新
    boolean update(User user);

    // 删除
    boolean removeById(Long id);

    // 新增：条件查询（用户名模糊+年龄）
    List<User> search(String username, Integer age);
}
