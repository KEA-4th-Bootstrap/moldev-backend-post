package org.bootstrap.post.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.repository.PostRepository;
import org.bootstrap.post.utils.RedisUtils;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class SchedulerService {

    private final static String VIEW_COUNT = "viewCount";
    private final RedisUtils redisUtils;
    private final PostRepository postRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleViewCount() {
        Optional.ofNullable(redisUtils.getZSetOperations().range(VIEW_COUNT, 0, -1))
                .ifPresent(this::processKeys);
    }

    private void processKeys(Set<String> viewCountKeys) {
        List<Long> keys = getKeyList(viewCountKeys);
        List<Post> posts = getExistPostsByKeys(keys);

        posts.forEach(this::updatePostViewCount);
    }

    private void updatePostViewCount(Post post) {
        String member = String.valueOf(post.getId());
        ZSetOperations<String, String> zSetOperations = redisUtils.getZSetOperations();
        Double viewCount = Objects.requireNonNull(zSetOperations.score(VIEW_COUNT, member));
        post.updateViewCount(post.getViewCount() + viewCount.intValue());
        zSetOperations.remove(VIEW_COUNT, member);
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
