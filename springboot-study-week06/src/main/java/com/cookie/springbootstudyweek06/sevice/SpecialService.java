package com.cookie.springbootstudyweek06.sevice;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cookie.springbootstudyweek06.entity.Special;

public interface SpecialService extends IService<Special> {

    /**
     * 分页模糊查询专题
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param keyword 模糊查询关键字（匹配标题）
     * @return 分页结果
     */
    Page<Special> pageSpecialList(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 根据ID查询专题详情
     * @param id 专题ID
     * @return 专题实体
     */
    Special getSpecialById(String id);
}