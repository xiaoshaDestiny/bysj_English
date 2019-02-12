package com.kg.xiaosha.bysj_english;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 使用 @EnableCaching开启注解缓存
 */

@EnableCaching
@SpringBootApplication
public class BysjEnglishApplication {

    public static void main(String[] args) {
        SpringApplication.run(BysjEnglishApplication.class, args);
    }

}

