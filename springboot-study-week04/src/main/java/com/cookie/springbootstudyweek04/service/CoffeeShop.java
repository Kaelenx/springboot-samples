package com.cookie.springbootstudyweek04.service;


import com.cookie.springbootstudyweek04.component.CoffeeMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 建议用 @Service 替代 @Component，语义更清晰（表示这是业务层）
public class CoffeeShop {

    @Autowired
    private CoffeeMachine coffeeMachine;

    public void openShop() {
        System.out.println("🏪 咖啡店开门啦！");
        coffeeMachine.makeCoffee();
    }
}