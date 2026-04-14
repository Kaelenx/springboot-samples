package com.cookie.springbootstudyweek06.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cookie.springbootstudyweek06.entity.Special;
import com.cookie.springbootstudyweek06.mapper.SpecialMapper;
import com.cookie.springbootstudyweek06.sevice.SpecialService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SpecialServiceImpl extends ServiceImpl<SpecialMapper, Special> implements SpecialService {

    @Override
    public Page<Special> pageSpecialList(Integer pageNum, Integer pageSize, String keyword) {
        // 1. 构建分页对象
        Page<Special> page = new Page<>(pageNum, pageSize);

        // 2. 构建查询条件
        LambdaQueryWrapper<Special> wrapper = new LambdaQueryWrapper<>();
        // 关键字非空时，对标题进行模糊查询
        wrapper.like(StringUtils.hasText(keyword), Special::getTitle, keyword);
        // 可扩展：按浏览量倒序排序
        wrapper.orderByDesc(Special::getViewCount);

        // 3. 执行分页查询
        return this.page(page, wrapper);
    }

    @Override
    public Special getSpecialById(String id) {
        // MP自带的getById方法，直接根据主键查询
        return this.getById(id);
    }
}
