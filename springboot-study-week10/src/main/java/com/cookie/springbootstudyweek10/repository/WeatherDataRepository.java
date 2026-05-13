package com.cookie.springbootstudyweek10.repository;

import com.cookie.springbootstudyweek10.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
}
