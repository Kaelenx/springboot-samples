package com.cookie.springbootstudyweek04.entity;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Data
@Component // 用于Spring容器注入yml配置，仅单元测试使用，接口入参不影响
public class Team {

    /**
     * 负责人姓名
     * 校验规则：非空、长度2-10位
     */
    @Value("${team.leader}")
    @NotBlank(message = "负责人姓名不能为空")
    @Length(min = 2, max = 10, message = "负责人姓名长度必须在2-10位之间")
    private String leader;

    /**
     * 负责人年龄
     * 校验规则：最小值30、最大值60
     */
    @Value("${team.age}")
    @Min(value = 30, message = "年龄不能小于30岁")
    @Max(value = 60, message = "年龄不能大于60岁")
    private Integer age;

    /**
     * 邮箱
     * 校验规则：符合邮箱格式规范
     */
    @Value("${team.email}")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号
     * 校验规则：11位纯数字
     */
    @Value("${team.phone}")
    @Pattern(regexp = "^[0-9]{11}$", message = "手机号必须是11位纯数字")
    private String phone;

    /**
     * 创建时间
     * 校验规则：必须是早于当前时间的过去时间
     */
    @Value("${team.createTime}")
    @Past(message = "创建时间必须早于当前时间")
    private LocalDate createTime;
}
