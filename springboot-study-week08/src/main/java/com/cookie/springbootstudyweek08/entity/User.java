package com.cookie.springbootstudyweek08.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
public class User implements Serializable {

    // 字段和测试代码完全对应
    private String name;        // 姓名
    private Integer age;        // 年龄
    private String email;       // 邮箱
    private Address address;    // 嵌套地址对象
}
