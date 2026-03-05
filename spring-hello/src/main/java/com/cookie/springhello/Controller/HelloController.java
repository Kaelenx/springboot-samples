package com.cookie.springhello.Controller;

import com.cookie.springhello.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello接口控制器
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    /**
     * Hello接口
     * @return 统一格式的欢迎信息
     */
    @GetMapping("/hello")
    public ResultVO<String> hello() {
        return ResultVO.success("Hello Spring Boot");
    }
}