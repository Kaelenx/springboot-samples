package com.cookie.springbootstudyweek08.stats;

import com.cookie.springbootstudyweek08.stats.dto.PostViewStatsView;
import com.cookie.springbootstudyweek08.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostViewStatsService {

    private final RedisUtil redisUtil;

    public PostViewStatsView increment(Long postId, int delta) {
        String key = PostViewStatsRedisKey.countKey(postId);
        long viewCount = defaultLong(redisUtil.increment(key, delta));
        return new PostViewStatsView(postId, viewCount);
    }

    public PostViewStatsView stats(Long postId) {
        String key = PostViewStatsRedisKey.countKey(postId);
        long viewCount = defaultLong(redisUtil.getLong(key));
        return new PostViewStatsView(postId, viewCount);
    }

    private long defaultLong(Long value) {
        return value == null ? 0L : value;
    }
}
