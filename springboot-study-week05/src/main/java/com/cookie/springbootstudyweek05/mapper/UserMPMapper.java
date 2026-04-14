package com.cookie.springbootstudyweek05.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cookie.springbootstudyweek05.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2026/4/2
 * Java
 * @description UserMPMapper:
 * 空接⼝：
 * BaseMapper
 * 已封装所有单表
 * CRUD
 * ⽅法，⽆需
 * ⼿动编写
 **/
@Mapper
public interface UserMPMapper extends BaseMapper<User> {
}

