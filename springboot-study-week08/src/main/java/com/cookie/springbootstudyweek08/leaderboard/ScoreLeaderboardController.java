package com.cookie.springbootstudyweek08.leaderboard;

import com.cookie.springbootstudyweek08.leaderboard.dto.LeaderboardEntryView;
import com.cookie.springbootstudyweek08.leaderboard.dto.ScoreIncrementRequest;
import com.cookie.springbootstudyweek08.leaderboard.dto.UserRankView;
import com.cookie.springbootstudyweek08.sms.dto.ApiResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@Validated
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class ScoreLeaderboardController {

    private final ScoreLeaderboardService scoreLeaderboardService;

    @PostMapping("/scores/increment")
    public ApiResult<UserRankView> increaseScore(@RequestBody @Valid ScoreIncrementRequest request) {
        return ApiResult.success(scoreLeaderboardService.increaseScore(request.userId(), request.scoreDelta()));
    }

    @GetMapping("/top")
    public ApiResult<List<LeaderboardEntryView>> top(
            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "limit 不能小于 1")
            @Max(value = 20, message = "limit 不能大于 20") Integer limit) {
        return ApiResult.success(scoreLeaderboardService.top(limit));
    }

    @GetMapping("/users/{userId}")
    public ApiResult<UserRankView> userRank(
            @PathVariable @Positive(message = "用户 ID 必须大于 0") Long userId) {
        return ApiResult.success(scoreLeaderboardService.userRank(userId));
    }
}
