package com.cookie.springbootstudyweek08.like;

import com.cookie.springbootstudyweek08.like.dto.HotPostView;
import com.cookie.springbootstudyweek08.like.dto.PostLikeStatsView;
import com.cookie.springbootstudyweek08.like.dto.PostLikeView;
import com.cookie.springbootstudyweek08.util.RedisUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PostLikeService {

    private final RedisUtil redisUtil;
    private final RedisScript<Long> postLikeScript;
    private final RedisScript<Long> postUnlikeScript;

    public PostLikeService(
            RedisUtil redisUtil,
            @Qualifier("postLikeScript") RedisScript<Long> postLikeScript,
            @Qualifier("postUnlikeScript") RedisScript<Long> postUnlikeScript) {
        this.redisUtil = redisUtil;
        this.postLikeScript = postLikeScript;
        this.postUnlikeScript = postUnlikeScript;
    }

    public PostLikeView like(Long postId, Long userId) {
        String usersKey = PostLikeRedisKey.usersKey(postId);
        String userIdValue = String.valueOf(userId);
        String postIdValue = String.valueOf(postId);

        redisUtil.execute(postLikeScript, List.of(usersKey, PostLikeRedisKey.RANKING_KEY), userIdValue, postIdValue);
        PostLikeStatsView stats = stats(postId, userId);
        return new PostLikeView(postId, userId, stats.likedByCurrentUser(), stats.likeCount());
    }

    public PostLikeView unlike(Long postId, Long userId) {
        String usersKey = PostLikeRedisKey.usersKey(postId);
        String userIdValue = String.valueOf(userId);
        String postIdValue = String.valueOf(postId);

        redisUtil.execute(postUnlikeScript, List.of(usersKey, PostLikeRedisKey.RANKING_KEY), userIdValue, postIdValue);
        PostLikeStatsView stats = stats(postId, userId);
        return new PostLikeView(postId, userId, stats.likedByCurrentUser(), stats.likeCount());
    }

    public PostLikeStatsView stats(Long postId, Long userId) {
        String usersKey = PostLikeRedisKey.usersKey(postId);
        long likeCount = defaultLong(redisUtil.sSize(usersKey));
        boolean liked = userId != null && Boolean.TRUE.equals(redisUtil.sIsMember(usersKey, String.valueOf(userId)));
        return new PostLikeStatsView(postId, likeCount, liked);
    }

    public List<HotPostView> ranking(int limit) {
        Set<ZSetOperations.TypedTuple<String>> tuples =
                redisUtil.zReverseRangeWithScores(PostLikeRedisKey.RANKING_KEY, 0, limit - 1L);

        List<HotPostView> result = new ArrayList<>();
        if (tuples == null || tuples.isEmpty()) {
            return result;
        }

        int rank = 1;
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            if (tuple == null || tuple.getValue() == null) {
                continue;
            }
            long likeCount = tuple.getScore() == null ? 0L : tuple.getScore().longValue();
            result.add(new HotPostView(rank++, Long.valueOf(tuple.getValue()), likeCount));
        }
        return result;
    }

    private long defaultLong(Long value) {
        return value == null ? 0L : value;
    }
}
