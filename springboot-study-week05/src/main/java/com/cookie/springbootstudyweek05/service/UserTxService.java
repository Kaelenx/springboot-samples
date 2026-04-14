package com.cookie.springbootstudyweek05.service;


import com.cookie.springbootstudyweek05.entity.User;
import com.cookie.springbootstudyweek05.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 事务测试业务类
 * 严格匹配PDF示例：带@Transactional的事务方法
 */
@Service
@RequiredArgsConstructor
public class UserTxService {

    // 注入Mapper（兼容原生MyBatis和MP：MP的UserMPMapper也可直接替换，insert方法完全兼容）
    private final UserMapper userMapper;

    /**
     * 新增两个用户，添加声明式事务
     * 核心规则：方法内任意一步抛出运行时异常，所有操作全部回滚
     * @param user1 用户1
     * @param user2 用户2
     */
    @Transactional // 核心事务注解
    public void addTwoUsers(User user1, User user2) {
        // 第一步：新增用户1
        userMapper.insert(user1);

        // 模拟异常场景：如果用户2的用户名为空，抛出运行时异常，触发事务回滚
        if (user2.getUsername() == null || user2.getUsername().isEmpty()) {
            throw new RuntimeException("用户2姓名不能为空，事务回滚");
        }

        // 第二步：新增用户2（若上面抛出异常，此步骤不会执行，用户1的新增也会被回滚）
        userMapper.insert(user2);
    }
}
