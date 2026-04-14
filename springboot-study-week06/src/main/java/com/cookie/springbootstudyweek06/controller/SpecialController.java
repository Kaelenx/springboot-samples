package com.cookie.springbootstudyweek06.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cookie.springbootstudyweek06.entity.Special;
import com.cookie.springbootstudyweek06.sevice.SpecialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/special")
// 接口分组标签
@Tag(name = "专题管理接口", description = "专题表的分页查询、模糊查询等核心接口")
public class SpecialController {

    @Autowired
    private SpecialService specialService;

    /**
     * 专题分页模糊查询接口
     */
    @GetMapping("/page")
    // 接口功能描述
    @Operation(summary = "专题分页模糊查询", description = "支持按专题标题进行模糊匹配，返回分页结果，默认按浏览量倒序排序")
    public Page<Special> pageSpecial(
            // 参数描述
            @Parameter(description = "页码", example = "1", required = true)
            @RequestParam(defaultValue = "1") Integer pageNum,

            @Parameter(description = "每页条数", example = "10", required = true)
            @RequestParam(defaultValue = "10") Integer pageSize,

            @Parameter(description = "模糊查询关键字（匹配专题标题）", example = "重阳")
            @RequestParam(required = false) String title
    ) {
        return specialService.pageSpecialList(pageNum, pageSize, title);
    }

    /**
     * 新增：专题详情查询接口
     * @param id 专题ID
     * @return 专题详情实体
     */
    @GetMapping("/detail")
    @Operation(summary = "专题详情查询", description = "根据专题ID查询单条专题详情")
    public Special getSpecialDetail(
            @Parameter(description = "专题ID", example = "1632808674837200897", required = true)
            @RequestParam String id
    ) {
        return specialService.getSpecialById(id);
    }
}