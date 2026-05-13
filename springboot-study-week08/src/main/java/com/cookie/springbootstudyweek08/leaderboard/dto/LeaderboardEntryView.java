package com.cookie.springbootstudyweek08.leaderboard.dto;

public record LeaderboardEntryView(
        int rank,
        Long userId,
        long score
) {
}
