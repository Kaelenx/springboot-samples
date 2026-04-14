package com.cookie.springbootstudyweek04.config;



import com.cookie.springbootstudyweek04.component.CoffeeMachine;
import com.cookie.springbootstudyweek04.component.CoffeeMachine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoffeeShopConfig {

    @Bean
    public CoffeeMachine coffeeMachine() {
        System.out.println("🔧 配置类正在创建咖啡机...");
        return new CoffeeMachine();
    }
}
