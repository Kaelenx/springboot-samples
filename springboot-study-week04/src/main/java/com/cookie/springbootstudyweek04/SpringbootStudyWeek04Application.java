package com.cookie.springbootstudyweek04;

import com.cookie.springbootstudyweek04.service.CoffeeShop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootStudyWeek04Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootStudyWeek04Application.class, args);

        // 测试：从容器中取出 CoffeeShop 并调用
        CoffeeShop coffeeShop = context.getBean(CoffeeShop.class);
        coffeeShop.openShop();
    }

}
