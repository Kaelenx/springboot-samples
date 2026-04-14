package com.cookie.springbootstudyweek06.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cookie.springbootstudyweek06.entity.Special;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpecialMapper extends BaseMapper<Special> {
    // 继承BaseMapper后，自带CRUD方法，无需手写基础SQL
}
