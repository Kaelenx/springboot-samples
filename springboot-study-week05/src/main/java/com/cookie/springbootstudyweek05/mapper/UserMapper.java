package com.cookie.springbootstudyweek05.mapper;

import com.cookie.springbootstudyweek05.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据ID查询用户
     */
    @Select("SELECT * FROM t_user WHERE id = #{id}")
    User selectById(Long id);

    /**
     * 查询所有用户
     */
    @Select("SELECT * FROM t_user")
    List<User> selectAll();

    /**
     * 新增用户
     */
    @Insert("INSERT INTO t_user(username, password, age, email) VALUES(#{username}, #{password}, #{age}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增回写
    int insert(User user);

    /**
     * 更新用户
     */
    @Update("UPDATE t_user SET username=#{username}, password=#{password}, age=#{age}, email=#{email} WHERE id=#{id}")
    int update(User user);

    /**
     * 根据ID删除用户
     */
    @Delete("DELETE FROM t_user WHERE id = #{id}")
    int deleteById(Long id);

    // ===================== 新增：XML实现条件查询 =====================
    List<User> selectByCondition(String username, Integer age);
}
