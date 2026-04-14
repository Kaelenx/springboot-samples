package com.cookie.springbootstudyweek05.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cookie.springbootstudyweek05.common.Result;
import com.cookie.springbootstudyweek05.entity.User;
import com.cookie.springbootstudyweek05.service.UserMPService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * MP版用户接口控制器
 * 严格遵循PDF规范：仅做接口暴露，不写业务逻辑
 */
@RestController
@RequestMapping("/api/user/mp")
@RequiredArgsConstructor
public class UserMPController {

    private final UserMPService userMPService;

    /**
     * 条件分页查询接口（PDF核心接口）
     * @param pageNum 页码，默认第1页
     * @param pageSize 每页条数，默认10条
     * @param username 用户名（可选，模糊查询）
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result<Page<User>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username) {
        Page<User> pageResult = userMPService.page(username, pageNum, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 新增用户接口
     */
    @PostMapping
    public Result<String> add(@RequestBody User user) {
        int row = userMPService.add(user);
        if (row <= 0) {
            return Result.error("MP 新增失败");
        }
        return Result.success("MP 新增成功");
    }

    /**
     * 根据ID删除用户接口
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        int row = userMPService.delete(id);
        if (row <= 0) {
            return Result.error("MP 删除失败");
        }
        return Result.success("MP 删除成功");
    }

    /**
     * 更新用户接口
     */
    @PutMapping
    public Result<String> update(@RequestBody User user) {
        int row = userMPService.update(user);
        if (row <= 0) {
            return Result.error("MP 更新失败");
        }
        return Result.success("MP 更新成功");
    }

    /**
     * 根据ID查询用户接口
     */
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        User user = userMPService.getById(id);
        return Result.success(user);
    }

    /**
     * 查询所有用户接口
     */
    @GetMapping("/list")
    public Result<java.util.List<User>> list() {
        java.util.List<User> userList = userMPService.list();
        return Result.success(userList);
    }
}
