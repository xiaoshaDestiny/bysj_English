package com.kg.xiaosha.bysj_english.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
public class MyCacheConfig {
    /**
     * 指定缓存模块Key的生成策略
     * @return
     */
    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator(){
            @Override
            public Object generate(Object target, Method method, Object... params) {

                //toString 方法已经有两个[]了 不用自己拼接了
                return method.getName()+ Arrays.asList(params).toString();

            }
        };
    }
}
