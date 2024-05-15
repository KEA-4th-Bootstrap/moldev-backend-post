package org.bootstrap.post.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.repository.PostRepository;
import org.bootstrap.post.utils.RedisUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class SchedulerService {

    private final RedisUtils redisUtils;
    private final PostRepository postRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleViewCount() {
        Optional.ofNullable(redisUtils.getKeys("*"))
                .ifPresent(this::processKeys);
    }

    private void processKeys(Set<String> viewCountKeys) {
        List<Long> keys = getKeyList(viewCountKeys);
        List<Post> posts = getExistPostsByKeys(keys);

        posts.stream()
                .peek(this::updateMemberViewCount)
                .collect(Collectors.toList());

        postRepository.saveAll(posts);
    }

    private void updateMemberViewCount(Post post) {
        String key = String.valueOf(post.getId());
        ValueOperations<String, String> valueOperations = redisUtils.getValueOperations();
        String value = valueOperations.getAndDelete(key);
        if (value != null) {
            post.updateViewCount(post.getViewCount() + Integer.parseInt(value));
        }
    }

    private List<Post> getExistPostsByKeys(List<Long> keys) {
        return keys.stream()
                .map(postRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private List<Long> getKeyList(Set<String> keys) {
        return keys.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

}
