package com.example.tokenbucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
  @Autowired
  RateLimitingInterceptor rateLimitInterceptor;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;


  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(rateLimitInterceptor);
  }
}
