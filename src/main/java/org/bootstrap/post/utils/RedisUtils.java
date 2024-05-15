package org.bootstrap.post.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String, String> redisTemplate;

    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public ValueOperations<String, String> getValueOperations() {
        return redisTemplate.opsForValue();
    }
}
