package org.bootstrap.post.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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

    public ZSetOperations<String, String> getZSetOperations() {
        return redisTemplate.opsForZSet();
    }

    public Set<Long> getTrendingPostIds(String key, int size, Long offset) {
        Set<String> stringSet = getZSetOperations().reverseRange(key, offset, offset + size - 1);
        return stringSet.stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }
}
