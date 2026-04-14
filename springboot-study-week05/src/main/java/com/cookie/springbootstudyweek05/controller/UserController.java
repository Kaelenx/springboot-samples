package com.cookie.springbootstudyweek05.controller;

import com.cookie.springbootstudyweek05.common.Result;
import com.cookie.springbootstudyweek05.entity.User;
import com.cookie.springbootstudyweek05.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/demo")
    public User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("mqxu");
        user.setPassword("123456");
        user.setAge(18);
        user.setEmail("16422802@qq.com");
        user.setCreateTime(LocalDateTime.now());
        return user;
    }

    @Resource
    private UserService userService;

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    /**
     * 查询所有用户
     */
    @GetMapping("/list")
    public Result<List<User>> list() {
        List<User> list = userService.list();
        return Result.success(list);
    }

    /**
     * 新增用户
     */
    @PostMapping
    public Result<String> save(@RequestBody User user) {
        boolean result = userService.save(user);
        return result ? Result.success() : Result.fail("新增失败");
    }

    /**
     * 更新用户
     */
    @PutMapping
    public Result<String> update(@RequestBody User user) {
        boolean result = userService.update(user);
        return result ? Result.success() : Result.fail("更新失败");
    }

    /**
     * 根据ID删除用户
     */
    @DeleteMapping("/{id}")
    public Result<String> removeById(@PathVariable Long id) {
        boolean result = userService.removeById(id);
        return result ? Result.success() : Result.fail("删除失败");
    }

    @GetMapping("/search")
    public Result<List<User>> search(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer age) {
        List<User> userList = userService.search(username, age);
        return Result.success(userList);
    }
}
