package com.cookie.springbootstudyweek08.leaderboard.dto;

public record UserRankView(
        Long userId,
        long score,
        long rank
) {
}
