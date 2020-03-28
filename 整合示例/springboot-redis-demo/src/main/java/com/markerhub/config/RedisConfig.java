package com.markerhub.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
public class RedisConfig {

    /**
     * 改变序列化方式
     * <p>
     * 公众号：MarkerHub
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * 配置一个CacheManager才能使用@Cacheable等注解
     * <p>
     * 公众号：MarkerHub
     */
@Bean
public CacheManager cacheManager(RedisTemplate<String, Object> template) {

    // 基本配置
//    RedisCacheConfiguration defaultCacheConfiguration =
//            RedisCacheConfiguration
//                    .defaultCacheConfig()
//                    // 设置key为String
//                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getStringSerializer()))
//                    // 设置value 为自动转Json的Object
//                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getValueSerializer()))
//                    // 不缓存null
//                    .disableCachingNullValues()
//                    // 缓存数据保存1小时
//                    .entryTtl(Duration.ofHours(1));

    // 够着一个redis缓存管理器
RedisCacheManager redisCacheManager =
        RedisCacheManager.RedisCacheManagerBuilder
                // Redis 连接工厂
                .fromConnectionFactory(template.getConnectionFactory())
                // 缓存配置
//                    .cacheDefaults(defaultCacheConfiguration)

                .cacheDefaults(getCacheConfigurationWithTtl(template, 60 * 60))
                .withCacheConfiguration("cache_user", getCacheConfigurationWithTtl(template, 10))
                // 配置同步修改或删除 put/evict
                .transactionAware()
                .build();

return redisCacheManager;
}

RedisCacheConfiguration getCacheConfigurationWithTtl(RedisTemplate<String, Object> template, long seconds) {

    return RedisCacheConfiguration
            .defaultCacheConfig()
            // 设置key为String
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getStringSerializer()))
            // 设置value 为自动转Json的Object
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getValueSerializer()))
            // 不缓存null
            .disableCachingNullValues()
            // 缓存数据保存1小时
            .entryTtl(Duration.ofSeconds(seconds));
}

}
