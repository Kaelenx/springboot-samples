package com.cookie.springbootstudyweek08.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 地址实体类
 */
@Data // 自动生成getter、setter、toString、equals、hashCode
public class Address implements Serializable {

    // 字段和测试代码完全对应
    private String city;      // 城市
    private String street;    // 街道
    private String zipCode;   // 邮编
}
