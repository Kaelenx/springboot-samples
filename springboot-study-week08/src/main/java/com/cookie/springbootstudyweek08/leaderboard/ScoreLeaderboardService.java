package com.cookie.springbootstudyweek08.leaderboard;

import com.cookie.springbootstudyweek08.leaderboard.dto.LeaderboardEntryView;
import com.cookie.springbootstudyweek08.leaderboard.dto.UserRankView;
import com.cookie.springbootstudyweek08.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScoreLeaderboardService {

    private final RedisUtil redisUtil;

    public UserRankView increaseScore(Long userId, int scoreDelta) {
        String userIdValue = String.valueOf(userId);
        redisUtil.zIncrementScore(ScoreLeaderboardRedisKey.SCORE_RANKING_KEY, userIdValue, scoreDelta);
        return userRank(userId);
    }

    public List<LeaderboardEntryView> top(int limit) {
        Set<ZSetOperations.TypedTuple<String>> tuples =
                redisUtil.zReverseRangeWithScores(ScoreLeaderboardRedisKey.SCORE_RANKING_KEY, 0, limit - 1L);

        List<LeaderboardEntryView> result = new ArrayList<>();
        if (tuples == null || tuples.isEmpty()) {
            return result;
        }

        int rank = 1;
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            if (tuple == null || tuple.getValue() == null) {
                continue;
            }
            long score = tuple.getScore() == null ? 0L : tuple.getScore().longValue();
            result.add(new LeaderboardEntryView(rank++, Long.valueOf(tuple.getValue()), score));
        }
        return result;
    }

    public UserRankView userRank(Long userId) {
        String userIdValue = String.valueOf(userId);
        Double score = redisUtil.zScore(ScoreLeaderboardRedisKey.SCORE_RANKING_KEY, userIdValue);
        Long rank = redisUtil.zReverseRank(ScoreLeaderboardRedisKey.SCORE_RANKING_KEY, userIdValue);
        long currentScore = score == null ? 0L : score.longValue();
        long currentRank = rank == null ? 0L : rank + 1;
        return new UserRankView(userId, currentScore, currentRank);
    }
}
